package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity asociacion(Long conciertoId, Long estadioId) throws EntityNotFoundException, IllegalOperationException {
        Optional<ConciertoEntity> conciertoOp = conciertoRepository.findById(conciertoId);
        if (conciertoOp.isEmpty()) {
            throw new EntityNotFoundException("Concierto no encontrado");
        }

        Optional<EstadioEntity> estadioOp = estadioRepository.findById(estadioId);
        if (estadioOp.isEmpty()) {
            throw new EntityNotFoundException("Estadio no encontrado");
        }

        ConciertoEntity concierto = conciertoOp.get();
        EstadioEntity estadio = estadioOp.get();

        if (concierto.getAforoMaximo() > estadio.getAforoMaximo()) {
            throw new IllegalOperationException("La capacidad del concierto no debe superar la capacidad del estadio.");
        }

        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
            throw new IllegalOperationException("El precio de alquiler del estadio no debe superar el presupuesto del concierto.");
        }

        List<ConciertoEntity> conciertosEnEstadio = estadio.getConciertos();
        for (ConciertoEntity c : conciertosEnEstadio) {
            if (Math.abs(Duration.between(c.getFechaConcierto(), concierto.getFechaConcierto()).toDays()) <= 2) {
                throw new IllegalOperationException("Debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio.");
            }
        }

        concierto.setEstadio(estadio);
        return conciertoRepository.save(concierto);
    }

}
