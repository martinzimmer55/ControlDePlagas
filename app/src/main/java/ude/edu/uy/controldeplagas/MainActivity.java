package ude.edu.uy.controldeplagas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;

public class MainActivity extends AppCompatActivity {

    private TextView txtABM;
    private ImageView imgABM;
    private Button btnPrueba;
    private String direccion, puerto, usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //saco shared preferences y verifico direccion y puerto
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        direccion = prefs.getString("direccion", "");
        puerto = prefs.getString("puerto", "");
        Log.d("shared pref: ", prefs.toString());
        Log.d("direccion: ", direccion);
        Log.d("puerto: ",puerto);
        if ((direccion.equals("")) || (puerto.equals(""))) {
            //direccionar a configuracion
            Intent intent = new Intent(getApplicationContext(), ActivityConfigurarServer.class);
            startActivity(intent);
        }

        usuario = prefs.getString("usuario", "");
        password = prefs.getString("password", "");
        Log.d("usuario y password: ", usuario + " " + password);
        if ((usuario.equals("")) || (password.equals(""))) {
            //direccionar a login
            Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
            startActivity(intent);
        }
    }

    public void abmCliente(View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityABMCliente.class);
        intent.putExtra("direccion", direccion);
        intent.putExtra("puerto", puerto);
        intent.putExtra("usuario", usuario);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void testpost(View v) {
        new ProbarPost().execute();
    }

    private class ProbarPost extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nombre", "Carlos3002");
                jsonObject.put("telefono", "16561656");
                jsonObject.put("email", "dafd@adfdaf.com");
                jsonObject.put("direccion", "odofidf");
                jsonObject.put("departamento", "http://localhost:8080/departamento/3");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String res = HttpUrlConnection.sendPost("http://192.168.255.171:8080/cliente/", "dmVuZGVkb3I6UGFzczAxLg==",
                    jsonObject.toString());
            Log.d("El resultado es: ", res);
            return null;
        }
    }

    public void configurarServer (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityConfigurarServer.class);
        startActivity(intent);
    }

    public void configurarLogin (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
        startActivity(intent);
    }

    public void buscarCliente(View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityBuscarCliente.class);
        intent.putExtra("direccion", direccion);
        intent.putExtra("puerto", puerto);
        intent.putExtra("usuario", usuario);
        intent.putExtra("password", password);
        intent.putExtra("operacion", "buscar");
        startActivity(intent);
    }

    public void borrarDatos (View v) {
        borrarConfig();
        System.exit(0);
    }


    public void borrarConfig() {
        SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("direccion", "");
        editor.putString("puerto", "");
        editor.putString("usuario", "");
        editor.putString("password", "");
        editor.commit();
    }
}
