package br.com.votacao.api.service;

import br.com.votacao.api.dto.SessaoDTO;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.repository.SessaoRepository;
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
import static org.mockito.Mockito.*;

@DisplayName("Testes do SessaoService")
class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private SessaoService sessaoService;

    private Pauta pautaMock;
    private Sessao sessaoMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        pautaMock = new Pauta();
        pautaMock.setId(1L);
        pautaMock.setDescricao("Eleição de presidente");

        sessaoMock = new Sessao();
        sessaoMock.setId(1L);
        sessaoMock.setPauta(pautaMock);
        sessaoMock.setAtiva(true);
        sessaoMock.setDataAbertura(LocalDateTime.now());
        sessaoMock.setDataFechamento(LocalDateTime.now().plusMinutes(1));
    }

    @Test
    @DisplayName("Deve abrir uma sessão de votação com duração padrão")
    void testAbrirSessaoComDuracaoPadrao() {
        SessaoDTO dto = new SessaoDTO(1L, null); // null = usa padrão (60 segundos)

        when(pautaService.buscarPauta(1L)).thenReturn(pautaMock);
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoMock);

        Sessao sessaoAbierta = sessaoService.abrirSessao(dto);

        assertNotNull(sessaoAbierta);
        assertTrue(sessaoAbierta.getAtiva());
        assertEquals(pautaMock.getId(), sessaoAbierta.getPauta().getId());
        verify(sessaoRepository, times(1)).save(any(Sessao.class));
    }

    @Test
    @DisplayName("Deve abrir uma sessão com duração customizada")
    void testAbrirSessaoComDuracaoCustomizada() {

        SessaoDTO dto = new SessaoDTO(1L, 300L); // 5 minutos

        when(pautaService.buscarPauta(1L)).thenReturn(pautaMock);
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoMock);

        Sessao sessaoAbierta = sessaoService.abrirSessao(dto);

        assertNotNull(sessaoAbierta);
        assertTrue(sessaoAbierta.getAtiva());
    }

    @Test
    @DisplayName("Deve buscar sessão por ID com sucesso")
    void testBuscarSessaoPorId() {
        Long sessaoId = 1L;

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessaoMock));

        Sessao sessaoEncontrada = sessaoService.buscarSessao(sessaoId);

        assertNotNull(sessaoEncontrada);
        assertEquals(1L, sessaoEncontrada.getId());
        assertTrue(sessaoEncontrada.getAtiva());
    }

    @Test
    @DisplayName("Deve lançar exceção quando sessão não existe")
    void testBuscarSessaoInexistente() {
        Long sessaoId = 999L;

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sessaoService.buscarSessao(sessaoId));

        assertEquals("Sessão não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve verificar que sessão está aberta")
    void testSessaoEstaAberta() {
        Sessao sessaoAberta = new Sessao();
        sessaoAberta.setAtiva(true);
        sessaoAberta.setDataFechamento(LocalDateTime.now().plusMinutes(1));

        Boolean estaAberta = sessaoService.sessaoEstaAberta(sessaoAberta);

        assertTrue(estaAberta);
    }

    @Test
    @DisplayName("Deve verificar que sessão está fechada (inativa)")
    void testSessaoEstaFechadaInativa() {
        Sessao sessaoFechada = new Sessao();
        sessaoFechada.setAtiva(false);
        sessaoFechada.setDataFechamento(LocalDateTime.now().plusMinutes(1));

        Boolean estaAberta = sessaoService.sessaoEstaAberta(sessaoFechada);

        assertFalse(estaAberta);
    }

    @Test
    @DisplayName("Deve verificar que sessão está fechada (data passou)")
    void testSessaoEstaFechadaDataPassa() {
        Sessao sessaoFechada = new Sessao();
        sessaoFechada.setAtiva(true);
        sessaoFechada.setDataFechamento(LocalDateTime.now().minusMinutes(1));

        Boolean estaAberta = sessaoService.sessaoEstaAberta(sessaoFechada);

        assertFalse(estaAberta);
    }

    @Test
    @DisplayName("Deve buscar sessão ativa por pauta")
    void testBuscarSessaoAtivaPorPauta() {
        Long pautaId = 1L;

        when(sessaoRepository.findSessaoAtivaPorPauta(pautaId)).thenReturn(Optional.of(sessaoMock));

        Sessao sessaoEncontrada = sessaoService.buscarSessaoAtiva(pautaId);

        assertNotNull(sessaoEncontrada);
        assertTrue(sessaoEncontrada.getAtiva());
        assertEquals(pautaId, sessaoEncontrada.getPauta().getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não há sessão ativa para pauta")
    void testBuscarSessaoAtivaPautaSemSessao() {
        Long pautaId = 999L;

        when(sessaoRepository.findSessaoAtivaPorPauta(pautaId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sessaoService.buscarSessaoAtiva(pautaId));

        assertEquals("Sessão ativa não encontrada para esta pauta", exception.getMessage());
    }
}