package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MercanciaService.class)
public class MercanciaServiceTest {
    
    @Autowired
    private MercanciaService mercanciaService;

    @Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<MercanciaEntity> mercanciaList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();
	}

    private void insertData() {
		for (int i = 0; i < 5; i++) {
			MercanciaEntity mercanciaEntity = factory.manufacturePojo(MercanciaEntity.class);
			entityManager.persist(mercanciaEntity);
			mercanciaList.add(mercanciaEntity);
		}
	}

    @Test
	void testCreateMercancia() throws IllegalOperationException {
		MercanciaEntity newMercancia = factory.manufacturePojo(MercanciaEntity.class);
		newMercancia.setCantidadDisponible(10000);
        newMercancia.setCodigoBarras(20000);
        newMercancia.setFechaRecepcion(LocalDateTime.of(2025, 12, 12, 12, 12));
        newMercancia.setNombre("Tubos");
		MercanciaEntity result = mercanciaService.crearMercancia(newMercancia);
		assertNotNull(result);
        MercanciaEntity mercancia2 = entityManager.find(MercanciaEntity.class, result.getId());
        assertEquals(newMercancia.getCantidadDisponible(), mercancia2.getCantidadDisponible());
        assertEquals(newMercancia.getCodigoBarras(), mercancia2.getCodigoBarras());
        assertEquals(newMercancia.getFechaRecepcion(), mercancia2.getFechaRecepcion());
        assertEquals(newMercancia.getNombre(), mercancia2.getNombre());
	}

    @Test
    void testCreateMercanciaPasada() throws IllegalOperationException {
        assertThrows(IllegalOperationException.class, ()->{
			MercanciaEntity newMercancia = factory.manufacturePojo(MercanciaEntity.class);
            newMercancia.setCantidadDisponible(10000);
            newMercancia.setCodigoBarras(20000);
            newMercancia.setFechaRecepcion(LocalDateTime.of(2000, 12, 12, 12, 12));
            newMercancia.setNombre("Tubos");
            mercanciaService.crearMercancia(newMercancia);
		});
	}

    @Test
    void testCreateMercanciaSinNombre() throws IllegalOperationException {
        assertThrows(IllegalOperationException.class, ()->{
			MercanciaEntity newMercancia = factory.manufacturePojo(MercanciaEntity.class);
            newMercancia.setCantidadDisponible(10000);
            newMercancia.setCodigoBarras(20000);
            newMercancia.setFechaRecepcion(LocalDateTime.of(2000, 12, 12, 12, 12));
            mercanciaService.crearMercancia(newMercancia);
		});
	}
    
}
