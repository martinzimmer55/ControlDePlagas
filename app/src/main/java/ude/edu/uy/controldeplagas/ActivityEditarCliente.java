package ude.edu.uy.controldeplagas;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;
import ude.edu.uy.controldeplagas.converters.ServerResponse;

/**
 * Created by mzimmer on 24/11/17.
 */

public class ActivityEditarCliente extends AppCompatActivity{
    private TextView txtTitulo, txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Spinner spDepartamento;
    private Button btnGuardar;
    private Intent intentAnterior;
    private String direccionServer, puerto, usuario, password, nombre, telefono, email, direccion, departamento, identificador;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        intentAnterior = getIntent();
        direccionServer = intentAnterior.getStringExtra("direccionServer");
        puerto = intentAnterior.getStringExtra("puerto");
        usuario = intentAnterior.getStringExtra("usuario");
        password = intentAnterior.getStringExtra("password");
        nombre = intentAnterior.getStringExtra("nombre");
        telefono = intentAnterior.getStringExtra("telefono");
        email = intentAnterior.getStringExtra("email");
        direccion = intentAnterior.getStringExtra("direccion");
        identificador = intentAnterior.getStringExtra("identificador");

        txtTitulo = (TextView) findViewById(R.id.txt_cliente_titulo);
        txtNombre = (TextView) findViewById(R.id.txt_cliente_nombre);
        txtNombre.setText(nombre);
        txtTelefono = (TextView) findViewById(R.id.txt_cliente_telefono);
        txtTelefono.setText(telefono);
        txtEmail = (TextView) findViewById(R.id.txt_cliente_email);
        txtEmail.setText(email);
        txtDireccion = (TextView) findViewById(R.id.txt_cliente_direccion);
        txtDireccion.setText(direccion);
        spDepartamento = (Spinner) findViewById(R.id.sp_cliente_departamento);
        txtTitulo.setText(R.string.cliente_editar_titulo);
        btnGuardar = (Button) findViewById(R.id.btn_cliente_siguiente);
        btnGuardar.setText(R.string.boton_modificar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String direccion = txtDireccion.getText().toString();
                String departamento = "http://localhost:8080/departamento/3";
                if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || departamento.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Los datos no pueden ser vacios.", Toast.LENGTH_LONG).show();
                } else {
                    pbar = (ProgressBar) findViewById(R.id.pbarCliente);
                    pbar.setVisibility(View.VISIBLE);
                    // metodo para guardar
                    new modificarUsuario().execute();
                }
            }
        });
    }


    public void volverABMCliente(View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityABMCliente.class);
        startActivity(intent);
    }

    private class modificarUsuario extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String nombre = txtNombre.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String email = txtEmail.getText().toString();
            String direccion = txtDireccion.getText().toString();
            String departamento = "http://localhost:8080/departamento/3";
            String resultado = "";
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
                String url = UrlBuilder.buildUrl(direccionServer, puerto, "cliente", identificador);
                Log.d("Direccion crear user: ", url);
                String authorization = null;
                try {
                    authorization = EncodeBase64.encodeUserPassword(usuario, password);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try{
                    resultado = HttpUrlConnection.sendPatch(url, authorization, json.toString());
                    Log.d("resultado: ", resultado);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return resultado;
        }
        @Override
        protected void onPostExecute(String result) {
            String validar = ServerResponse.responseConvert(result);
            AlertDialog alert = createAlertDialog("Modificacion de usuario", validar, ActivityEditarCliente.this);
            alert.show();
        }

        public AlertDialog createAlertDialog(String titulo, String mensaje, Activity activity) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(titulo)
                    .setMessage(mensaje)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
            return builder.create();
        }
    }

}
