package br.com.votacao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaDTO {

    @NotBlank(message = "Descrição da pauta é obrigatória")
    private String descricao;
}