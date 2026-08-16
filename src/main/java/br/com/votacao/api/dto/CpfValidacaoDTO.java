package br.com.votacao.api.dto;

import br.com.votacao.api.enums.VotoStatus;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpfValidacaoDTO {
    @JsonValue
    private VotoStatus status;
}