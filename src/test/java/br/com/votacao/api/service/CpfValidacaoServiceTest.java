package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.exception.CpfInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do CpfValidacaoService")
class CpfValidacaoServiceTest {

    private CpfValidacaoService cpfValidacaoClient;

    @BeforeEach
    void setup() {
        cpfValidacaoClient = new CpfValidacaoService();
    }

    @Test
    @DisplayName("Deve validar CPF com formato correto")
    void testValidarCpfFormatoCorreto() {
        // Arrange
        String cpf = "12345678900"; // 11 dígitos

        // Act & Assert
        // Pode retornar ABLE_TO_VOTE ou UNABLE_TO_VOTE ou lançar exceção
        // Vamos testar múltiplas vezes pois é aleatório
        boolean temResultado = false;
        for (int i = 0; i < 100; i++) {
            try {
                CpfValidacaoDTO resultado = cpfValidacaoClient.validarCpf(cpf);
                assertNotNull(resultado);
                assertTrue(resultado.getStatus().equals("ABLE_TO_VOTE") ||
                        resultado.getStatus().equals("UNABLE_TO_VOTE"));
                temResultado = true;
                break;
            } catch (CpfInvalidoException e) {
                // Esperado às vezes (CPF inválido aleatório)
            }
        }
        assertTrue(temResultado, "Deveria ter validado o CPF com sucesso em alguma tentativa");
    }

    @Test
    @DisplayName("Deve rejeitar CPF com menos de 11 dígitos")
    void testValidarCpfComMenosDeDezDigitos() {
        // Arrange
        String cpf = "1234567890"; // 10 dígitos

        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoClient.validarCpf(cpf));

        assertEquals("Formato de CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com mais de 11 dígitos")
    void testValidarCpfComMaisDeDezDigitos() {
        // Arrange
        String cpf = "123456789001"; // 12 dígitos

        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoClient.validarCpf(cpf));

        assertEquals("Formato de CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF com caracteres não numéricos")
    void testValidarCpfComCaracteresNaoNumericos() {
        // Arrange
        String cpf = "1234567890a"; // Contém letra

        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoClient.validarCpf(cpf));

        assertEquals("Formato de CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar CPF nulo")
    void testValidarCpfNulo() {
        // Arrange
        String cpf = null;

        // Act & Assert
        CpfInvalidoException exception = assertThrows(CpfInvalidoException.class,
                () -> cpfValidacaoClient.validarCpf(cpf));

        assertEquals("Formato de CPF inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar status válido quando CPF é aceito")
    void testRetornarStatusValido() {
        // Arrange
        String cpf = "12345678900";

        // Act & Assert
        // Testar múltiplas vezes pois é aleatório
        boolean temAbleToVote = false;
        boolean temUnableToVote = false;

        for (int i = 0; i < 1000; i++) {
            try {
                CpfValidacaoDTO resultado = cpfValidacaoClient.validarCpf(cpf);
                if ("ABLE_TO_VOTE".equals(resultado.getStatus())) {
                    temAbleToVote = true;
                } else if ("UNABLE_TO_VOTE".equals(resultado.getStatus())) {
                    temUnableToVote = true;
                }
                if (temAbleToVote && temUnableToVote) {
                    break;
                }
            } catch (CpfInvalidoException e) {
                // Esperado às vezes
            }
        }

        assertTrue(temAbleToVote, "Deveria ter retornado ABLE_TO_VOTE em alguma tentativa");
        assertTrue(temUnableToVote, "Deveria ter retornado UNABLE_TO_VOTE em alguma tentativa");
    }
}