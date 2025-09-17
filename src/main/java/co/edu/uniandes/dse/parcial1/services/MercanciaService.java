package co.edu.uniandes.dse.parcial1.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;

import jakarta.transaction.Transactional;
import java.util.Optional;


@Slf4j
@Service
public class MercanciaService {
    /*No se puede registrar una mercancía sin código de barras único. 
    o El nombre de la mercancía es obligatorio y no puede estar vacío. 
    o La fecha de recepción no puede ser posterior a la fecha actual. */
    @Autowired
    MercanciaRepository mercanciaRepository;
    
    @Transactional
    public MercanciaEntity crearMercancia(MercanciaEntity mercancia) throws IllegalOperationException {
        log.info("Inicio de la creación de la mercancía");

        LocalDateTime actual = LocalDateTime.now();
        Optional<MercanciaEntity> existing = mercanciaRepository.findByCodigoBarras(mercancia.getCodigoBarras());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Una mercancía con este código de barras ya existe");
        }
        if (mercancia.getNombre() == null){
            throw new IllegalOperationException("El nombre de la mercancía no puede ser vacío");
        }
        if(mercancia.getFechaRecepcion().isBefore(actual)){
            throw new IllegalOperationException("La fecha de la recepción de la mercancía no puede ser posterior a la actual");
        }
        return mercanciaRepository.save(mercancia);
    }
}
