package mx.edu.utez.proyecto1c.service.persona;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1c.controller.persona.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.repository.usuario.persona.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Persona updatePersona(Long id, RequestActualizarPersonaDTO payload) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("La persona con id " + id + " no existe"));

        persona.setNombres(payload.getNombres());
        persona.setPrimerApellido(payload.getPrimerApellido());
        persona.setSegundoApellido(payload.getSegundoApellido());
        persona.setFechaNacimiento(payload.getFechaNacimiento());
        persona.setCorreo(payload.getCorreo());
        persona.setCurp(payload.getCurp());

        return personaRepository.save(persona);
    }
}