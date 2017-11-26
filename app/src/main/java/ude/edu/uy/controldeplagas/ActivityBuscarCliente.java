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
    private Intent intentAnterior;
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
            pbar = (ProgressBar)findViewById(R.id.pbarBusqueda);
            pbar.setVisibility(View.VISIBLE);
            new buscar().execute();
        } else {
            Toast.makeText(getApplicationContext(), "El identificador no puede ser vacio.", Toast.LENGTH_LONG).show();
        }
    }

    private class buscar extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String identificador = txtIdentificador.getText().toString();
            String resultado = "";
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
            return resultado;
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
                        Intent intent = null;
                        if (operacion.equals("buscar")) {
                            intent = new Intent(getApplicationContext(), ActivityVerCliente.class);
                        } else {
                            if (operacion.equals("editar")) {
                                intent = new Intent(getApplicationContext(), ActivityEditarCliente.class);
                            } else {
                                if (operacion.equals("borrar")) {
                                    intent = new Intent(getApplicationContext(), ActivityBorrarCliente.class);
                                }
                            }
                        }
                        intent.putExtra("direccionServer", direccionServer);
                        intent.putExtra("puerto", puerto);
                        intent.putExtra("usuario", usuario);
                        intent.putExtra("password", password);
                        intent.putExtra("nombre", cliente.getString("nombre"));
                        intent.putExtra("telefono", cliente.getString("telefono"));
                        intent.putExtra("email", cliente.getString("email"));
                        intent.putExtra("direccion", cliente.getString("direccion"));
                        intent.putExtra("identificador", txtIdentificador.getText().toString());
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    }
}