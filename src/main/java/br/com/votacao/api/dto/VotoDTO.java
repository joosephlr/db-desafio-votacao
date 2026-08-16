package br.com.votacao.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {

    @NotNull(message = "ID da sessão é obrigatório")
    private Long sessaoId;

    @NotBlank(message = "CPF do associado é obrigatório")
    private String cpfAssociado;

    @NotNull(message = "Voto é obrigatório")
    private Boolean voto;
}