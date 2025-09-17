package co.edu.uniandes.dse.parcial1.services;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;

@DataJpaTest
@Transactional
@Import({MercanciaService.class, UbicacionBodegaService.class})
public class MercanciaUbicacionBodegaTest {
    @Autowired
    private MercanciaUbicacionBodegaService mercanciaUbicacionBodegaService;

    @Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private MercanciaEntity mercancia = new MercanciaEntity();
	private List<UbicacionBodegaEntity> ubicacionBodegaList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();
	}

    private void insertData() {
        mercancia = factory.manufacturePojo(MercanciaEntity.class);
		mercancia.setCantidadDisponible(10000);
		mercancia.setCodigoBarras(1);
        mercancia.setFechaRecepcion(LocalDateTime.of(2025, 12, 12, 12, 12));
        mercancia.setNombre("tubos");
        entityManager.persist(mercancia);
		UbicacionBodegaEntity ubicacionBodegaEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
		ubicacionBodegaEntity.getItemsMercancia().add(mercancia);
		entityManager.persist(ubicacionBodegaEntity);
		ubicacionBodegaList.add(ubicacionBodegaEntity);
		mercancia.setUbicacion(ubicacionBodegaEntity);
	}
}
