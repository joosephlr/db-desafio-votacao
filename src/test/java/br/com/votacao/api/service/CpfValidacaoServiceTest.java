package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do CpfValidacaoService")
class CpfValidacaoServiceTest {

    private CpfValidacaoService cpfValidacaoService;

    @Test
    @DisplayName("Deve rejeitar CPF nulo")
    void testValidarCpfNulo() {
        cpfValidacaoService = new CpfValidacaoService();

        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf(null));

        assertEquals("CPF não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com menos de 11 dígitos")
    void testValidarCpfComMenosDezDigitos() {
        cpfValidacaoService = new CpfValidacaoService();

        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("1234567890"));

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com mais de 11 dígitos")
    void testValidarCpfComMaisDezDigitos() {
        cpfValidacaoService = new CpfValidacaoService();

        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("123456789001"));

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com caracteres não numéricos")
    void testValidarCpfComCaracteresNaoNumericos() {
        cpfValidacaoService = new CpfValidacaoService();

        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("1234567890a"));

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar CPF válido e retornar ABLE_TO_VOTE")
    void testValidarCpfValidoAbleToVote() {
        cpfValidacaoService = new CpfValidacaoService();

        CpfValidacaoDTO resultado = cpfValidacaoService.validarCpf("11144477735");

        assertNotNull(resultado);
        assertEquals(VotoStatus.ABLE_TO_VOTE, resultado.getStatus());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com todos os dígitos iguais")
    void testValidarCpfTodosDigitosIguais() {
        cpfValidacaoService = new CpfValidacaoService();

        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("11111111111"));

        assertEquals("CPF inválido", exception.getMessage());
    }
}