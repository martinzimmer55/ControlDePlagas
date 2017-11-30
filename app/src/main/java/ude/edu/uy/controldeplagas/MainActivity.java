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

    private TextView txtABM, txtUserLogueado;
    private ImageView imgABM;
    private String direccion, puerto, usuario, password, perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //saco shared preferences y verifico direccion y puerto
        SharedPreferences prefs = getSharedPreferences("controldeplagaspref", Context.MODE_PRIVATE);
        direccion = prefs.getString("direccion", "");
        puerto = prefs.getString("puerto", "");
        Log.d("direccion: ", direccion);
        Log.d("puerto: ", puerto);
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

        txtUserLogueado = (TextView) findViewById(R.id.txtUsuarioLogueado);
        txtUserLogueado.setText(usuario);
        perfil = prefs.getString("perfil", "");
        if (!perfil.equals("ROLE_VENDEDOR")) {
            txtABM = (TextView) findViewById(R.id.txtAltaCliente);
            imgABM = (ImageView) findViewById(R.id.imgABM);
            txtABM.setVisibility(View.INVISIBLE);
            imgABM.setVisibility(View.INVISIBLE);
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

    public void configurarServer(View v) {
        borrarConfig();
        Intent intent = new Intent(getApplicationContext(), ActivityConfigurarServer.class);
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

    public void borrarDatos(View v) {
        borrarConfig();
    }


    public void borrarConfig() {
        SharedPreferences prefs = getSharedPreferences("controldeplagaspref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("direccion", "");
        editor.putString("puerto", "");
        editor.putString("usuario", "");
        editor.putString("password", "");
        editor.commit();
        finishAffinity();
        System.exit(0);
    }
}
