package com.elite.hospital.controller;

import com.elite.hospital.enums.Especialidade;
import com.elite.hospital.model.Cirurgiao;
import com.elite.hospital.model.MedicoEspecialista;
import com.elite.hospital.model.ProcedimentoCirurgico;
import com.elite.hospital.service.CategoriaProfissionalService;
import com.elite.hospital.service.ProcedimentoCirurgicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
public class CategoriaProfissionalController {

    @Autowired
    private CategoriaProfissionalService profissionalService;

    @Autowired
    private ProcedimentoCirurgicoService procedimentoService;

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
