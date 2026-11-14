
package com.examen.apiexamen;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final JdbcTemplate jdbcTemplate;

    public LoginController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> datos) {

        String usuario = datos.get("usuario");
        String password = datos.get("password");

        if (usuario == null || password == null
                || usuario.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().body(
                Map.of("mensaje", "Usuario y contraseña requeridos")
            );
        }

        String sql = """
            SELECT COUNT(*)
            FROM USUARIOS
            WHERE USUARIO = ? AND PASSWORD = ?
            """;

        Integer cantidad = jdbcTemplate.queryForObject(
            sql, Integer.class, usuario, password
        );

        if (cantidad != null && cantidad > 0) {
            return ResponseEntity.ok(
                Map.of("mensaje", "Login exitoso",
                       "autenticado", true)
            );
        }

        return ResponseEntity.status(401).body(
            Map.of("mensaje", "Credenciales incorrectas",
                   "autenticado", false)
        );
    }
}
