package br.com.votacao.api.controller;

import br.com.votacao.api.dto.ResultadoVotacaoDTO;
import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Voto;
import br.com.votacao.api.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    // Registrar voto
    // POST http://localhost:8080/api/v1/votos
    @PostMapping
    public ResponseEntity<Voto> votar(@Valid @RequestBody VotoDTO dto) {
        Voto voto = votoService.votar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(voto);
    }

    // Obter resultado da votação
    // GET http://localhost:8080/api/v1/votos/resultado/1
    @GetMapping("/resultado/{sessaoId}")
    public ResponseEntity<ResultadoVotacaoDTO> obterResultado(@PathVariable Long sessaoId) {
        ResultadoVotacaoDTO resultado = votoService.obterResultado(sessaoId);
        return ResponseEntity.ok(resultado);
    }
}