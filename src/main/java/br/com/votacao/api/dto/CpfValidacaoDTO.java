package br.com.votacao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpfValidacaoDTO {
    private String status; // "ABLE_TO_VOTE" ou "UNABLE_TO_VOTE"
}