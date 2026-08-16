package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes do CpfValidacaoService")
class CpfValidacaoServiceTest {

    @Autowired
    private CpfValidacaoService cpfValidacaoService;

    @Test
    @DisplayName("Deve rejeitar CPF nulo")
    void testValidarCpfNulo() {
        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf(null));

        assertEquals("CPF não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com menos de 11 dígitos")
    void testValidarCpfComMenosDezDigitos() {
        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("1234567890"));

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com mais de 11 dígitos")
    void testValidarCpfComMaisDezDigitos() {
        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("123456789001"));

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com caracteres não numéricos")
    void testValidarCpfComCaracteresNaoNumericos() {
        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("1234567890a"));

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar CPF válido e retornar status")
    void testValidarCpfValido() {
        // Act & Assert
        CpfValidacaoDTO resultado = cpfValidacaoService.validarCpf("11144477735");

        assertNotNull(resultado);
        assertNotNull(resultado.getStatus());
        assertTrue(
                resultado.getStatus().equals(VotoStatus.ABLE_TO_VOTE) ||
                        resultado.getStatus().equals(VotoStatus.UNABLE_TO_VOTE)
        );
    }

    @Test
    @DisplayName("Deve rejeitar CPF com todos os dígitos iguais")
    void testValidarCpfTodosDigitosIguais() {
        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoService.validarCpf("11111111111"));

        assertEquals("CPF inválido", exception.getMessage());
    }
}