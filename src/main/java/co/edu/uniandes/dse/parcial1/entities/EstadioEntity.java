package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;    
    private Long precioAlquiler;

    private String nombreCiudad;
    private Integer aforoMaximo;
    
    @PodamExclude
    @OneToMany(mappedBy = "estadio", cascade = CascadeType.PERSIST)
    private List<ConciertoEntity> conciertos = new ArrayList<>();
}


