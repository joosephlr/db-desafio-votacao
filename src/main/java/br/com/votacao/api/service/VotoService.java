package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.dto.ResultadoVotacaoDTO;
import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.entity.Voto;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import br.com.votacao.api.repository.VotoRepository;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final SessaoService sessaoService;
    private final CpfValidacaoService cpfValidacaoService;

    public VotoService(VotoRepository votoRepository, SessaoService sessaoService, CpfValidacaoService cpfValidacaoService) {
        this.votoRepository = votoRepository;
        this.sessaoService = sessaoService;
        this.cpfValidacaoService = cpfValidacaoService;
    }

    public Voto votar(VotoDTO dto) {

        Sessao sessao = sessaoService.buscarSessao(dto.getSessaoId());

        if (!sessaoService.sessaoEstaAberta(sessao)) {
            throw new RuntimeException("Sessão de votação encerrada");
        }

        try {
            CpfValidacaoDTO validacao = cpfValidacaoService.validarCpf(dto.getCpfAssociado());

            if (VotoStatus.UNABLE_TO_VOTE.equals(validacao.getStatus())) {
               throw new RuntimeException("CPF não está autorizado para votar");
            }
        } catch (CpfInvalidoException e) {
            throw new RuntimeException("CPF inválido");
        }

        if (votoRepository.findVotoPorCpfNaSessao(sessao.getId(), dto.getCpfAssociado()).isPresent()) {
            throw new RuntimeException("CPF já votou nesta sessão");
        }

        Voto voto = new Voto(sessao, dto.getCpfAssociado(), dto.getVoto());

        return votoRepository.save(voto);
    }

    public ResultadoVotacaoDTO obterResultado(Long sessaoId) {
        Sessao sessao = sessaoService.buscarSessao(sessaoId);

        Long votosSim = votoRepository.countVotosSim(sessaoId);
        Long votosNao = votoRepository.countVotosNao(sessaoId);
        Long totalVotos = votosSim + votosNao;

        String resultado = "";

        if(votosSim > votosNao)
        {
            resultado = "APROVADO";
        }
        else if(votosNao > votosSim)
        {
            resultado = "REPROVADO";
        }
        else
        {
            resultado = "EMPATE";
        }

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