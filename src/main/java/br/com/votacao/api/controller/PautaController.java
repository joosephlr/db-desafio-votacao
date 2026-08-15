package br.com.votacao.api.controller;

import br.com.votacao.api.dto.PautaDTO;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    // Criar nova pauta
    // POST http://localhost:8080/api/v1/pautas
    @PostMapping
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaDTO dto) {
        Pauta pauta = pautaService.criarPauta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pauta);
    }

    // Buscar pauta por ID
    // GET http://localhost:8080/api/v1/pautas/1
    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPauta(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPauta(id);
        return ResponseEntity.ok(pauta);
    }
}