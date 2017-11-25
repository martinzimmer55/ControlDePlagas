package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;

/**
 * Created by mzimmer on 24/11/17.
 */

public class ActivityAltaCliente extends AppCompatActivity {

    private TextView txtTitulo, txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Spinner spDepartamento;
    private Button btnGuardar;
    private Intent intentAnterior;
    private String direccionServer, puerto, usuario, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        intentAnterior = getIntent();
        direccionServer = intentAnterior.getStringExtra("direccion");
        puerto = intentAnterior.getStringExtra("puerto");
        usuario = intentAnterior.getStringExtra("usuario");
        password = intentAnterior.getStringExtra("password");

        txtTitulo = (TextView) findViewById(R.id.txt_cliente_titulo);
        txtNombre = (TextView) findViewById(R.id.txt_cliente_nombre);
        txtTelefono = (TextView) findViewById(R.id.txt_cliente_telefono);
        txtEmail = (TextView) findViewById(R.id.txt_cliente_email);
        txtDireccion = (TextView) findViewById(R.id.txt_cliente_direccion);
        spDepartamento = (Spinner) findViewById(R.id.sp_cliente_departamento);
        txtTitulo.setText(R.string.cliente_alta_titulo);
        btnGuardar = (Button) findViewById(R.id.btn_cliente_siguiente);
        btnGuardar.setText(R.string.boton_guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // metodo para guardar
                new guardarUsuario().execute();
            }
        });
    }


    public void volverABMCliente(View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityABMCliente.class);
        startActivity(intent);
    }

    private class guardarUsuario extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String nombre = txtNombre.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String email = txtEmail.getText().toString();
            String direccion = txtDireccion.getText().toString();
            String departamento = "http://localhost:8080/departamento/3";
            if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || departamento.isEmpty()) {
                //mostrar error campos vacios
                Log.d("Datos vacios", "error");
            } else {
                JSONObject json = new JSONObject();
                try {
                    json.put("nombre", nombre);
                    json.put("telefono", telefono);
                    json.put("email", email);
                    json.put("direccion", direccion);
                    json.put("departamento", departamento);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String url = UrlBuilder.buildUrl(direccionServer, puerto, "cliente", "");
                Log.d("Direccion crear user: ", url);
                String authorization = null;
                try {
                    authorization = EncodeBase64.encodeUserPassword(usuario, password);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try{
                    String resultado = HttpUrlConnection.sendPost(url, authorization, json.toString());
                    Log.d("resultado: ", resultado);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

}
