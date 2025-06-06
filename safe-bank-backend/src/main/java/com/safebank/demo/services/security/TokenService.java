package com.safebank.demo.services.security;

// import java.time.Instant;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.safebank.demo.domains.Customer;
import com.safebank.demo.services.CustomerService;


@Service
public class TokenService {
    @Autowired
    private CustomerService customerService;

    // injetando a secret que esta na app.properties
    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(Customer customer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // o HMAC256 recebe uma secret para adicionar algo a mais, a fim de evitar um possivel mapeamento invasor utilizando brutal force. So a aplicacao deve conhecer a chave
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(customer.getCpf())
                    .withClaim("id", String.valueOf(customer.getId())) // Adiciona o ID do usuário como um claim
                    //.withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException("Token inválido: " + exception.getMessage());
        }
    }
    
    public Long getUserIdFromToken(String token) {
        String userCpf = validateToken(token);
        return customerService.getCustomerIdByCpf(userCpf);
    }    

    // nosso token expira em 2 hrs (time zone brasilia)
    // private Instant genExpirationDate() {
    //     return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    // }

}