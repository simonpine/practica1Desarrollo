package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    //     nombre del artista, fecha del concierto y capacidad de aforo del concierto


    private String nombre;
    private String nombreArtista;
    private Long presupuesto;
    private LocalDateTime fechaConcierto;
    private int aforoMaximo;

    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadioEntity estadio;

}
