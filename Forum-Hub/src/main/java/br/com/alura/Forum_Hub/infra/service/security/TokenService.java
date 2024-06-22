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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
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
            secretOwner = (User) userRepository.findByLogin(login); // Removido casting desnecessário
            if (secretOwner != null) {
                this.secret = secretOwner.getPassword();
            } else {
                throw new RuntimeException("Usuário não encontrado para o login: " + login);
            }
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
                    .withIssuer("Forum-Hub")
                    .withSubject(user.getLogin())
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
                    .withIssuer("Forum-Hub")
                    .build();

            verifier.verify(token); // Verifica se o token é válido
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao validar o token: " + e.getMessage());
        }
    }

    private Instant expire() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
