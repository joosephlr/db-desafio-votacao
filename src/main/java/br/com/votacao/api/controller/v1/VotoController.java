package br.com.votacao.api.controller.v1;

import br.com.votacao.api.dto.ResultadoVotacaoDTO;
import br.com.votacao.api.dto.VotoDTO;
import br.com.votacao.api.entity.Voto;
import br.com.votacao.api.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votos")
@AllArgsConstructor
@Tag(name = "Votos", description = "Registrar votos e obter resultados")
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    @Operation(summary = "Registrar voto", description = "Registra um novo voto em uma sessão de votação")
    public ResponseEntity<Voto> votar(@Valid @RequestBody VotoDTO dto) {

        Voto voto = votoService.votar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(voto);
    }

    @GetMapping("/resultado/{sessaoId}")
    @Operation(summary = "Obter resultado da votação", description = "Retorna os resultados consolidados de uma sessão de votação")
    public ResponseEntity<ResultadoVotacaoDTO> obterResultado(@PathVariable Long sessaoId) {
        
        ResultadoVotacaoDTO resultado = votoService.obterResultado(sessaoId);

        return ResponseEntity.ok(resultado);
    }
}