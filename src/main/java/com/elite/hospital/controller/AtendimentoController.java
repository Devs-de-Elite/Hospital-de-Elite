package com.elite.hospital.controller;

import com.elite.hospital.model.Atendimento;
import com.elite.hospital.enums.SituacaoConsulta;
import com.elite.hospital.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping
    public List<Atendimento> listarTodos() {
        return atendimentoService.listarTodos();
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> listarAtendimentos(
            @RequestParam(required = false) String filtro) {

        if ("pendentes".equalsIgnoreCase(filtro)) {
            return ResponseEntity.ok(atendimentoService.listarAguardandoAvaliacao());
        } 
        
        if ("avaliados".equalsIgnoreCase(filtro)) {
            return ResponseEntity.ok(atendimentoService.listarAvaliados());
        }

        return ResponseEntity.ok(atendimentoService.listarTodos());
    }
    
    @PostMapping
    public ResponseEntity<Atendimento> marcarAtendimento(@RequestBody Atendimento atendimento) {
        Atendimento novoAtendimento = atendimentoService.marcarAtendimento(atendimento);
        return ResponseEntity.ok(novoAtendimento);
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<Atendimento> atualizarSituacao(
            @PathVariable Long id, 
            @RequestParam SituacaoConsulta novaSituacao) {
        
        Atendimento atendimentoAtualizado = atendimentoService.atualizarSituacao(id, novaSituacao);
        return ResponseEntity.ok(atendimentoAtualizado);
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Atendimento> adicionarObservacao(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        
        String texto = payload.get("observacoes");
        
        Atendimento atendimentoAtualizado = atendimentoService.adicionarObservacao(id, texto);
        return ResponseEntity.ok(atendimentoAtualizado);
    }
}