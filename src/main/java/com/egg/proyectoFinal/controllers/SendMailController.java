package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.services.impl.SendMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SendMailController {

    @Autowired
    private SendMailServiceImpl mailService;

    @GetMapping("/contacto")
    public String index() {
        return "contacto";
    }

    @PostMapping("/mail")
    public String sendMail(@RequestParam("name") String name, @RequestParam("mail") String mail,
                           @RequestParam("subject") String subject, @RequestParam("body") String body, RedirectAttributes attributes) {

        String message = "Detalle: " + "\n" + body + "\n\n Datos de contacto: " + "\nNombre: " + name + "\nE-mail: " + mail;
        mailService.sendMail("proyecto.final.egg@gmail.com", "arreglaya.app@gmail.com", subject, message);
        attributes.addFlashAttribute("success", "Su email fue enviado correctamente!!");
        return "redirect:/contacto";
    }
}
