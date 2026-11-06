package com.elite.hospital.service;

import com.elite.hospital.model.Quarto;
import com.elite.hospital.enums.SituacaoInternacao;
import com.elite.hospital.repository.QuartoRepository;
import com.elite.hospital.repository.InternacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuartoService {
    @Autowired
    private QuartoRepository quartoRepository;
    @Autowired
    private InternacaoRepository internacaoRepository;

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Quarto cadastrar(Quarto quarto) {
        if (quarto.getId() != null) {
            throw new RuntimeException("Um novo quarto não deve informar ID.");
        }
        quarto.validarCondicoes();
        return quartoRepository.save(quarto);
    }

    public long ocupacaoAtual(Long id) {
        if (!quartoRepository.existsById(id)) {
            throw new RuntimeException("Quarto não encontrado!");
        }
        return internacaoRepository.countByQuartoIdAndSituacao(id, SituacaoInternacao.ATIVA);
    }
}
