package co.edu.uniandes.dse.parcial1.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import lombok.extern.slf4j.Slf4j;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;


@Slf4j
@Service
public class MercanciaUbicacionBodegaService {

    @Autowired
	private MercanciaRepository mercanciaRepository;

	@Autowired
	private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
	public UbicacionBodegaEntity addUbicacionBodega(Long mercanciaId, Long ubicacionBodegaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de asociarle un ubicacionBodega al mercancia con id = {0}", mercanciaId);
		Optional<MercanciaEntity> mercanciaEntity = mercanciaRepository.findById(mercanciaId);
		Optional<UbicacionBodegaEntity> ubicacionBodegaEntity = ubicacionBodegaRepository.findById(ubicacionBodegaId);

        if (mercanciaEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontró un mercancia con id " + mercanciaId);
        }
        if (ubicacionBodegaEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontró un ubicacionBodega con id " + ubicacionBodegaId);
        }
        mercanciaEntity.get().setUbicacion(ubicacionBodegaEntity.get());
        return ubicacionBodegaEntity.get();
		
	}

    @Transactional
	public MercanciaEntity addMercanciaEntity(Long mercanciaId, Long ubicacionBodegaId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de asociarle una mercancía a la bodega con id = {0}", ubicacionBodegaId);
		Optional<MercanciaEntity> mercanciaEntity = mercanciaRepository.findById(mercanciaId);
		Optional<UbicacionBodegaEntity> ubicacionBodegaEntity = ubicacionBodegaRepository.findById(ubicacionBodegaId);

        if (mercanciaEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontró un mercancia con id " + mercanciaId);
        }
        if (ubicacionBodegaEntity.isEmpty()){
            throw new EntityNotFoundException("No se encontró un ubicacionBodega con id " + ubicacionBodegaId);
        }
        ubicacionBodegaEntity.get().getItemsMercancia().add(mercanciaEntity.get());
        return mercanciaEntity.get();
		
	}
}
