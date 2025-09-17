package co.edu.uniandes.dse.parcial1.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Import(ConciertoService.class)
class ConciertoServiceTest {

    @Autowired
    private ConciertoService conciertoService;

    private PodamFactoryImpl factory = new PodamFactoryImpl();


    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        clearData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
    }


    @Test
    void testCreateConciertoSuccess() throws IllegalOperationException {
        ConciertoEntity newTutor = factory.manufacturePojo(ConciertoEntity.class);
        newTutor.setNombre("MarioSanchez");
        newTutor.setFechaConcierto(LocalDateTime.of(2027, 03, 3, 4,4,4));

        ConciertoEntity result = conciertoService.createConcierto(newTutor);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("MarioSanchez", result.getNombre());
    }

        @Test
    void testCreateConciertoFail() {
        ConciertoEntity newTutor = factory.manufacturePojo(ConciertoEntity.class);
        newTutor.setNombre("MarioSanchez");
        newTutor.setFechaConcierto(LocalDateTime.now());

        IllegalOperationException exception = assertThrows(
            IllegalOperationException.class,
            () -> conciertoService.createConcierto(newTutor)
        );
        
        assertEquals("Tiene que ser una fecha posterior", exception.getMessage());

    }
}
