package br.com.votacao.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDTO {

    @NotNull(message = "ID da pauta é obrigatório")
    private Long pautaId;

    @Positive(message = "A duração deve ser maior que zero.")
    private Long duracaoEmSegundos;

    public Long getDuracaoEmSegundos() {
        return duracaoEmSegundos != null ? duracaoEmSegundos : 60L;
    }
}