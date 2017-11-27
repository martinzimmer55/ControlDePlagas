package ude.edu.uy.controldeplagas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.Departamentos;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;
import ude.edu.uy.controldeplagas.converters.ServerResponse;

/**
 * Created by mzimmer on 24/11/17.
 */

public class ActivityAltaCliente extends AppCompatActivity {

    private TextView txtTitulo, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Spinner spDepto;
    private Button btnGuardar;
    private Intent intentAnterior;
    private String direccionServer, puerto, usuario, password, departamento;
    private ArrayList listaDeptos;
    private Map<String, String> mapaDeptos;
    private ArrayAdapter<String> adapter;


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
        txtTitulo.setText(R.string.cliente_alta_titulo);

        departamento = null;
        Log.d("Busco spinner: ", "");
        spDepto = (Spinner) findViewById(R.id.sp_cliente_departamento);
        Log.d("Lleno spinner: ", "");
        new llenarSpiner().execute();
        spDepto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                departamento = spDepto.getSelectedItem().toString();
                Log.d("depto seleccionado: ", departamento);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        btnGuardar = (Button) findViewById(R.id.btn_cliente_siguiente);
        btnGuardar.setText(R.string.boton_guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String direccion = txtDireccion.getText().toString();
                if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || departamento == null) {
                    Toast.makeText(getApplicationContext(), "Los datos no pueden ser vacios, complete todos los datos.", Toast.LENGTH_LONG).show();
                } else {
                    // metodo para guardar
                    new guardarUsuario().execute();
                }
            }
        });

    }


    public void volverMain(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    private class llenarSpiner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String resultado = "";
            String url = UrlBuilder.buildUrl(direccionServer, puerto, "departamento", "");
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
            mapaDeptos = Departamentos.getDatosDepartamentos(resultado);
            listaDeptos = Departamentos.getListaDepartamentos(mapaDeptos);
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_text_view, listaDeptos);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            spDepto.setAdapter(adapter);
        }
    }

    private class guardarUsuario extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String nombre = txtNombre.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String email = txtEmail.getText().toString();
            String direccion = txtDireccion.getText().toString();
            String resultado = "";
            String urlDepto = mapaDeptos.get(departamento);
            Log.d("url depto = ", urlDepto);
            JSONObject json = new JSONObject();
            try {
                json.put("nombre", nombre);
                json.put("telefono", telefono);
                json.put("email", email);
                json.put("direccion", direccion);
                json.put("departamento", urlDepto);
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
            try {
                resultado = HttpUrlConnection.sendPost(url, authorization, json.toString());
                Log.d("resultado: ", resultado);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            String validar = ServerResponse.responseConvert(result);
            Log.d("validar = ", validar);
            AlertDialog alert = createAlertDialog("Creacion de usuario", validar, ActivityAltaCliente.this);
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
