package ude.edu.uy.controldeplagas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.connection.UrlBuilder;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;

/**
 * Created by mzimmer on 24/11/17.
 */

public class ActivityLogin extends AppCompatActivity {

    private EditText txtUsuario, txtPassword;
    private String direccion, puerto, usuario, password, perfilUsuario;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pbar = (ProgressBar)findViewById(R.id.pbarLogin);

        Intent intent = getIntent();
        direccion = intent.getStringExtra("direccion");
        puerto = intent.getStringExtra("puerto");

        txtUsuario = (EditText) findViewById(R.id.txtLoginUsuario);
        txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
        SharedPreferences prefs = getSharedPreferences("controldeplagaspref", Context.MODE_PRIVATE);
        usuario = prefs.getString("usuario", "");
        password = prefs.getString("password", "");
        perfilUsuario = prefs.getString("perfil", "");
        Log.d("datos usuario: ", usuario + " : " + password + " : " + perfilUsuario);
        if (!((usuario.equals("")) || (password.equals("")) || (perfilUsuario.equals("")))) {
            pbar.setVisibility(View.VISIBLE);
            new verificarDatosLogin().execute();
        }

    }

    public void login(View v) {
        usuario = txtUsuario.getText().toString();
        password = txtPassword.getText().toString();
        if (usuario.isEmpty()) {
            //mostrar mensaje usuario vacio
            Toast.makeText(getApplicationContext(), "El usuario no puede ser vacio.", Toast.LENGTH_LONG).show();

        } else {
            if (password.isEmpty()) {
                //mostrar mensaje password vacio
                Toast.makeText(getApplicationContext(), "El password no puede ser vacio.", Toast.LENGTH_LONG).show();
            } else {
                pbar.setVisibility(View.VISIBLE);
                new verificarDatosLogin().execute();
            }
        }
    }

    private class verificarDatosLogin extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = UrlBuilder.buildUrl(direccion, puerto, "usuario", "");
            String authorization = "";
            String resultado = "";
            try {
                authorization = EncodeBase64.encodeUserPassword(usuario, password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                resultado = HttpUrlConnection.verificarLogin(url, authorization);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            pbar.setVisibility(View.INVISIBLE);
            if (!result.equals("Datos Incorrectos")) {
                Boolean notExpAcc = true, notLock = true, notExpCred = true, notEnabled = true;
                try {
                    Log.d("result: ", result.toString());
                    JSONObject datosUser = new JSONObject(result);
                    JSONArray rolesUser = datosUser.getJSONArray("authorities");
                    JSONObject rol = rolesUser.getJSONObject(0);
                    Log.d("objeto rol: ", rol.toString());
                    perfilUsuario = rol.getString("authority");
                    notExpAcc = datosUser.getBoolean("accountNonExpired");
                    notLock = datosUser.getBoolean("accountNonLocked");
                    notExpCred = datosUser.getBoolean("credentialsNonExpired");
                    notEnabled = datosUser.getBoolean("enabled");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!notExpAcc) {
                    Toast.makeText(getApplicationContext(), "Su cuenta ha expirado", Toast.LENGTH_LONG).show();
                } else {
                    if (!notLock) {
                        Toast.makeText(getApplicationContext(), "Su cuenta está bloqueada", Toast.LENGTH_LONG).show();
                    } else {
                        if (!notExpCred) {
                            Toast.makeText(getApplicationContext(), "Sus credenciales han expirado", Toast.LENGTH_LONG).show();
                        } else {
                            if (!notEnabled) {
                                Toast.makeText(getApplicationContext(), "Su cuenta ha sido deshabilitada", Toast.LENGTH_LONG).show();
                            } else {
                                SharedPreferences prefs = getSharedPreferences("controldeplagaspref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("usuario", usuario);
                                editor.putString("password", password);
                                editor.putString("perfil", perfilUsuario);
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "Login correcto", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Datos incorrectos, por favor verifique", Toast.LENGTH_LONG).show();
            }
        }
    }
}
