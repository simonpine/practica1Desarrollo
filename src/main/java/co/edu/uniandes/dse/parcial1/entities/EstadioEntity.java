package co.edu.uniandes.dse.parcial1.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Integer precioAlquiler;
    private String nombreCiudad;
    private Integer capacidadMaxima;

    
    @PodamExclude
	@OneToMany(mappedBy = "estadio") 
    private List<ConciertoEntity> conciertos;

}


