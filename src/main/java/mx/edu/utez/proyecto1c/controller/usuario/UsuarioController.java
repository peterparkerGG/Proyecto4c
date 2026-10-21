package mx.edu.utez.proyecto1c.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1c.controller.usuario.dto.RequestActualizarUsuarioDTO;
import mx.edu.utez.proyecto1c.controller.usuario.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.proyecto1c.model.usuario.Usuario;
import mx.edu.utez.proyecto1c.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin({"*"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.status(200).body(this.usuarioService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody @Valid RequestCrearUsuarioDTO payload) {
        return ResponseEntity.status(201).body(this.usuarioService.createUser(payload));
    }

    // Consultar información de un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.usuarioService.getUserById(id));
    }

    // Activar usuario
    @PutMapping("/{id}/activar")
    public ResponseEntity<Usuario> activarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.usuarioService.cambiarEstatusUsuario(id, true));
    }

    // Desactivar usuario
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.usuarioService.cambiarEstatusUsuario(id, false));
    }

    // Actualizar username y descripcion sin perder relacion con Persona ni borrar datos
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Long id,
            @RequestBody @Valid RequestActualizarUsuarioDTO payload) {
        return ResponseEntity.status(200).body(this.usuarioService.updateUsuario(id, payload));
    }
}