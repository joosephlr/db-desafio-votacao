package br.com.votacao.api.repository;

import br.com.votacao.api.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    // JpaRepository já fornece: save, findById, findAll, delete, etc
}
