package br.edu.ufape.roomie.service;
import br.edu.ufape.roomie.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {
    private TokenService tokenService;
    private final String SECRET = "test-secret";

    @BeforeEach
    void setUp(){
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", SECRET);
    }

    @Test
    @DisplayName("Deve gerar token JWT válido para um usuário")
    void shouldGenerateToken(){
        User user = new User();
        user.setEmail("test@gmail.com");

        String token = tokenService.generateToken(user);

        assertNotNull(token); 
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("Deve validar token JWT válido e retornar o email do usuário")
    void shouldValidateValidToken() {
        User user = new User(); 
        user.setEmail("test@gmail.com");
        String token = tokenService.generateToken(user); 
        String subject = tokenService.validateToken(token); 
        assertEquals("test@gmail.com", subject);
    }

    @Test
    @DisplayName("Deve retornar string vazia para token JWT inválido")
    void shouldReturnEmptyStringTokenIsInvalid() {
        String invalidToken = "token.invalido"; 
        String result = tokenService.validateToken(invalidToken); 
        assertEquals("", result);
    }

    @Test
    @DisplayName("Deve rejeitar token assinado com segredo incorreto (Hacker tentou forjar)")
    void shouldRejectTokenSignedWithWrongSecret() {
        Algorithm wrongAlgorithm = Algorithm.HMAC256("segredo-do-hacker");
        String forgedToken = JWT.create()
                .withIssuer("roomie-api")
                .withSubject("admin@roomie.com")
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .sign(wrongAlgorithm);

        // O serviço deve rejeitar
        String result = tokenService.validateToken(forgedToken);

        assertEquals("", result, "O serviço aceitou um token forjado!");
    }
}
