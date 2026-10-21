package mx.edu.utez.proyecto1c.controller.persona;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1c.controller.persona.dto.RequestActualizarPersonaDTO;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.service.persona.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persona")
@CrossOrigin({"*"})
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    // Actualizar información de una Persona de manera independiente
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarPersonaDTO payload) {
        return ResponseEntity.status(200).body(this.personaService.updatePersona(id, payload));
    }
}