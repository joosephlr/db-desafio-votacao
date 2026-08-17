package br.com.votacao.api.controller.v1;

import br.com.votacao.api.dto.SessaoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessoes")
@AllArgsConstructor
@Tag(name = "Sessões", description = "Gerenciar sessões de votação")
public class SessaoController {

    private final SessaoService sessaoService;

    @PostMapping
    @Operation(summary = "Abrir sessão de votação", description = "Abre uma nova sessão para votação em uma pauta")
    public ResponseEntity<Sessao> abrirSessao(@Valid @RequestBody SessaoDTO dto) {
        Sessao sessao = sessaoService.abrirSessao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sessão por ID", description = "Retorna os detalhes de uma sessão específica")
    public ResponseEntity<Sessao> buscarSessao(@PathVariable Long id) {
        Sessao sessao = sessaoService.buscarSessao(id);
        return ResponseEntity.ok(sessao);
    }
}