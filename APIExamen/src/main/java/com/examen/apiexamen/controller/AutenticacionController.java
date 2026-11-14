
package com.examen.apiexamen.controller;

import com.examen.apiexamen.model.Usuario;
import com.examen.apiexamen.service.AutenticacionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    public AutenticacionController(
            AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @PostMapping({"/login", "/api/autenticar"})
    public ResponseEntity<?> autenticar(
            @RequestBody Map<String, String> datos) {

        String usuario = datos.get("usuario");
        String password = datos.get("password");

        if (usuario == null || password == null) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "autenticado", false,
                    "mensaje", "Faltan credenciales"
                )
            );
        }

        Usuario encontrado =
                autenticacionService.autenticar(usuario, password);

        if (encontrado != null) {
            return ResponseEntity.ok(Map.of(
                "autenticado", true,
                "mensaje", "Usuario autenticado correctamente",
                "nombre", encontrado.getNombre()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "autenticado", false,
                    "mensaje", "Usuario o contraseña incorrectos"
                ));
    }
}
