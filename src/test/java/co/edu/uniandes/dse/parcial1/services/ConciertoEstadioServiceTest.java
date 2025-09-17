package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Import({ ConciertoEstadioService.class, EstadioService.class })
class ConciertoEstadioServiceTest {

    @Autowired
    private ConciertoEstadioService conciertoEstadioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private EstadioEntity estadio = new EstadioEntity();
    private List<ConciertoEntity> conciertos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
    }

    private void insertData() {
        estadio = factory.manufacturePojo(EstadioEntity.class);
        estadio.setPrecioAlquiler((long) 1000.0);
        estadio.setAforoMaximo(10000);
        entityManager.persist(estadio);

        for (int i = 0; i < 3; i++) {
            ConciertoEntity entity = factory.manufacturePojo(ConciertoEntity.class);
            entity.setPresupuesto((long) 2000.0);
            entity.setAforoMaximo(5000);
            entity.setFechaConcierto(LocalDateTime.now().plusDays(10 + i * 5));
            entityManager.persist(entity);
            conciertos.add(entity);
        }
    }

    @Test
    void testAsociacionExitosa() throws EntityNotFoundException, IllegalOperationException {
        ConciertoEntity concierto = conciertos.get(0);
        ConciertoEntity result = conciertoEstadioService.asociacion(concierto.getId(), estadio.getId());

        assertNotNull(result);
        assertEquals(concierto.getId(), result.getId());
        assertNotNull(result.getEstadio());
        assertEquals(estadio.getId(), result.getEstadio().getId());
    }

    @Test
    void testAsociacionEstadioNoExiste() {
        ConciertoEntity concierto = conciertos.get(0);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            conciertoEstadioService.asociacion(concierto.getId(), 0L);
        });
        assertTrue(exception.getMessage().contains("Estadio no encontrado"));
    }

    @Test
    void testAsociacionConciertoNoExiste() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            conciertoEstadioService.asociacion(0L, estadio.getId());
        });
        assertTrue(exception.getMessage().contains("Concierto no encontrado"));
    }

    @Test
    void testAsociacionFechaInvalida() {
        ConciertoEntity c1 = factory.manufacturePojo(ConciertoEntity.class);
        c1.setFechaConcierto(LocalDateTime.now().plusDays(1));
        c1.setEstadio(estadio);
        c1.setPresupuesto((long) 2000.0);
        c1.setAforoMaximo(5000);
        entityManager.persist(c1);

        ConciertoEntity c2 = factory.manufacturePojo(ConciertoEntity.class);
        c2.setFechaConcierto(LocalDateTime.now().plusDays(2));
        c2.setPresupuesto((long) 2000.0);
        c2.setAforoMaximo(5000);
        entityManager.persist(c2);

        estadio.getConciertos().add(c1);

        IllegalOperationException exception = assertThrows(IllegalOperationException.class, () -> {
            conciertoEstadioService.asociacion(c2.getId(), estadio.getId());
        });
        assertTrue(exception.getMessage().contains("Debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio."));
    }
}