package ude.edu.uy.controldeplagas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;
import ude.edu.uy.controldeplagas.converters.ServerResponse;

/**
 * Created by mzimmer on 25/11/17.
 */

public class ActivityBuscarCliente extends AppCompatActivity {
    private TextView txtIdentificador;
    private Intent intentAnterior, intentNuevo;
    private String direccionServer, puerto, usuario, password, operacion;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);
        intentAnterior = getIntent();
        direccionServer = intentAnterior.getStringExtra("direccion");
        puerto = intentAnterior.getStringExtra("puerto");
        usuario = intentAnterior.getStringExtra("usuario");
        password = intentAnterior.getStringExtra("password");
        operacion = intentAnterior.getStringExtra("operacion");
        txtIdentificador = (TextView) findViewById(R.id.txt_cliente_id);
    }

    public void buscarCliente(View v) {
        if (!txtIdentificador.getText().toString().isEmpty()) {
            pbar = (ProgressBar) findViewById(R.id.pbarBusqueda);
            pbar.setVisibility(View.VISIBLE);
            new buscarCliente().execute();
        } else {
            Toast.makeText(getApplicationContext(), "El identificador no puede ser vacio.", Toast.LENGTH_LONG).show();
        }
    }

    private class buscarCliente extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String identificador = txtIdentificador.getText().toString();
            String cliente = "";
            String url = UrlBuilder.buildUrl(direccionServer, puerto, "cliente", identificador);
            Log.d("Direccion buscar cli: ", url);
            String authorization = null;
            try {
                authorization = EncodeBase64.encodeUserPassword(usuario, password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                cliente = HttpUrlConnection.sendGet(url, authorization);
                Log.d("cliente: ", cliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cliente;
        }

        @Override
        protected void onPostExecute(String result) {
            String validar = ServerResponse.responseConvert(result);
            if (!validar.equals("Respuesta exitosa")) {
                AlertDialog alert = createAlertDialog("Error en busqueda de usuario", validar, ActivityBuscarCliente.this);
                alert.show();
            } else {
                JSONObject cliente = null;
                try {
                    cliente = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (cliente != null) {
                    try {
                        intentNuevo = null;
                        if (operacion.equals("buscar")) {
                            intentNuevo = new Intent(getApplicationContext(), ActivityVerCliente.class);
                        } else {
                            if (operacion.equals("editar")) {
                                intentNuevo = new Intent(getApplicationContext(), ActivityEditarCliente.class);
                            } else {
                                if (operacion.equals("borrar")) {
                                    intentNuevo = new Intent(getApplicationContext(), ActivityBorrarCliente.class);
                                }
                            }
                        }
                        intentNuevo.putExtra("direccionServer", direccionServer);
                        intentNuevo.putExtra("puerto", puerto);
                        intentNuevo.putExtra("usuario", usuario);
                        intentNuevo.putExtra("password", password);
                        intentNuevo.putExtra("nombre", cliente.getString("nombre"));
                        intentNuevo.putExtra("telefono", cliente.getString("telefono"));
                        intentNuevo.putExtra("email", cliente.getString("email"));
                        intentNuevo.putExtra("direccion", cliente.getString("direccion"));
                        intentNuevo.putExtra("identificador", txtIdentificador.getText().toString());
                        new buscarDepto().execute();
                        //startActivity(intentNuevo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog alert = createAlertDialog("Error en busqueda de usuario", "El usuario no existe.", ActivityBuscarCliente.this);
                    alert.show();
                }
            }
        }
    }

    private class buscarDepto extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String identificador = txtIdentificador.getText().toString();
            String departamento = "";
            String selector = "cliente/" + identificador.toString() + "/departamento";
            String url = UrlBuilder.buildUrl(direccionServer, puerto, selector, "");
            Log.d("Direccion buscar depto:", url);
            String authorization = null;
            try {
                authorization = EncodeBase64.encodeUserPassword(usuario, password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                departamento = HttpUrlConnection.sendGet(url, authorization);
                Log.d("departamento: ", departamento);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return departamento;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject departamento = null;
            try {
                departamento = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (departamento != null) {
                String depto = "";
                try {
                    depto = departamento.getString("nombre");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intentNuevo.putExtra("departamento", depto);
                startActivity(intentNuevo);
            } else {
                AlertDialog alert = createAlertDialog("Error en busqueda de usuario", "El usuario no existe.", ActivityBuscarCliente.this);
                alert.show();
            }

        }
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


    public void volverMain(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}