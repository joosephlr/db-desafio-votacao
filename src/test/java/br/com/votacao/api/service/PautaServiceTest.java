package br.com.votacao.api.service;

import br.com.votacao.api.dto.PautaDTO;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Testes do PautaService")
class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar uma pauta com sucesso")
    void testCriarPauta() {
        // Arrange (Preparar)
        PautaDTO dto = new PautaDTO("Eleição de presidente");
        Pauta pautaEsperada = new Pauta("Eleição de presidente");

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaEsperada);

        // Act (Agir)
        Pauta pautaCriada = pautaService.criarPauta(dto);

        // Assert (Afirmar)
        assertNotNull(pautaCriada);
        assertEquals("Eleição de presidente", pautaCriada.getDescricao());
        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }

    @Test
    @DisplayName("Deve buscar pauta por ID com sucesso")
    void testBuscarPauta() {
        // Arrange
        Long pautaId = 1L;
        Pauta pautaEsperada = new Pauta(1L, "Eleição de presidente", null);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pautaEsperada));

        // Act
        Pauta pautaEncontrada = pautaService.buscarPauta(pautaId);

        // Assert
        assertNotNull(pautaEncontrada);
        assertEquals(1L, pautaEncontrada.getId());
        assertEquals("Eleição de presidente", pautaEncontrada.getDescricao());
    }

    @Test
    @DisplayName("Deve lançar exceção quando pauta não existe")
    void testBuscarPautaInexistente() {
        // Arrange
        Long pautaId = 999L;
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> pautaService.buscarPauta(pautaId));

        assertEquals("Pauta não encontrada", exception.getMessage());
    }
}