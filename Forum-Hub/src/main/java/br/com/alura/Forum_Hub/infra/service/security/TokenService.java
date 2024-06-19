package br.com.alura.Forum_Hub.infra.service.security;

import br.com.alura.Forum_Hub.domain.model.user.User;
import br.com.alura.Forum_Hub.infra.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class TokenService {
    @Autowired
    private UserRepository userRepository;

    private String secret;
    @Getter
    private User secretOwner;

    private void setSecret(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String login = decodedJWT.getSubject();
            secretOwner = (User) userRepository.findByLogin(login);
            this.secret = secretOwner.getPassword();

        } catch (JWTDecodeException e) {
            throw new RuntimeException("Erro ao decodificar o token JWT: " + e.getMessage());
        }
    }

    /*gera o token a partir dos dados do usuario*/
    public String generateToken(User user) {
        try {
            this.secret = user.getPassword();
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Instant expireAt = this.expire();

            return JWT.create()
                    .withIssuer("API voll.med")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(Date.from(expireAt))
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o TOKEN: " + e.getMessage());
        }
    }

    /*  utiliza a password disponibilizada pelo getSecret e o usa para setar o algoritimo
     *   para permitir a verificação do token passado*/
    public void validateToken(String token) {

        try {
            this.setSecret(token);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("API voll.med")
                    .build();

            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao validar o token: "+e);
        }
    }

    private Instant expire() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
