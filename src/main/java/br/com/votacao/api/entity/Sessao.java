package br.com.votacao.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "sessoes",
    indexes = {
        @Index(name = "idx_pauta_ativa", columnList = "pauta_id, ativa")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    @Column(nullable = false)
    private LocalDateTime dataFechamento;

    @Column(nullable = false)
    private Boolean ativa;

    public Sessao(Pauta pauta, LocalDateTime dataAbertura, LocalDateTime dataFechamento) {
        this.pauta = pauta;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.ativa = true;
    }
}