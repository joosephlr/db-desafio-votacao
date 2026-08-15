package br.com.votacao.api.service;

import br.com.votacao.api.dto.SessaoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaService pautaService;

    // Abrir sessão de votação
    public Sessao abrirSessao(SessaoDTO dto) {
        Pauta pauta = pautaService.buscarPauta(dto.getPautaId());

        LocalDateTime dataAbertura = LocalDateTime.now();
        Long duracao = dto.getDuracaoEmSegundos();
        LocalDateTime dataFechamento = dataAbertura.plusSeconds(duracao);

        Sessao sessao = new Sessao(pauta, dataAbertura, dataFechamento);
        return sessaoRepository.save(sessao);
    }

    // Buscar sessão ativa de uma pauta
    public Sessao buscarSessaoAtiva(Long pautaId) {
        return sessaoRepository.findSessaoAtivaPorPauta(pautaId)
                .orElseThrow(() -> new RuntimeException("Sessão ativa não encontrada para esta pauta"));
    }

    // Buscar sessão por ID
    public Sessao buscarSessao(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }

    // Verificar se sessão está aberta
    public Boolean sessaoEstaAberta(Sessao sessao) {
        return sessao.getAtiva() && LocalDateTime.now().isBefore(sessao.getDataFechamento());
    }
}