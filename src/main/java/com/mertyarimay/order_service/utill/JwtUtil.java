package com.mertyarimay.order_service.utill;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "your_secret_key"; // JWT'leri doğrulamak için gizli bir anahtar
    private final long EXPIRATION_TIME = 3600000; // 1 saat geçerlilik süresi
    public String extractUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);  // Token'ı decode ediyoruz
        return jwt.getClaim("userId").asString(); // Kullanıcı ID'sini döndürüyoruz
    }

    // Token'dan kullanıcı adını alma metodu
    public String extractUsername(String token) {
        DecodedJWT jwt = JWT.decode(token); // Token'ı decode ediyoruz yani payload kısmına bakılır yetkiler username vs
        return jwt.getSubject(); // Kullanıcı adını döndürüyoruz
    }

    // Token'dan kullanıcının rollerini çıkarma metodu
    public List<String> extractRoles(String token) {
        DecodedJWT jwt = JWT.decode(token); // Token'ı decode ediyoruz orjinal okunabilir hale getirme
        return List.of(jwt.getClaim("roles").asArray(String.class)); // Rol bilgilerini liste olarak alıyoruz
    }

    // Token doğrulama metodu
    public boolean validateToken(String token) {
        try {
            // Token'ı doğrulamak için algoritma
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build(); // JWT doğrulayıcı oluşturulur
            DecodedJWT jwt = verifier.verify(token); // Token doğrulanır

            // Token süresi geçerli mi kontrol etme
            return !isTokenExpired(jwt);
        } catch (Exception e) {
            return false; // Eğer doğrulama başarısız olursa, false döner
        }
    }

    // Token'ın süresinin dolup dolmadığını kontrol etme metodu
    private boolean isTokenExpired(DecodedJWT jwt) {
        return jwt.getExpiresAt().before(new Date()); // Geçerlilik süresi bitmişse false döner
    }

    // Token'ın geçerli olup olmadığını ve kullanıcı adı ve rollerle doğrulama
    public boolean validateToken(String token, String username, List<String> expectedRoles) {
        try {
            // Algoritma ile JWT doğrulayıcı oluşturuluyor
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token); // Token doğrulama işlemi

            // Kullanıcı adı ve roller kontrolü
            List<String> roles = extractRoles(token); // Token'dan roller alınır
            return jwt.getSubject().equals(username) && roles.containsAll(expectedRoles) && !isTokenExpired(jwt);
        } catch (Exception e) {
            return false; // Hata durumunda false döndürülür
        }
    }

}
