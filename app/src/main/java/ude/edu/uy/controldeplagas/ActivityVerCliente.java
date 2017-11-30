package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mzimmer on 25/11/17.
 */

public class ActivityVerCliente extends AppCompatActivity{

    private TextView txtTitulo, txtId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    private Button btnGuardar;
    private Spinner spDepto;
    private ArrayList listaDeptos;
    private ArrayAdapter<String> adapter;
    private String departamento;


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
        departamento = intentAnterior.getStringExtra("departamento");
        spDepto = (Spinner) findViewById(R.id.sp_cliente_departamento);
        new llenarSpiner().execute();
    }

    public void volverMain (View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private class llenarSpiner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            listaDeptos = new ArrayList();
            listaDeptos.add(departamento);
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_text_view, listaDeptos);
            return departamento;
        }

        @Override
        protected void onPostExecute(String s) {
            spDepto.setAdapter(adapter);
            int pos = listaDeptos.indexOf(departamento);
            spDepto.setSelection(pos);
        }
    }
}
