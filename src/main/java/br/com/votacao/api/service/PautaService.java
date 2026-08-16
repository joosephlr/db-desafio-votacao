package br.com.votacao.api.service;

import br.com.votacao.api.dto.PautaDTO;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta criarPauta(PautaDTO dto) {
        Pauta pauta = new Pauta(dto.getDescricao());
        return pautaRepository.save(pauta);
    }

    public Pauta buscarPauta(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
    }
}