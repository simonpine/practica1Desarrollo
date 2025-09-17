package co.edu.uniandes.dse.parcial1.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConciertoDTO {

    private Long id;
    private String nombre;
    private String nombreArtista;
    private Long presupuesto;
    private LocalDateTime fechaConcierto;
    private int aforoMaximo;
}
