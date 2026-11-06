package com.elite.hospital.controller;

import com.elite.hospital.model.Quarto;
import com.elite.hospital.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping({"/quartos", "/api/quartos"})
@CrossOrigin(origins = "*")
public class QuartoController {
    @Autowired
    private QuartoService quartoService;

    @GetMapping
    public List<Quarto> listarTodos() {
        return quartoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Quarto> cadastrar(@RequestBody Quarto quarto) {
        return ResponseEntity.ok(quartoService.cadastrar(quarto));
    }

    @GetMapping("/{id}/ocupacao")
    public long ocupacaoAtual(@PathVariable Long id) {
        return quartoService.ocupacaoAtual(id);
    }
}
