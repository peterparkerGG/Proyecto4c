package mx.edu.utez.proyecto1c.service.usuario;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1c.controller.usuario.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.usuario.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1c.exception.customExceptions.BadRequestException;
import mx.edu.utez.proyecto1c.model.persona.Persona;
import mx.edu.utez.proyecto1c.model.usuario.Roles;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import mx.edu.utez.proyecto1c.repository.usuario.UsuarioRepository;
import mx.edu.utez.proyecto1c.repository.usuario.persona.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    // Validar existencia y consultar por ID
    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El usuario con id " + id + " no existe"));
    }

    @Transactional
    public Usuario createUser(RequestCrearUsuarioDTO payload) {
        if (usuarioRepository.findByUsername(payload.getUsername()).isPresent()) {
            throw new BadRequestException("Usuario ya existente");
        }

        Persona newPersona = new Persona();
        newPersona.setNombres(payload.getNombres());
        newPersona.setPrimerApellido(payload.getPrimerApellido());
        newPersona.setSegundoApellido(payload.getSegundoApellido());
        newPersona.setFechaNacimiento(payload.getFechaNacimiento());
        newPersona.setCorreo(payload.getCorreo());
        newPersona.setCurp(payload.getCurp());

        newPersona = personaRepository.save(newPersona);

        Usuario newUser = new Usuario();
        newUser.setUsername(payload.getUsername());
        newUser.setPassword(payload.getPassword());
        newUser.setDescripcion(payload.getDescripcion());
        newUser.setEnable(true);
        newUser.setRol(Roles.valueOf(payload.getRol()));
        newUser.setPersona(newPersona);

        return usuarioRepository.save(newUser);
    }

    // Activar y desactivar usuario por ID
    @Transactional
    public Usuario cambiarEstatusUsuario(Long id, boolean estatus) {
        Usuario usuario = getUserById(id);
        usuario.setEnable(estatus);
        return usuarioRepository.save(usuario);
    }

    // Actualizar username y descripción (mantiene contraseña, estatus, rol y relación con Persona)
    @Transactional
    public Usuario updateUsuario(Long id, RequestActualizarUsuarioDTO payload) {
        Usuario usuario = getUserById(id);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(payload.getUsername());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(id)) {
            throw new BadRequestException("El username ya está en uso");
        }

        usuario.setUsername(payload.getUsername());
        usuario.setDescripcion(payload.getDescripcion());

        return usuarioRepository.save(usuario);
    }
}