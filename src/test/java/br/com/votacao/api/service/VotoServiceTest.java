package br.com.votacao.api.service;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.entity.Voto;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Testes do VotoService")
public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private CpfValidacaoService cpfValidacaoService;

    @InjectMocks
    private VotoService votoService;

    private Sessao sessaoMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        sessaoMock = new Sessao();
        sessaoMock.setId(1L);
        sessaoMock.setAtiva(true);
        sessaoMock.setDataFechamento(LocalDateTime.now().plusMinutes(1));
    }

    @Test
    @DisplayName("Deve registrar voto com sucesso")
    public void testVotarComSucesso() {

        VotoDTO dto = new VotoDTO(1L, "12345678900", true);
        Voto votoEsperado = new Voto(sessaoMock, "12345678900", true);

        when(cpfValidacaoService.validarCpf("12345678900")).thenReturn(new CpfValidacaoDTO(VotoStatus.ABLE_TO_VOTE));
        when(sessaoService.buscarSessao(1L)).thenReturn(sessaoMock);
        when(sessaoService.sessaoEstaAberta(sessaoMock)).thenReturn(true);
        when(votoRepository.findVotoPorCpfNaSessao(1L, "12345678900")).thenReturn(Optional.empty());
        when(votoRepository.save(any(Voto.class))).thenReturn(votoEsperado);

        Voto votoRegistrado = votoService.votar(dto);

        assertNotNull(votoRegistrado);
        assertEquals("12345678900", votoRegistrado.getCpfAssociado());
        assertTrue(votoRegistrado.getVoto());
    }

    @Test
    @DisplayName("Deve rejeitar voto se CPF já votou")
    public void testVotoDuplicado() {

        VotoDTO dto = new VotoDTO(1L, "12345678900", true);
        Voto votoExistente = new Voto(sessaoMock, "12345678900", true);

        when(cpfValidacaoService.validarCpf("12345678900")).thenReturn(new CpfValidacaoDTO(VotoStatus.ABLE_TO_VOTE));
        when(sessaoService.buscarSessao(1L)).thenReturn(sessaoMock);
        when(sessaoService.sessaoEstaAberta(sessaoMock)).thenReturn(true);
        when(votoRepository.findVotoPorCpfNaSessao(1L, "12345678900")).thenReturn(Optional.of(votoExistente));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> votoService.votar(dto));

        assertEquals("CPF já votou nesta sessão", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar voto se sessão encerrada")
    public void testVotoSessaoEncerrada() {

        VotoDTO dto = new VotoDTO(1L, "12345678900", true);

        when(cpfValidacaoService.validarCpf("12345678900")).thenReturn(new CpfValidacaoDTO(VotoStatus.ABLE_TO_VOTE));
        when(sessaoService.buscarSessao(1L)).thenReturn(sessaoMock);
        when(sessaoService.sessaoEstaAberta(sessaoMock)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> votoService.votar(dto));

        assertEquals("Sessão de votação encerrada", exception.getMessage());
    }
}