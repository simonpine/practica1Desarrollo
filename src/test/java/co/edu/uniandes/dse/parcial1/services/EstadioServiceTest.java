package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Transactional
@Import(EstadioService.class)
public class EstadioServiceTest {

    @Autowired
    private EstadioService estadioService;

    @Autowired
    private TestEntityManager entityManager;

    private List<EstadioEntity> estadioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();  
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM EstadioEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EstadioEntity estadioEntity = new EstadioEntity();
            estadioEntity.setNombreCiudad("Ciudad" + i);
            estadioEntity.setCapacidadMaxima(10000 + i * 5000);
            estadioEntity.setPrecioAlquiler(150000 + i * 50000);
            entityManager.persist(estadioEntity);
            estadioList.add(estadioEntity);
        }
    }

    @Test
    void testCrearEstadio_Exitoso() {
        EstadioEntity newEstadio = new EstadioEntity();
        newEstadio.setNombreCiudad("Medellín");
        newEstadio.setCapacidadMaxima(50000);
        newEstadio.setPrecioAlquiler(200000);

        EstadioEntity result = estadioService.crearEstadio(newEstadio);
        assertNotNull(result);
        EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());

        assertEquals(newEstadio.getNombreCiudad(), entity.getNombreCiudad());
        assertEquals(newEstadio.getCapacidadMaxima(), entity.getCapacidadMaxima());
        assertEquals(newEstadio.getPrecioAlquiler(), entity.getPrecioAlquiler());
    }

    @Test
    void testCrearEstadio_Falla() {
        EstadioEntity newEstadio = new EstadioEntity();
        newEstadio.setNombreCiudad("Me"); // Menos de 3 caracteres
        newEstadio.setCapacidadMaxima(50000);
        newEstadio.setPrecioAlquiler(200000);

        assertThrows(IllegalArgumentException.class, () -> estadioService.crearEstadio(newEstadio));
    }

    @Test
    void testCrearEstadio_FallaPorCapacidadMenor() {
        EstadioEntity newEstadio = new EstadioEntity();
        newEstadio.setNombreCiudad("Bogotá");
        newEstadio.setCapacidadMaxima(500); 
        newEstadio.setPrecioAlquiler(200000);

        assertThrows(IllegalArgumentException.class, () -> estadioService.crearEstadio(newEstadio));
    }

    @Test
    void testCrearEstadio_FallaPorPrecioBajo() {
        EstadioEntity newEstadio = new EstadioEntity();
        newEstadio.setNombreCiudad("Bogotá");
        newEstadio.setCapacidadMaxima(50000);
        newEstadio.setPrecioAlquiler(50000); 

        assertThrows(IllegalArgumentException.class, () -> estadioService.crearEstadio(newEstadio));
    }
}



