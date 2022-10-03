package com.egg.proyectoFinal.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessMessage extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SessionFlashMapManager fManager = new SessionFlashMapManager();
        FlashMap fMap = new FlashMap();
        fMap.put("success", "Bienvenido ha iniciado sesion con exito !!");
        fManager.saveOutputFlashMap(fMap, request, response);
        super.onAuthenticationSuccess(request, response, authentication);
    }


}
