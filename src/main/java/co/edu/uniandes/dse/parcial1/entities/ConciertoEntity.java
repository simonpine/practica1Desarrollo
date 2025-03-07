package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Integer presupuesto;
    private String nombreArtista;
    private LocalDate fecha;
    private Integer capacidad;

    
    @PodamExclude
    @ManyToOne
    @JoinColumn(name = "estadio_id", nullable = false)
    private EstadioEntity estadios;

    
}


