package br.com.votacao.api.service;

import br.com.votacao.api.dto.ResultadoVotacaoDTO;
import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.entity.Voto;
import br.com.votacao.api.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoService sessaoService;

    // Registrar voto
    public Voto votar(VotoDTO dto) {
        // Buscar sessão
        Sessao sessao = sessaoService.buscarSessao(dto.getSessaoId());

        // Verificar se sessão está aberta
        if (!sessaoService.sessaoEstaAberta(sessao)) {
            throw new RuntimeException("Sessão de votação encerrada");
        }

        // Verificar se CPF já votou
        if (votoRepository.findVotoPorCpfNaSessao(sessao.getId(), dto.getCpfAssociado()).isPresent()) {
            throw new RuntimeException("CPF já votou nesta sessão");
        }

        // Registrar voto
        Voto voto = new Voto(sessao, dto.getCpfAssociado(), dto.getVoto());
        return votoRepository.save(voto);
    }

    // Contabilizar resultado da votação
    public ResultadoVotacaoDTO obterResultado(Long sessaoId) {
        Sessao sessao = sessaoService.buscarSessao(sessaoId);

        Long votosSim = votoRepository.countVotosSim(sessaoId);
        Long votosNao = votoRepository.countVotosNao(sessaoId);
        Long totalVotos = votosSim + votosNao;

        // Determinar resultado
        String resultado = votosSim > votosNao ? "APROVADO" : "REPROVADO";

        return new ResultadoVotacaoDTO(
                sessao.getPauta().getId(),
                sessao.getPauta().getDescricao(),
                totalVotos,
                votosSim,
                votosNao,
                resultado
        );
    }
}