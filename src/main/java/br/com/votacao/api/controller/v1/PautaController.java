package br.com.votacao.api.controller.v1;

import br.com.votacao.api.dto.PautaDTO;
import br.com.votacao.api.entity.Pauta;
import br.com.votacao.api.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas")
@Tag(name = "Pautas", description = "Gerenciar pautas de votação")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    @Operation(summary = "Criar nova pauta", description = "Cria uma nova pauta para votação")
    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaDTO dto) {
        Pauta pauta = pautaService.criarPauta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pauta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pauta por ID", description = "Retorna os detalhes de uma pauta específica")
    public ResponseEntity<Pauta> buscarPauta(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPauta(id);
        return ResponseEntity.ok(pauta);
    }
}