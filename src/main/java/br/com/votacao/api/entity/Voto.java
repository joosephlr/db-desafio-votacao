package br.com.votacao.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "votos",
        indexes = {
                @Index(name = "idx_sessao_cpf", columnList = "sessao_id, cpf_associado", unique = true),
                @Index(name = "idx_sessao_voto", columnList = "sessao_id, voto"),
                @Index(name = "idx_cpf", columnList = "cpf_associado")
        }
)
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

    @Column(name = "cpf_associado", nullable = false)
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