package ude.edu.uy.controldeplagas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mzimmer on 24/11/17.
 */

public class ActivityLogin extends AppCompatActivity {

    private EditText txtUsuario, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = (EditText) findViewById(R.id.txtLoginUsuario);
        txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
    }





    public void login (View v){
        String usuario = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();
        if (usuario.isEmpty()) {
            //mostrar mensaje usuario vacio
            Toast.makeText(getApplicationContext(), "El usuario no puede ser vacio.", Toast.LENGTH_LONG).show();

        } else {
            if (password.isEmpty()) {
                //mostrar mensaje password vacio
                Toast.makeText(getApplicationContext(), "El password no puede ser vacio.", Toast.LENGTH_LONG).show();
            } else {
                SharedPreferences prefs = getSharedPreferences(String.valueOf(R.string.preferences_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("usuario", usuario);
                editor.putString("password", password);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }

    }
}
