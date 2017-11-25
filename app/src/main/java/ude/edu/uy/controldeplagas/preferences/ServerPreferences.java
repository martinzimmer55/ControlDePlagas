package ude.edu.uy.controldeplagas.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import ude.edu.uy.controldeplagas.R;

/**
 * Created by mzimmer on 25/11/17.
 */


public class ServerPreferences extends AppCompatActivity {


    public String getUsuario() {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        return prefs.getString("usuario", "usuario_por_defecto");
    }

    public String getPassword() {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        return prefs.getString("password", "password_por_defecto");
    }

    public String getDireccion() {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        return prefs.getString("direccion", "direccion_por_defecto");
    }

    public String getPuerto() {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        return prefs.getString("puerto", "puerto_por_defecto");
    }

    public void setUsuario(String usuario) {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", usuario);
        editor.apply();
    }

    public void setPassword(String password) {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", password);
        editor.apply();
    }

    public void setDireccion(String direccion) {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("direccion", direccion);
        editor.apply();
    }

    public void setPuerto(String puerto) {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("puerto", puerto);
        editor.apply();
    }
}
