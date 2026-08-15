package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.exception.CpfInvalidoException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfValidacaoService {

    private final Random random = new Random();

    /**
     * Valida CPF de forma aleatória (Fake/Mock)
     * 50% de chance de CPF válido
     * 50% de chance de CPF inválido (retorna 404)
     */
    public CpfValidacaoDTO validarCpf(String cpf) {

        // Validar formato básico do CPF
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new CpfInvalidoException("Formato de CPF inválido");
        }

        // Gerar resultado aleatório (50% chance de ser válido)
        boolean cpfValido = random.nextBoolean();

        if (!cpfValido) {
            throw new CpfInvalidoException("CPF inválido ou não encontrado");
        }

        // CPF válido - agora decide se pode votar ou não (50% chance)
        String status = random.nextBoolean() ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE";

        return new CpfValidacaoDTO(status);
    }
}