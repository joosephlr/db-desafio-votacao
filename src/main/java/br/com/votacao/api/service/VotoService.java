package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.dto.ResultadoVotacaoDTO;
import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.entity.Voto;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import br.com.votacao.api.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private CpfValidacaoService cpfValidacaoService;

    // Registrar voto
    public Voto votar(VotoDTO dto) {

        // 1️⃣ Validar CPF (novo!)
        try {
            CpfValidacaoDTO validacao = cpfValidacaoService.validarCpf(dto.getCpfAssociado());

            // Verificar se usuário pode votar
            if (VotoStatus.UNABLE_TO_VOTE.equals(validacao.getStatus())) {
                throw new RuntimeException("CPF não está autorizado para votar");
            }
        } catch (CpfInvalidoException e) {
            throw new RuntimeException("CPF inválido");
        }

        // 2️⃣ Buscar sessão
        Sessao sessao = sessaoService.buscarSessao(dto.getSessaoId());

        // 3️⃣ Verificar se sessão está aberta
        if (!sessaoService.sessaoEstaAberta(sessao)) {
            throw new RuntimeException("Sessão de votação encerrada");
        }

        // 4️⃣ Verificar se CPF já votou
        if (votoRepository.findVotoPorCpfNaSessao(sessao.getId(), dto.getCpfAssociado()).isPresent()) {
            throw new RuntimeException("CPF já votou nesta sessão");
        }

        // 5️⃣ Registrar voto
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