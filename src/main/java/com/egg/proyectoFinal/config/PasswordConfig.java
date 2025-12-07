package com.egg.proyectoFinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", bcrypt);
        encoders.put("null", bcrypt); // hashes antiguos con prefijo {null}

        DelegatingPasswordEncoder delegating = new DelegatingPasswordEncoder("bcrypt", encoders);
        delegating.setDefaultPasswordEncoderForMatches(bcrypt); // hashes sin prefijo

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return delegating.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null) {
                    return false;
                }

                // Intenta primero el flujo estándar
                if (delegating.matches(rawPassword, encodedPassword)) {
                    return true;
                }

                // Si viene con un prefijo incrustado en el hash, lo saneamos y validamos igual
                int closing = encodedPassword.indexOf('}');
                if (encodedPassword.startsWith("{") && closing > 0 && encodedPassword.length() > closing + 1) {
                    String sanitized = encodedPassword.substring(closing + 1);
                    return bcrypt.matches(rawPassword, sanitized);
                }

                return false;
            }
        };
    }
}
