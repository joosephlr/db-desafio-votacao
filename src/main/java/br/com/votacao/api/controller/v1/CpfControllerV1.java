package br.com.votacao.api.controller.v1;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import br.com.votacao.api.service.CpfValidacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cpf")
@Tag(name = "CPF", description = "Validar CPF e autorização de voto")
public class CpfControllerV1 {

    @Autowired
    private CpfValidacaoService cpfValidacaoService;

    @GetMapping("/validar/{cpf}")
    @Operation(summary = "Validar CPF", description = "Valida um CPF e retorna se o usuário está autorizado a votar")
    public ResponseEntity<CpfValidacaoDTO> validarCpf(@PathVariable String cpf) {
        try {
            CpfValidacaoDTO resultado = cpfValidacaoService.validarCpf(cpf);

            if (VotoStatus.UNABLE_TO_VOTE.equals(resultado.getStatus())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
            }

            return ResponseEntity.ok(resultado);

        } catch (CpfInvalidoException e) {
            throw e;
        }
    }
}