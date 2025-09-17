package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(UbicacionBodegaService.class)
public class UbicacionBodegaServiceTest {

    @Autowired
    private UbicacionBodegaService ubicacionBodegaService;
    

    @Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<UbicacionBodegaEntity> ubicacionBodegaList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();
	}

    private void insertData() {
		for (int i = 0; i < 5; i++) {
			UbicacionBodegaEntity ubicacionBodegaEntity = factory.manufacturePojo(UbicacionBodegaEntity.class);
			entityManager.persist(ubicacionBodegaEntity);
			ubicacionBodegaList.add(ubicacionBodegaEntity);
		}
	}

    @Test
	void testCreateUbicacionBodega() throws IllegalOperationException {
		UbicacionBodegaEntity newUbicacionBodega = factory.manufacturePojo(UbicacionBodegaEntity.class);
		newUbicacionBodega.setCanasta("Canasta1");
        newUbicacionBodega.setNumeroEstante(1);
        newUbicacionBodega.setPesoMaxCanasta(30);
		UbicacionBodegaEntity result = ubicacionBodegaService.crearUbicacionBodega(newUbicacionBodega);
		assertNotNull(result);
        UbicacionBodegaEntity ubicacionBodega2 = entityManager.find(UbicacionBodegaEntity.class, result.getId());
        assertEquals(newUbicacionBodega.getCanasta(), ubicacionBodega2.getCanasta());
        assertEquals(newUbicacionBodega.getNumeroEstante(), ubicacionBodega2.getNumeroEstante());
        assertEquals(newUbicacionBodega.getPesoMaxCanasta(), ubicacionBodega2.getPesoMaxCanasta());
	}

    @Test
    void testCreateUbicacionBodegaNumeroEstanteNegativo() throws IllegalOperationException {
        assertThrows(IllegalOperationException.class, ()->{
			UbicacionBodegaEntity newUbicacionBodega = factory.manufacturePojo(UbicacionBodegaEntity.class);
            newUbicacionBodega.setCanasta("Canasta1");
            newUbicacionBodega.setNumeroEstante(-1);
            newUbicacionBodega.setPesoMaxCanasta(30);
            ubicacionBodegaService.crearUbicacionBodega(newUbicacionBodega);
		});
	}
    
}
