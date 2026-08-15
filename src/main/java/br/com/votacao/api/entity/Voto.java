package br.com.votacao.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "votos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @Column(nullable = false)
    private String cpfAssociado;

    @Column(nullable = false)
    private Boolean voto; // true = Sim, false = Não

    @Column(nullable = false)
    private LocalDateTime dataVoto;

    public Voto(Sessao sessao, String cpfAssociado, Boolean voto) {
        this.sessao = sessao;
        this.cpfAssociado = cpfAssociado;
        this.voto = voto;
        this.dataVoto = LocalDateTime.now();
    }
}