package com.elite.hospital.controller;

import com.elite.hospital.model.ProcedimentoCirurgico;
import com.elite.hospital.service.ProcedimentoCirurgicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedimentos-cirurgicos")
@CrossOrigin(origins = "*")
public class ProcedimentoCirurgicoController {

    @Autowired
    private ProcedimentoCirurgicoService service;

    @GetMapping
    public List<ProcedimentoCirurgico> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<ProcedimentoCirurgico> marcar(@RequestBody ProcedimentoCirurgico procedimento,
                                                        @RequestParam Long pacienteId,
                                                        @RequestParam Long cirurgiaoId) {
        return ResponseEntity.ok(service.marcarProcedimento(procedimento, pacienteId, cirurgiaoId));
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<ProcedimentoCirurgico> iniciar(@PathVariable Long id) {
        return ResponseEntity.ok(service.iniciar(id));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<ProcedimentoCirurgico> concluir(@PathVariable Long id,
                                                          @RequestParam(required = false) String observacoesPosOperatorias) {
        return ResponseEntity.ok(service.concluir(id, observacoesPosOperatorias));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ProcedimentoCirurgico> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
