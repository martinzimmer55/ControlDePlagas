package ude.edu.uy.controldeplagas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

public class ActivityBorrarCliente extends AppCompatActivity {
    private TextView txtTitulo, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Spinner spDepartamento;
    private Button btnBorrar;
    private String identificador, direccionServer, puerto, usuario, password;
    private ProgressBar pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        txtTitulo = (TextView) findViewById(R.id.txt_cliente_titulo);
        txtNombre = (TextView) findViewById(R.id.txt_cliente_nombre);
        txtTelefono = (TextView) findViewById(R.id.txt_cliente_telefono);
        txtEmail = (TextView) findViewById(R.id.txt_cliente_email);
        txtDireccion = (TextView) findViewById(R.id.txt_cliente_direccion);
        txtTitulo.setText(R.string.cliente_borrar_titulo);
        btnBorrar = (Button) findViewById(R.id.btn_cliente_siguiente);
        btnBorrar.setText(R.string.boton_eliminar);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo para borrar clienten
                pbar = (ProgressBar) findViewById(R.id.pbarCliente);
                pbar.setVisibility(View.VISIBLE);
                new borrarCliente().execute();
            }
        });

        Intent intentAnterior = getIntent();
        txtNombre.setText(intentAnterior.getStringExtra("nombre"));
        txtDireccion.setText(intentAnterior.getStringExtra("direccion"));
        txtEmail.setText(intentAnterior.getStringExtra("email"));
        txtTelefono.setText(intentAnterior.getStringExtra("telefono"));

        //datos para tirar la consulta y borrar el cliente
        identificador = intentAnterior.getStringExtra("identificador");
        direccionServer = intentAnterior.getStringExtra("direccionServer");
        puerto = intentAnterior.getStringExtra("puerto");
        usuario = intentAnterior.getStringExtra("usuario");
        password = intentAnterior.getStringExtra("password");

    }

    public void volverMain(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private class borrarCliente extends AsyncTask <String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String url = UrlBuilder.buildUrl(direccionServer, puerto, "cliente", identificador);
            Log.d("Direccion borrar user: ", url);
            String authorization = null;
            String resultado = "";
            try {
                authorization = EncodeBase64.encodeUserPassword(usuario, password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                resultado = HttpUrlConnection.sendDelete(url, authorization);
                Log.d("resultado: ", resultado);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            String validar = ServerResponse.responseConvert(result);
            AlertDialog alert = createAlertDialog("Borrado de usuario", validar, ActivityBorrarCliente.this);
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
