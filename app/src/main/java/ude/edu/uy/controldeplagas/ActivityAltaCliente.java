package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by mzimmer on 24/11/17.
 */

public class ActivityAltaCliente extends AppCompatActivity{

    private TextView txtTitulo, txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Spinner spDepartamento;
    private Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtTitulo = (TextView) findViewById(R.id.txt_cliente_titulo);
        txtNombre = (TextView) findViewById(R.id.txt_cliente_nombre);
        txtTelefono = (TextView) findViewById(R.id.txt_cliente_telefono);
        txtEmail = (TextView) findViewById(R.id.txt_cliente_email);
        txtDireccion = (TextView) findViewById(R.id.txt_cliente_direccion);
        spDepartamento = (Spinner) findViewById(R.id.sp_cliente_departamento);
        txtTitulo.setText(R.string.cliente_alta_titulo);
        btnGuardar = (Button) findViewById(R.id.btn_cliente_siguiente);
        btnGuardar.setText(R.string.boton_guardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // metodo para guardar
            }
        });
    }


    public void volverABMCliente(View v){
        Intent intent = new Intent(getApplicationContext(), ActivityABMCliente.class);
        startActivity(intent);
    }

}
