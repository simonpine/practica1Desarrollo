package co.edu.uniandes.dse.parcial1.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {


    @Autowired
	ConciertoRepository conciertoRepository;


    @Transactional
	public ConciertoEntity crearConcierto(ConciertoEntity concierto, EstadioEntity estadio) {
		
		//if (concierto.getFecha().isBefore(new Date())) {
         //   throw new IllegalArgumentException("La fecha del concierto no puede ser en el pasado.");
        //}

        if (concierto.getCapacidad() <= 10) {
            throw new IllegalArgumentException("La capacidad del concierto debe ser mayor a 10 personas.");
        }

        if (concierto.getPresupuesto() <= 1000) {
            throw new IllegalArgumentException("El presupuesto del concierto debe ser mayor a 1000 dólares.");
        }

        if (concierto.getCapacidad() > estadio.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La capacidad del concierto no puede superar la capacidad del estadio.");
        }

        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
            throw new IllegalArgumentException("El precio de alquiler del estadio no puede superar el presupuesto del concierto.");
        }
		
		return conciertoRepository.save(concierto);

    }
}



