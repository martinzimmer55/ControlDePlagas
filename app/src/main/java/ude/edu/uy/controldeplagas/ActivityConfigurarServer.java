package ude.edu.uy.controldeplagas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by mzimmer on 25/11/17.
 */

public class ActivityConfigurarServer extends AppCompatActivity{
    private TextView txtDireccion, txtPuerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);
    }

    public void guardarConfig (View v) {
        txtDireccion = (TextView) findViewById(R.id.txtIPServer);
        txtPuerto = (TextView) findViewById(R.id.txtPuertoServer);
        String direccion = txtDireccion.getText().toString();
        String puerto = txtPuerto.getText().toString();
        if (direccion.isEmpty()) {
            //mostrar mensaje direccion vacia
            Toast.makeText(getApplicationContext(), "La direccion no puede ser vacia", Toast.LENGTH_LONG).show();
        } else {
            if (puerto.isEmpty()) {
                //mostrar mensaje puerto vacio
                Toast.makeText(getApplicationContext(), "El puerto no puede ser vacio", Toast.LENGTH_LONG).show();
            } else {
                SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("direccion", direccion);
                editor.putString("puerto", puerto);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }


}
