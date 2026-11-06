package com.elite.hospital.controller;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.model.Cirurgiao;
import com.elite.hospital.model.Profissional;
import com.elite.hospital.model.MedicoClinicoGeral;
import com.elite.hospital.model.MedicoEspecialista;
import com.elite.hospital.model.ProcedimentoCirurgico;
import com.elite.hospital.service.CategoriaProfissionalService;
import com.elite.hospital.service.ProcedimentoCirurgicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/profissionais", "/profissionais"})
@CrossOrigin(origins = "*")
public class CategoriaProfissionalController {

    @Autowired
    private CategoriaProfissionalService profissionalService;

    @Autowired
    private ProcedimentoCirurgicoService procedimentoService;

    @GetMapping
    public List<Profissional> listarTodos() {
        return profissionalService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Profissional> cadastrar(@RequestBody Profissional profissional) {
        return ResponseEntity.ok(profissionalService.cadastrar(profissional));
    }

    @PostMapping("/clinicos")
    public ResponseEntity<Profissional> cadastrarClinico(@RequestBody MedicoClinicoGeral clinico) {
        return ResponseEntity.ok(profissionalService.cadastrar(clinico));
    }

    @PostMapping("/{id}/encaminhamentos")
    public ResponseEntity<MedicoEspecialista> encaminhar(@PathVariable Long id,
            @RequestParam Long pacienteId, @RequestParam Especialidade especialidade) {
        return ResponseEntity.ok(profissionalService.encaminhar(id, pacienteId, especialidade));
    }

    @PostMapping("/especialistas")
    public ResponseEntity<MedicoEspecialista> cadastrarEspecialista(@RequestBody MedicoEspecialista especialista) {
        return ResponseEntity.ok(profissionalService.cadastrarEspecialista(especialista));
    }

    @GetMapping("/especialistas")
    public List<MedicoEspecialista> listarEspecialistas(@RequestParam(required = false) Especialidade especialidade) {
        return profissionalService.listarEspecialistas(especialidade);
    }

    @PostMapping("/cirurgioes")
    public ResponseEntity<Cirurgiao> cadastrarCirurgiao(@RequestBody Cirurgiao cirurgiao) {
        return ResponseEntity.ok(profissionalService.cadastrarCirurgiao(cirurgiao));
    }

    @GetMapping("/cirurgioes/{id}/procedimentos")
    public List<ProcedimentoCirurgico> procedimentosRealizados(@PathVariable Long id) {
        return procedimentoService.listarRealizados(id);
    }
}
