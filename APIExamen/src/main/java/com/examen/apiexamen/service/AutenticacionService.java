package com.examen.apiexamen.service;

import com.examen.apiexamen.dao.UsuarioDAO;
import com.examen.apiexamen.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService {

    private static final int CONSTANTE = 5;

    private final UsuarioDAO usuarioDAO;

    public AutenticacionService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public String decodificarPassword(String passwordCodificada) {

        if (passwordCodificada == null ||
                passwordCodificada.isEmpty()) {
            throw new IllegalArgumentException(
                "La contraseña codificada está vacía"
            );
        }

        String[] valores = passwordCodificada.split("-", -1);
        StringBuilder resultado = new StringBuilder();

        for (String valor : valores) {

            int numero = Integer.parseInt(valor);

            int codigoOriginal = numero - CONSTANTE;

            if (codigoOriginal < 0 ||
                    codigoOriginal > Character.MAX_VALUE) {
                throw new IllegalArgumentException(
                    "Código de carácter inválido"
                );
            }

            char caracter = (char) codigoOriginal;

            resultado.append(caracter);
        }

        return resultado.toString();
    }

    public Usuario autenticar(String usuario,
                              String passwordCodificada) {

        if (usuario == null || usuario.isBlank()) {
            return null;
        }

        try {
            String passwordOriginal =
                    decodificarPassword(passwordCodificada);

            return usuarioDAO.buscarPorCredenciales(
                usuario, passwordOriginal
            );

        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
