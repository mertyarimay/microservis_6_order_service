package com.mertyarimay.order_service.security;

import com.mertyarimay.order_service.utill.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final DataSource dataSource;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, DataSource dataSource) {
        this.jwtUtil = jwtUtil;
        this.dataSource = dataSource;
    }

    //token kontrolü yapılmayan end pointler
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/order/item/api/create") || path.equals("/order/api/create");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  //isteğin filtrelendiği yer
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request); //request body nin bir kez okunması kuralını aşması için kullanılır

        String token = getTokenFromRequest(wrappedRequest); //tokene alıyor headerdan
        System.out.println("Token: " + token);

        if (token == null || !jwtUtil.validateToken(token)) {  //token doğrulama
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //tokenın içindeki bilgileri alır username,role, utill sınıfı yardımıyla

        String username = jwtUtil.extractUsername(token);
        List<String> roles = jwtUtil.extractRoles(token);
        System.out.println("Roller: " + roles);

        int customerIdFromToken;
        try {
            customerIdFromToken = Integer.parseInt(jwtUtil.extractUserId(token));
            System.out.println("TokenCustomerId: " + customerIdFromToken);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<String> authorities = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .collect(Collectors.toList());
        System.out.println("Authorities: " + authorities);
     //security kısmına kullanıcının rolü gönderilir yetkisi uygunmu
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username, null,
                        AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]))
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String uri = wrappedRequest.getRequestURI();  //uri alınır aşağdaki adreslerde özel kontrol yapılması için
        System.out.println("Request URI: " + uri);

        try {
            if (uri.matches(".*/adress/api/(create|getById|update|delete)/\\d*")) {
                if (!roles.contains("ROLE_CUSTOMER")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
            if (uri.matches(".*/order/api/getAll/?$")) {
                if (!roles.contains("ROLE_ADMIN")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                //url deki order ıdyi alıp aşağdaki metotda o order ıdye ait customer ıd le tokendaki customer ıd birbirine eşitmi diye bakıyor
            } else if (uri.matches(".*/order/api/(getById|update|delete)/\\d+/?$")) {
                int orderId = extractIdFromUrl(uri);
                if (!isCustomerOwnerOfOrder(orderId, customerIdFromToken)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                //order ıtemde urlde ki order ıtem ıdyi alır ona ait orderı getirir sonra o ordera ait customer ıd ile tokendaki customer ıd eşitmi onun kontrolüne bakar

            } else if (uri.matches(".*/order/item/api/(getById|update|delete)/\\d+/?$")) {
                int orderItemId = extractIdFromUrl(uri);
                Integer orderId = getOrderIdFromOrderItem(orderItemId);
                if (orderId == null || !isCustomerOwnerOfOrder(orderId, customerIdFromToken)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }


            filterChain.doFilter(wrappedRequest, response);  //doğrulama işlemi doğruysa istek devam eder
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private int extractIdFromUrl(String uri) {
        String[] parts = uri.split("/");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (parts[i].matches("\\d+")) {
                return Integer.parseInt(parts[i]);
            }
        }
        return -1;
    }

    private boolean isCustomerOwnerOfOrder(int orderId, int customerIdFromToken) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT customer_id FROM orders WHERE id = ?")) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("customer_id") == customerIdFromToken;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Integer getOrderIdFromOrderItem(int orderItemId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT order_id FROM order_items WHERE id = ?")) {
            stmt.setInt(1, orderItemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("order_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }
}
