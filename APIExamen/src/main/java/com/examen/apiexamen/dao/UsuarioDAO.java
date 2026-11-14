package com.examen.apiexamen.dao;

import com.examen.apiexamen.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Usuario buscarPorCredenciales(
            String usuario, String password) {

        String sql = """
            SELECT ID_USUARIO, USUARIO, PASSWORD, NOMBRE
            FROM USUARIOS
            WHERE USUARIO = ? AND PASSWORD = ?
            """;

        List<Usuario> usuarios = jdbcTemplate.query(
            sql,
            (rs, fila) -> new Usuario(
                rs.getInt("ID_USUARIO"),
                rs.getString("USUARIO"),
                rs.getString("PASSWORD"),
                rs.getString("NOMBRE")
            ),
            usuario,
            password
        );

        return usuarios.isEmpty() ? null : usuarios.get(0);
    }
}
