package co.edu.uniandes.dse.parcial1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired
    private ConciertoRepository conciertoRepository;
    
    @Autowired
    private EstadioRepository estadioRepository;

    public ConciertoEntity asignarConciertoAEstadio(Long idConcierto, Long idEstadio) {
        
        ConciertoEntity concierto = conciertoRepository.findById(idConcierto).orElseThrow(() -> new IllegalArgumentException("Concierto no encontrado"));

        EstadioEntity estadio = estadioRepository.findById(idEstadio).orElseThrow(() -> new IllegalArgumentException("Estadio no encontrado"));

        if (concierto.getCapacidad() > estadio.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La capacidad del concierto supera la capacidad del estadio.");
        }

        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
            throw new IllegalArgumentException("El precio de alquiler del estadio supera el presupuesto del concierto.");
        }

        //List<ConciertoEntity> conciertosPrevios = conciertoRepository.findById(idEstadio);
        //for (ConciertoEntity previo : conciertosPrevios) {
            //long diferenciaDias = DAYS.between(previo.getFechaConcierto(), concierto.getFechaConcierto());
          //  if (Math.abs(diferenciaDias) < 2) {
            //    throw new IllegalArgumentException("Debe haber un mínimo de 2 días entre conciertos en el mismo estadio.");
           // }
       // }

        concierto.setEstadios(estadio);
        return conciertoRepository.save(concierto);
    }
}



