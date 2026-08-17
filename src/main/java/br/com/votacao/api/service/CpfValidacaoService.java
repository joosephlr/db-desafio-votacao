package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfValidacaoService {

    private final Random random;

    public CpfValidacaoService() {
        this(new Random());
    }

    public CpfValidacaoService(Random random) {
        this.random = random;
    }

    public CpfValidacaoDTO validarCpf(String cpf) {

        if (cpf == null || cpf.isEmpty()) {
            throw new CpfInvalidoException("CPF não pode ser vazio");
        }

        if (!validarCpfAlgoritmo(cpf)) {
            throw new CpfInvalidoException("CPF inválido");
        }

        return new CpfValidacaoDTO(VotoStatus.ABLE_TO_VOTE);
    }

    public CpfValidacaoDTO validarCpfRandom(String cpf) {

        if (cpf == null || cpf.isEmpty()) {
            throw new CpfInvalidoException("CPF não pode ser vazio");
        }

        boolean cpfValido = random.nextBoolean();

        if (!cpfValido) {
            throw new CpfInvalidoException("CPF inválido");
        }

        VotoStatus status = random.nextBoolean() ? VotoStatus.ABLE_TO_VOTE : VotoStatus.UNABLE_TO_VOTE;

        return new CpfValidacaoDTO(status);
    }

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