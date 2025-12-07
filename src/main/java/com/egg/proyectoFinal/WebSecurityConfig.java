package com.egg.proyectoFinal;

import com.egg.proyectoFinal.security.JwtAuthenticationEntryPoint;
import com.egg.proyectoFinal.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // <-- FIX

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // UserDetailsService + PasswordEncoder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/registro").permitAll()
                .antMatchers(HttpMethod.GET, "/api/servicios/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/personas/**").permitAll()

                .antMatchers(HttpMethod.POST, "/api/ordenes/persona/**")
                .hasAnyRole("GUEST", "USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/ordenes/mias").hasAnyRole("GUEST","USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/ordenes/*").hasAnyRole("GUEST","USER","ADMIN")

                .antMatchers(HttpMethod.GET, "/api/ordenes/recibidas").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/ordenes/*/aceptar").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/ordenes/*/finalizar").hasAnyRole("GUEST","USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/personas/*").hasAnyRole("USER","ADMIN")

                .antMatchers(HttpMethod.POST, "/api/comentarios/orden/**").hasAnyRole("GUEST","USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/comentarios/**").hasAnyRole("GUEST","USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/comentarios/proveedor/**").permitAll()

                .antMatchers("/api/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
