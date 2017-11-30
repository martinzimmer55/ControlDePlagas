package ude.edu.uy.controldeplagas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;

/**
 * Created by mzimmer on 25/11/17.
 */

public class ActivityConfigurarServer extends AppCompatActivity {
    private TextView txtDireccion, txtPuerto;
    private String direccion, puerto;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);
        pbar = (ProgressBar)findViewById(R.id.pbarConfigurar);
        SharedPreferences prefs = getSharedPreferences("controldeplagaspref", Context.MODE_PRIVATE);
        direccion = prefs.getString("direccion", "");
        puerto = prefs.getString("puerto", "");
        if (!((direccion.equals("")) || (puerto.equals("")))) {
            pbar.setVisibility(View.VISIBLE);
            new verificarDatosServer().execute();
        }


    }
    public void guardarConfig(View v) {
        txtDireccion = (TextView) findViewById(R.id.txtIPServer);
        txtPuerto = (TextView) findViewById(R.id.txtPuertoServer);
        direccion = txtDireccion.getText().toString();
        puerto = txtPuerto.getText().toString();
        if (direccion.isEmpty()) {
            //mostrar mensaje direccion vacia
            Toast.makeText(getApplicationContext(), "La direccion no puede ser vacia", Toast.LENGTH_LONG).show();
        } else {
            if (puerto.isEmpty()) {
                //mostrar mensaje puerto vacio
                Toast.makeText(getApplicationContext(), "El puerto no puede ser vacio", Toast.LENGTH_LONG).show();
            } else {
                pbar.setVisibility(View.VISIBLE);
                new verificarDatosServer().execute();
            }
        }
    }

    private class verificarDatosServer extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = UrlBuilder.buildUrl(direccion, puerto, "", "");
            Log.d("Direccion server:", url);
            String authorization = "";
            String resultado = null;
            try {
                resultado = HttpUrlConnection.verificarServer(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            pbar.setVisibility(View.INVISIBLE);
            if (result.equals("Verificacion exitosa")) {
                SharedPreferences prefs = getSharedPreferences("controldeplagaspref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("direccion", direccion);
                editor.putString("puerto", puerto);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Datos correctos", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                intent.putExtra("direccion", direccion);
                intent.putExtra("puerto", puerto);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Datos incorrectos, por favor verifique", Toast.LENGTH_LONG).show();
            }
        }
    }
}
