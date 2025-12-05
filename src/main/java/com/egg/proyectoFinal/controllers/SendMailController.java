package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.services.impl.SendMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
public class SendMailController {

    @Autowired
    private SendMailServiceImpl mailService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Void> sendMail(@RequestBody Map<String, String> payload) {
        String name = payload.getOrDefault("name", "");
        String mail = payload.getOrDefault("mail", "");
        String subject = payload.getOrDefault("subject", "");
        String body = payload.getOrDefault("body", "");
        String message = "Detalle: " + "\n" + body + "\n\n Datos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
        mailService.sendMail("proyecto.final.egg@gmail.com", "arreglaya.app@gmail.com", subject, message);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
