package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConciertoService.class)
public class ConciertoServiceTest {

    @Autowired
    private ConciertoService conciertoService;

    @Autowired
    private TestEntityManager entityManager;

    private List<ConciertoEntity> conciertoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM ConciertoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ConciertoEntity conciertoEntity = new ConciertoEntity();
            conciertoEntity.setNombreArtista("Artista " + i);
            conciertoEntity.setFecha(LocalDate.now().plusDays(10 + i));
            conciertoEntity.setCapacidad(5000 + i * 1000);
            conciertoEntity.setPresupuesto(50000 + i * 10000);
            entityManager.persist(conciertoEntity);
            conciertoList.add(conciertoEntity);
        }
    }

    @Test
    void testCrearConcierto_Exitoso() {
        ConciertoEntity newConcierto = new ConciertoEntity();
        newConcierto.setNombreArtista("Coldplay");
        newConcierto.setFecha(LocalDate.now().plusDays(30));
        newConcierto.setCapacidad(7000);
        newConcierto.setPresupuesto(60000);

        ConciertoEntity result = conciertoService.crearConcierto(newConcierto, null);

        assertNotNull(result);
        ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());

        assertEquals(newConcierto.getNombreArtista(), entity.getNombreArtista());
        assertEquals(newConcierto.getFecha(), entity.getFecha());
        assertEquals(newConcierto.getCapacidad(), entity.getCapacidad());
        assertEquals(newConcierto.getPresupuesto(), entity.getPresupuesto());
    }

    @Test
    void testCrearConcierto_FallaPorFechaPasada() {
        ConciertoEntity newConcierto = new ConciertoEntity();
        newConcierto.setNombreArtista("Maluma");
        newConcierto.setFecha(LocalDate.now().minusDays(5)); 
        newConcierto.setCapacidad(5000);
        newConcierto.setPresupuesto(60000);

        assertThrows(IllegalArgumentException.class, () -> conciertoService.crearConcierto(newConcierto, null));
    }

    @Test
    void testCrearConcierto_FallaPorCapacidadMenor() {
        ConciertoEntity newConcierto = new ConciertoEntity();
        newConcierto.setNombreArtista("Feid");
        newConcierto.setFecha(LocalDate.now().plusDays(20));
        newConcierto.setCapacidad(5);
        newConcierto.setPresupuesto(60000);

        assertThrows(IllegalArgumentException.class, () -> conciertoService.crearConcierto(newConcierto, null));
    }

    @Test
    void testCrearConcierto_FallaPorPresupuestoBajo() {
        ConciertoEntity newConcierto = new ConciertoEntity();
        newConcierto.setNombreArtista("Imagine Dragons");
        newConcierto.setFecha(LocalDate.now().plusDays(20));
        newConcierto.setCapacidad(5000);
        newConcierto.setPresupuesto(500); // Menos de 1000

        assertThrows(IllegalArgumentException.class, () -> conciertoService.crearConcierto(newConcierto, null));
    }
}







