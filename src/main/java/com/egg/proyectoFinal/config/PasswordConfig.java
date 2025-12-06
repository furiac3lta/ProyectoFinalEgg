package com.egg.proyectoFinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bcrypt.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String sanitized = encodedPassword;

                // Soporta hashes antiguos con prefijo {bcrypt} o {null}
                if (encodedPassword != null && encodedPassword.startsWith("{")) {
                    int closing = encodedPassword.indexOf('}');
                    if (closing > 0 && encodedPassword.length() > closing + 1) {
                        sanitized = encodedPassword.substring(closing + 1);
                    }
                }

                return bcrypt.matches(rawPassword, sanitized);
            }
        };
    }
}
