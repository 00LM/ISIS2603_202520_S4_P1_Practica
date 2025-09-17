package co.edu.uniandes.dse.parcial1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface UbicacionBodegaRepository extends JpaRepository<UbicacionBodegaEntity, Long> {

}
