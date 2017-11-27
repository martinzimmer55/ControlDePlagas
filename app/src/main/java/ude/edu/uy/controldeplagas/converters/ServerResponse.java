package ude.edu.uy.controldeplagas.converters;

/**
 * Created by mzimmer on 26/11/17.
 */

public class ServerResponse {
    public static String responseConvert (String resultado) {
        String response ="";
        if (resultado.contains("Error")) {
            if (resultado.contains("404")) {
                response = "Recurso no encontrado";
            } else {
                if (resultado.contains("500")) {
                    response = "Datos inválidos";
                } else {
                    if (resultado.contains("401")) {
                        response = "Permisos insuficientes o datos de usuario invalidos, verifique usuario y password";
                    } else {
                        response = "Error desconocido";
                    }
                }
            }
        } else {
            response = "Respuesta exitosa";

        }

        return response;
    }
}
