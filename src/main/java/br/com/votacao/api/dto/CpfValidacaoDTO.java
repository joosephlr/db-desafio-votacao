package br.com.votacao.api.dto;

import br.com.votacao.api.enums.VotoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpfValidacaoDTO {
    private VotoStatus status;
}