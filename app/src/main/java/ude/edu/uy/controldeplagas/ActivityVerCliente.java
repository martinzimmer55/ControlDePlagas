package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by mzimmer on 25/11/17.
 */

public class ActivityVerCliente extends AppCompatActivity{

    private TextView txtTitulo, txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Button btnGuardar;
    private Spinner spDepto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        txtTitulo = (TextView) findViewById(R.id.txt_cliente_titulo);
        txtNombre = (TextView) findViewById(R.id.txt_cliente_nombre);
        txtTelefono = (TextView) findViewById(R.id.txt_cliente_telefono);
        txtEmail = (TextView) findViewById(R.id.txt_cliente_email);
        txtDireccion = (TextView) findViewById(R.id.txt_cliente_direccion);
        txtTitulo.setText(R.string.cliente_ver_titulo);
        btnGuardar = (Button) findViewById(R.id.btn_cliente_siguiente);
        btnGuardar.setVisibility(View.INVISIBLE);

        Intent intentAnterior = getIntent();
        txtNombre.setText(intentAnterior.getStringExtra("nombre"));
        txtDireccion.setText(intentAnterior.getStringExtra("direccion"));
        txtEmail.setText(intentAnterior.getStringExtra("email"));
        txtTelefono.setText(intentAnterior.getStringExtra("telefono"));
        //falta departamento
        spDepto = (Spinner) findViewById(R.id.sp_cliente_departamento);

    }

    public void volverMain (View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
