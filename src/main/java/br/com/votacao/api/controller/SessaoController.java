package br.com.votacao.api.controller;

import br.com.votacao.api.dto.SessaoDTO;
import br.com.votacao.api.entity.Sessao;
import br.com.votacao.api.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    // Abrir sessão de votação
    // POST http://localhost:8080/api/v1/sessoes
    @PostMapping
    public ResponseEntity<Sessao> abrirSessao(@Valid @RequestBody SessaoDTO dto) {
        Sessao sessao = sessaoService.abrirSessao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }

    // Buscar sessão por ID
    // GET http://localhost:8080/api/v1/sessoes/1
    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscarSessao(@PathVariable Long id) {
        Sessao sessao = sessaoService.buscarSessao(id);
        return ResponseEntity.ok(sessao);
    }
}