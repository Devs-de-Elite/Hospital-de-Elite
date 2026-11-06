package com.elite.hospital.controller;

import com.elite.hospital.model.Internacao;
import com.elite.hospital.service.InternacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping({"/internacoes", "/api/internacoes"})
@CrossOrigin(origins = "*")
public class InternacaoController {
    @Autowired
    private InternacaoService internacaoService;

    @GetMapping
    public List<Internacao> listarTodas() {
        return internacaoService.listarTodas();
    }

    @PostMapping
    public ResponseEntity<Internacao> internar(@RequestBody Internacao internacao,
            @RequestParam Long pacienteId, @RequestParam Long profissionalId, @RequestParam Long quartoId) {
        return ResponseEntity.ok(internacaoService.internar(internacao, pacienteId, profissionalId, quartoId));
    }

    @PatchMapping("/{id}/alta")
    public ResponseEntity<Internacao> registrarAlta(@PathVariable Long id, @RequestParam LocalDate dataAlta) {
        return ResponseEntity.ok(internacaoService.registrarAlta(id, dataAlta));
    }
}
