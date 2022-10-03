package com.egg.proyectoFinal;

import com.egg.proyectoFinal.services.impl.UsuarioServicioImpl;
import com.egg.proyectoFinal.utils.LoginSuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServicioImpl usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }

     @Autowired
     private LoginSuccessMessage successMessage;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/logincheck","/index", "/mail","/contacto","/styles/**","/images/**","/layout/**",
                                        "/views/personas/listar","/js/**","/imagen/**",
                                        "/registro","/views/personas/listar_oficios",
                                        "/views/personas/listar_nombres", "views/ordenes/**",
                                        "/views/personas/listar_email","/").permitAll()

                .antMatchers("/dashboard_reportes_rol").hasAnyRole("ADMIN")
                .antMatchers("/dashboard").hasAnyRole("ADMIN")
                .antMatchers("/views/servicios/form").hasAnyRole("ADMIN")
                .antMatchers("/views/personas/form").hasAnyRole("ADMIN")
                .antMatchers("/views/comentarios/form").hasAnyRole("ADMIN")
                .antMatchers("/displayBarGraph").hasAnyRole("ADMIN")
                .antMatchers("/displayBarGraph_1").hasAnyRole("ADMIN")
                .antMatchers("/dashboard_reportes").hasAnyRole("ADMIN")
                .antMatchers("/views/ordenes/listar_admin").hasAnyRole("ADMIN")
                .antMatchers("/views/servicios/listar").hasAnyRole("GUEST","USER","ADMIN")
                .antMatchers("/views/personas/listar_todo").hasAnyRole("ADMIN")
                .antMatchers("/views/personas/puente").hasAnyRole("GUEST","USER","ADMIN")
                .antMatchers("/views/personas/perfil").hasAnyRole("GUEST","USER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
               .loginProcessingUrl("/logincheck")
               .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successMessage)
                .defaultSuccessUrl("/")
                .permitAll()
                .and().logout()
                .permitAll();
    }
    }

