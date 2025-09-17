package co.edu.uniandes.dse.parcial1.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    // Complete

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public EstadioEntity createEstadio(EstadioEntity newTutor) throws IllegalOperationException {

        if (newTutor.getNombreCiudad().length() < 3){
            throw new IllegalOperationException("Tiene que ser una una longitud superior");
        }
        if (newTutor.getAforoMaximo() <= 1000 || newTutor.getAforoMaximo() >= 1000000){
            throw new IllegalOperationException("Debe haber mas capacidad o menos");
        }
        if (newTutor.getPrecioAlquiler() <= 100000){
            throw new IllegalOperationException("Debe haber mas presupuetso");
        }
        return estadioRepository.save(newTutor);
    }

}
