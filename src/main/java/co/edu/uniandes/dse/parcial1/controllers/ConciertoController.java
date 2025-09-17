package co.edu.uniandes.dse.parcial1.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.parcial1.dto.ConciertoDTO;
import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.ConciertoService;

@RestController
@RequestMapping("/conciertos")
public class ConciertoController {

    @Autowired
    private ConciertoService conciertoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ConciertoDTO create(@RequestBody ConciertoDTO conciertoDTO) throws IllegalOperationException {
        ConciertoEntity conciertoEntity = modelMapper.map(conciertoDTO, ConciertoEntity.class);
        ConciertoEntity nuevoConcierto = conciertoService.createConcierto(conciertoEntity);
        return modelMapper.map(nuevoConcierto, ConciertoDTO.class);
    }
}
