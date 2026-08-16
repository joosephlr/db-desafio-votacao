package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfValidacaoService {

    private final Random random = new Random();

    /**
     * Valida CPF usando algoritmo real + retorna status aleatório
     */
    public CpfValidacaoDTO validarCpf(String cpf) {

        if (cpf == null || cpf.isEmpty()) {
            throw new CpfInvalidoException("CPF não pode ser vazio");
        }

        // Validar CPF
        if (!validarCpfAlgoritmo(cpf)) {
            throw new CpfInvalidoException("CPF inválido");
        }

        // CPF válido - retorna status aleatório
        VotoStatus status = random.nextBoolean() ?
                VotoStatus.ABLE_TO_VOTE :
                VotoStatus.UNABLE_TO_VOTE;

        return new CpfValidacaoDTO(status);
    }

    /**
     * Valida CPF usando o algoritmo real do CPF brasileiro
     */
    private boolean validarCpfAlgoritmo(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int primeiroDigito = calcularDigito(cpf.substring(0, 9), 10);
        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        int segundoDigito = calcularDigito(cpf.substring(0, 9) + primeiroDigito, 11);
        if (segundoDigito != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }

    private int calcularDigito(String sequencia, int multiplicador) {
        int soma = 0;
        for (int i = 0; i < sequencia.length(); i++) {
            soma += Character.getNumericValue(sequencia.charAt(i)) * (multiplicador - i);
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}