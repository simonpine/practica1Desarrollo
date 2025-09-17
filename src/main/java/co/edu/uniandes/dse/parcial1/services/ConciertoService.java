package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Transactional
    public ConciertoEntity createConcierto(ConciertoEntity concierto) throws IllegalOperationException {

        if (Duration.between(LocalDateTime.now(), concierto.getFechaConcierto()).isNegative()){
            throw new IllegalOperationException("Tiene que ser una fecha posterior");
        }
        if (concierto.getAforoMaximo() <= 10){
            throw new IllegalOperationException("Debe haber mas capacidad");
        }
        if (concierto.getPresupuesto() <= 1000){
            throw new IllegalOperationException("Debe haber mas presupuetso");
        }
        return conciertoRepository.save(concierto);
    }
    // Complete

}
