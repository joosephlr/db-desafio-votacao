package br.com.votacao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDTO {

    @NotNull(message = "ID da pauta é obrigatório")
    private Long pautaId;

    private Long duracaoEmSegundos;

    public Long getDuracaoEmSegundos() {
        return duracaoEmSegundos != null ? duracaoEmSegundos : 60L;
    }
}