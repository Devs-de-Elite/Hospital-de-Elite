package com.elite.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HospitalController {

    @GetMapping("/homepage")
    public String mostrarHomepage(){
        return "homepage";
    }

    @GetMapping("/quartos")
    public String mostrarQuartos(){
        return "quartos";
    }

    @GetMapping("/consultas")
    public String mostrarConsultas(){
        return "consultas";
    }

    @GetMapping("/internacoes")
    public String mostrarInternacoes(){
        return "internacoes";
    }

    @GetMapping("/pacientes")
    public String mostrarPacientes(){
        return "pacientes";
    }

    @GetMapping("/profissionais")
    public String mostrarProfissionais(){
        return "profissionais";
    }
}
