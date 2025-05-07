package com.mertyarimay.order_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //@PreAuthorize()  kullanabiliyoruz bu annotation sayesinde
public class PasswordEncoderConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public PasswordEncoderConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF devre dışı bırakma
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/order/api/create").permitAll()
                        .requestMatchers("/order/item/api/create").permitAll()
                        .requestMatchers("order/api/getAll").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("order/api/getById/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("order/api/update/{id}").hasAnyAuthority("ROLE_CUSTOMER")
                        .requestMatchers("order/api/delete/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("order/item/api/getById/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("order/item/api/update/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("order/item/api/delete/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("adress/api/create").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("adress/api/getById/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("adress/api/update/{id}").hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("adress/api/delete/{id}").hasAuthority("ROLE_CUSTOMER")
                        .anyRequest().authenticated() // Diğer tüm isteklere kimlik doğrulama zorunlu
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtresi ekliyoruz
        //jwtAuthenticationFilter ilk önce bu filtre uygulanır tokenen doğruluğu kontrol edilir ,UsernamePasswordAuthenticationFilter.class kimlik doğrulaması için kullanılır login işlemlerinde

        return http.build(); // Yapılandırmayı tamamla ve SecurityFilterChain nesnesini döndür

    }
}

