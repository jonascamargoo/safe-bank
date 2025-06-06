package com.safebank.demo.infra.security;


import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.safebank.demo.repositories.CustomerRepository;
import com.safebank.demo.services.security.TokenService;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final CustomerRepository customerRepository;

    public SecurityFilter(TokenService tokenService, CustomerRepository customerRepository) {
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
    }

    // antes de chamar o securityFilter no filterChain do SecurityConfiguration, ele ira passar por aqui primeiro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        if(token != null) {
            var login = tokenService.validateToken(token);
            UserDetails customer = customerRepository.findByCpf(login);

            var authentication = new UsernamePasswordAuthenticationToken(customer, null, customer.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // caso nao haja token, passa pro proximo item do filtro (nesse caso, o UsernamePasswordAuthenticationFilter.class)
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", ""); // substituindo o valor do bearer por um vazio, pois quando o token vem no header, ele identifica o tipo de token (nesse caso, o bearer) e em seguida o token. Como queremos so o token, retiramos a primeira palavra
    }
}