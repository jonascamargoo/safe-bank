package com.safebank.demo.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // desabilitando o Cross-Site Request Forgery, pois o client eh
                                              // confiavel(frontend)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/auth/logar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/registrar").permitAll()
                        .anyRequest().authenticated() // como nao temos role admin, todas as req nao autorizadas
                                                      // precisaram de autenticacao
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return httpSecurity
    //             .csrf(csrf -> csrf.disable())
    //             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests(authorize -> authorize
    //                     // ALTERAÇÃO PARA DESATIVAR A SEGURANÇA:
    //                     .anyRequest().permitAll() 
    //             )
    //             // O filtro de token pode ou não ser comentado, mas com permitAll não fará diferença.
    //             // .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
    //             .build();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // algoritmo para fazer o hash
    @Bean
    public PasswordEncoder passwordEncoder() {
        // deveria fazer um BCryptPasswordEncoder().encode?
        return new BCryptPasswordEncoder();
    }
}