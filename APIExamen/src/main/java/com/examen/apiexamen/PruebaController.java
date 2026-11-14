
package com.examen.apiexamen;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PruebaController {

    private final JdbcTemplate jdbcTemplate;

    public PruebaController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/prueba")
    public Map<String, Object> probarConexion() {

        Integer cantidad = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM USUARIOS",
            Integer.class
        );

        return Map.of(
            "mensaje", "Conexion con Oracle exitosa",
            "totalUsuarios", cantidad
        );
    }
}
