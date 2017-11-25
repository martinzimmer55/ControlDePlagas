package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by goliath on 17/11/2017.
 */

public class ActivityABMCliente extends AppCompatActivity {

    private Intent intentAnterior;
    private String direccion, puerto, usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abm_cliente);
        intentAnterior = getIntent();
        direccion = intentAnterior.getStringExtra("direccion");
        puerto = intentAnterior.getStringExtra("puerto");
        usuario = intentAnterior.getStringExtra("usuario");
        password = intentAnterior.getStringExtra("password");
    }


    public void altaCliente (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityAltaCliente.class);
        intent.putExtra("direccion", direccion);
        intent.putExtra("puerto", puerto);
        intent.putExtra("usuario", usuario);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void editarCliente (View v){
        Intent intent = new Intent(getApplicationContext(), ActivityBuscarCliente.class);
        startActivity(intent);
    }

    public void borrarCliente (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityBorrarCliente.class);
        startActivity(intent);
    }
}
