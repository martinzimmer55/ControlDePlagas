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
                    response = "Datos invalidos";
                } else {
                    if (response.contains("401")) {
                        response = "Datos de usuario invalidos";
                    }
                }
            }
        } else {
            response = "Respuesta exitosa";

        }
        return response;
    }
}
