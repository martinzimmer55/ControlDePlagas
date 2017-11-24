package ude.edu.uy.controldeplagas.converters;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by goliath on 21/11/2017.
 */

public class EncodeBase64 {
    public static String encodeUserPassword (String usuario, String password) throws UnsupportedEncodingException {
        String user = usuario.concat(":");
        byte[] userBytes = user.getBytes("UTF-8");
        byte[] passwordBytes = password.getBytes("UTF-8");
        String userBase64 = Base64.encodeToString(userBytes, Base64.NO_WRAP);
        String passBase64 = Base64.encodeToString(passwordBytes, Base64.NO_WRAP);
        String result = userBase64.concat(passBase64);
        return result;
    }
}
