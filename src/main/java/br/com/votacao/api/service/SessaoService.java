package br.com.votacao.api.service;

import br.com.votacao.api.dto.SessaoDTO;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final PautaService pautaService;

    public SessaoService(SessaoRepository sessaoRepository, PautaService pautaService) {
        this.sessaoRepository = sessaoRepository;
        this.pautaService = pautaService;
    }

    public Sessao abrirSessao(SessaoDTO dto) {
        Pauta pauta = pautaService.buscarPauta(dto.getPautaId());

        Long duracao = dto.getDuracaoEmSegundos();

        LocalDateTime dataAbertura = LocalDateTime.now();
        LocalDateTime dataFechamento = dataAbertura.plusSeconds(duracao);

        Sessao sessao = new Sessao(pauta, dataAbertura, dataFechamento);
        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessaoAtiva(Long pautaId) {
        return sessaoRepository.findSessaoAtivaPorPauta(pautaId)
                .orElseThrow(() -> new RuntimeException("Sessão ativa não encontrada para esta pauta"));
    }

    public Sessao buscarSessao(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }

    public Boolean sessaoEstaAberta(Sessao sessao) {
        return sessao.getAtiva() && LocalDateTime.now().isBefore(sessao.getDataFechamento());
    }
}