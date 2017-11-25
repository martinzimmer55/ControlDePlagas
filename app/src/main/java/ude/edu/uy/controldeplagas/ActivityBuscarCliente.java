package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;

/**
 * Created by mzimmer on 25/11/17.
 */

public class ActivityBuscarCliente extends AppCompatActivity{
    private TextView txtIdentificador;
    private Intent intentAnterior;
    private String direccionServer, puerto, usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);
        intentAnterior = getIntent();
        direccionServer = intentAnterior.getStringExtra("direccion");
        puerto = intentAnterior.getStringExtra("puerto");
        usuario = intentAnterior.getStringExtra("usuario");
        password = intentAnterior.getStringExtra("password");
        txtIdentificador = (TextView) findViewById(R.id.txt_cliente_id);
    }

    public void buscarCliente(View v) {
        new buscar().execute();
    }

    private class buscar extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            String identificador = txtIdentificador.getText().toString();
            String resultado = "";
            JSONArray jsonArray = null;
            if (identificador.isEmpty()) {
                //mensaje identificador vacio
            } else {
                String url = UrlBuilder.buildUrl(direccionServer, puerto, "cliente", identificador);
                Log.d("Direccion buscar user: ", url);
                String authorization = null;
                try {
                    authorization = EncodeBase64.encodeUserPassword(usuario, password);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    resultado = HttpUrlConnection.sendGet(url, authorization);
                    Log.d("resultado: ", resultado);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            JSONObject cliente = null;
            try {
                cliente = new JSONObject(resultado);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (cliente != null) {
                try{
                    Intent intent = new Intent(getApplicationContext(), ActivityVerCliente.class);
                    intent.putExtra("nombre", cliente.getString("nombre"));
                    intent.putExtra("telefono", cliente.getString("telefono"));
                    intent.putExtra("email", cliente.getString("email"));
                    intent.putExtra("direccion", cliente.getString("direccion"));
                    //intent.putExtra("departamento", cliente.getString("departamento"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //usuario no existe, tirar mensaje
            }
            /*
            try {
                jsonArray = new JSONArray(resultado);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject cliente = buscarPorNombre(jsonArray, identificador);
            if (cliente == null) {
                //mostrar mensaje de error, no existe el cliente con ese nombre
            } else {
                try{
                    Intent intent = new Intent(getApplicationContext(), ActivityVerCliente.class);
                    intent.putExtra("nombre", cliente.getString("nombre"));
                    intent.putExtra("telefono", cliente.getString("telefono"));
                    intent.putExtra("email", cliente.getString("email"));
                    intent.putExtra("direccion", cliente.getString("direccion"));
                    intent.putExtra("departamento", cliente.getString("departamento"));
                } catch (JSONException e) {
                    e.printStackTrace();
            }
            }

            */
            return null;
        }
    }

    public JSONObject buscarPorNombre (JSONArray jsonArray, String nombre) {
        int i = 0;
        boolean encontrado = false;
        JSONObject result = null;
        while ((i<jsonArray.length()) && (!encontrado)) {
            try {
                JSONObject cliente = jsonArray.getJSONObject(i);
                String name = cliente.getString("nombre");
                if(name.equals(nombre)) {
                    result = jsonArray.getJSONObject(i);
                    encontrado = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        return result;
    }
}
