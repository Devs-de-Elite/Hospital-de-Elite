package com.elite.hospital.controller;

import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.model.ConsultaEspecializada;
import com.elite.hospital.service.ConsultaEspecializadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas/especializadas")
@CrossOrigin(origins = "*")
public class ConsultaEspecializadaController {

    @Autowired
    private ConsultaEspecializadaService service;

    @GetMapping
    public List<ConsultaEspecializada> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public ResponseEntity<ConsultaEspecializada> marcar(@RequestBody ConsultaEspecializada consulta,
                                                        @RequestParam Long pacienteId,
                                                        @RequestParam Long profissionalId) {
        return ResponseEntity.ok(service.marcarConsulta(consulta, pacienteId, profissionalId));
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<ConsultaEspecializada> atualizarSituacao(@PathVariable Long id,
                                                                   @RequestParam SituacaoConsulta novaSituacao) {
        return ResponseEntity.ok(service.atualizarSituacao(id, novaSituacao));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<ConsultaEspecializada> registrarObservacoes(@PathVariable Long id,
                                                                      @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(service.registrarObservacoes(id, payload.get("observacoes")));
    }
}
