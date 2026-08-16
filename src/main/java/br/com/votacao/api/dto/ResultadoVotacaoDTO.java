package br.com.votacao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoVotacaoDTO {

    private Long pautaId;
    private String descricaoPauta;
    private Long totalVotos;
    private Long votosSim;
    private Long votosNao;
    private String resultado;
}