package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Import(EstadioService.class)
class EstadioServiceTest {

    @Autowired
    private EstadioService estadioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        clearData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
    }

    @Test
    void testCreateEstadioSuccess() throws IllegalOperationException {
        EstadioEntity newEstadio = factory.manufacturePojo(EstadioEntity.class);
        newEstadio.setNombreCiudad("Bogota");
        newEstadio.setAforoMaximo(20000);
        newEstadio.setPrecioAlquiler((long) 200000.0);
        EstadioEntity result = estadioService.createEstadio(newEstadio);

        assertNotNull(result);
        EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());
        assertEquals(newEstadio.getNombre(), entity.getNombre());
    }

    @Test
    void testCreateEstadioFailPrecioAlquiler() {
        EstadioEntity newEstadio = factory.manufacturePojo(EstadioEntity.class);
        newEstadio.setNombreCiudad("Bogota");
        newEstadio.setAforoMaximo(20000);
        newEstadio.setPrecioAlquiler((long) 50000.0);
        assertThrows(IllegalOperationException.class, () -> {
            estadioService.createEstadio(newEstadio);
        });
    }
}