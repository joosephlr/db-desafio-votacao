package br.com.votacao.api.controller;

import br.com.votacao.api.dto.CpfValidacaoDTO;
import br.com.votacao.api.enums.VotoStatus;
import br.com.votacao.api.exception.CpfInvalidoException;
import br.com.votacao.api.service.CpfValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cpf")
public class CpfController {

    @Autowired
    private CpfValidacaoService cpfValidacaoService;

    // Validar CPF
    // GET http://localhost:8080/api/v1/cpf/validar/12345678900
    @GetMapping("/validar/{cpf}")
    public ResponseEntity<CpfValidacaoDTO> validarCpf(@PathVariable String cpf) {
        try {
            CpfValidacaoDTO resultado = cpfValidacaoService.validarCpf(cpf);

            // Se UNABLE_TO_VOTE, retorna 404 com o DTO (sem mensagem de erro)
            if (VotoStatus.UNABLE_TO_VOTE.equals(resultado.getStatus())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
            }

            // Se ABLE_TO_VOTE, retorna 200
            return ResponseEntity.ok(resultado);

        } catch (CpfInvalidoException e) {
            // CPF inválido - retorna 404 (o GlobalExceptionHandler vai tratar)
            throw e;
        }
    }
}