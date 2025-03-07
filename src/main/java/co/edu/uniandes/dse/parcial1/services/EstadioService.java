package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

     @Autowired
	EstadioRepository estadioRepository;


    @Transactional
    public EstadioEntity crearEstadio(EstadioEntity estadio) {
        
        if (estadio.getNombreCiudad().length() < 3) {
            throw new IllegalArgumentException("El nombre de la ciudad debe tener al menos 3 caracteres.");
        }
        if (estadio.getCapacidadMaxima() <= 1000) {
            throw new IllegalArgumentException("La capacidad del estadio debe ser mayor a 1000 personas.");
        }
        if (estadio.getPrecioAlquiler() <= 100000) {
            throw new IllegalArgumentException("El precio de alquiler del estadio debe ser mayor a 100000 dólares.");
        }

        return estadioRepository.save(estadio);

    }
    

    // Complete

}
