package br.com.votacao.api.repository;

import br.com.votacao.api.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    // Encontrar sessão ativa por pauta
    @Query("SELECT s FROM Sessao s WHERE s.pauta.id = :pautaId AND s.ativa = true")
    Optional<Sessao> findSessaoAtivaPorPauta(@Param("pautaId") Long pautaId);
}