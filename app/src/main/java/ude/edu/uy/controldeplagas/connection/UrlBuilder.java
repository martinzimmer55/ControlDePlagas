package ude.edu.uy.controldeplagas.connection;

import ude.edu.uy.controldeplagas.R;

/**
 * Created by mzimmer on 24/11/17.
 */

public class UrlBuilder {

    public static String buildUrl (String direccion, String puerto) {
        String url = "http://" + direccion + R.string.separador + puerto;
        return url;
    }
}
