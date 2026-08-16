package br.com.votacao.api.service;

import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Pauta;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Testes de Performance do VotoService")
class VotoPerformanceServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(VotoPerformanceServiceTest.class);

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private CpfValidacaoService cpfValidacaoService;

    @InjectMocks
    private VotoService votoService;

    private Sessao sessaoMock;
    private Pauta pautaMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        pautaMock = new Pauta();
        pautaMock.setId(1L);
        pautaMock.setDescricao("Eleição");

        sessaoMock = new Sessao();
        sessaoMock.setId(1L);
        sessaoMock.setPauta(pautaMock);
        sessaoMock.setAtiva(true);
        sessaoMock.setDataFechamento(LocalDateTime.now().plusHours(1));
    }

    @Test
    @DisplayName("Deve processar 10000 votos em menos de 30 segundos")
    void testPerformance10000Votos() {

        int quantidadeVotos = 10000;
        long inicio = System.currentTimeMillis();

        when(sessaoService.buscarSessao(1L)).thenReturn(sessaoMock);
        when(sessaoService.sessaoEstaAberta(sessaoMock)).thenReturn(true);
        when(cpfValidacaoService.validarCpf(any())).thenReturn(new br.com.votacao.api.dto.CpfValidacaoDTO(VotoStatus.ABLE_TO_VOTE));
        when(votoRepository.findVotoPorCpfNaSessao(any(), any())).thenReturn(Optional.empty());
        when(votoRepository.save(any(Voto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        for (int i = 0; i < quantidadeVotos; i++) {
            VotoDTO dto = new VotoDTO(1L, String.format("%011d", i), i % 2 == 0);
            votoService.votar(dto);
        }

        long fim = System.currentTimeMillis();
        long duracao = fim - inicio;

        assertTrue(duracao < 30000,
                String.format("Processamento de %d votos levou %d ms (máximo: 30000 ms)",
                        quantidadeVotos, duracao));

        logger.info("Processou {} votos em {} ms ({} votos/segundo)",
                quantidadeVotos, duracao, String.format("%.2f", (quantidadeVotos * 1000.0) / duracao));
    }

    @Test
    @DisplayName("Deve contar votos eficientemente")
    void testPerformanceContagemVotos() {

        long sessaoId = 1L;

        when(votoRepository.countVotosSim(sessaoId)).thenReturn(5000L);
        when(votoRepository.countVotosNao(sessaoId)).thenReturn(5000L);

        long inicio = System.currentTimeMillis();

        Long votosSim = votoRepository.countVotosSim(sessaoId);
        Long votosNao = votoRepository.countVotosNao(sessaoId);

        long fim = System.currentTimeMillis();

        assertEquals(5000L, votosSim);
        assertEquals(5000L, votosNao);
        assertTrue((fim - inicio) < 1000, "Contagem deveria ser rápida (< 1 segundo)");

        logger.info("Contagem de votos completada em {} ms", fim - inicio);
    }
}