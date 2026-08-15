package br.com.votacao.api.repository;

import br.com.votacao.api.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    // Verificar se um CPF já votou em uma sessão
    @Query("SELECT v FROM Voto v WHERE v.sessao.id = :sessaoId AND v.cpfAssociado = :cpf")
    Optional<Voto> findVotoPorCpfNaSessao(@Param("sessaoId") Long sessaoId, @Param("cpf") String cpf);

    // Encontrar todos os votos de uma sessão
    @Query("SELECT v FROM Voto v WHERE v.sessao.id = :sessaoId")
    List<Voto> findVotosPorSessao(@Param("sessaoId") Long sessaoId);

    // Contar votos SIM em uma sessão
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.sessao.id = :sessaoId AND v.voto = true")
    Long countVotosSim(@Param("sessaoId") Long sessaoId);

    // Contar votos NÃO em uma sessão
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.sessao.id = :sessaoId AND v.voto = false")
    Long countVotosNao(@Param("sessaoId") Long sessaoId);
}