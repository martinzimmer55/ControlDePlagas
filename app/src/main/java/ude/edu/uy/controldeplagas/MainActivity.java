package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import ude.edu.uy.controldeplagas.connection.HttpUrlConnection;
import ude.edu.uy.controldeplagas.converters.EncodeBase64;

public class MainActivity extends AppCompatActivity {

    private TextView txtABM;
    private ImageView imgABM;
    private Button btnPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*txtABM = (TextView) findViewById(R.id.txtAltaCliente);
        imgABM = (ImageView) findViewById(R.id.imgABM);
        txtABM.setVisibility(View.VISIBLE);
        imgABM.setVisibility(View.VISIBLE);
        String usuario = "pepe";
        btnPrueba = (Button) findViewById(R.id.btnPrueba);
        if (usuario.equals("admin")) {
            btnPrueba.setText(R.string.boton_eliminar);
        } else {
            if (usuario.equals("pepe")) {
                btnPrueba.setText(R.string.boton_guardar);
            } else {
                btnPrueba.setText(R.string.boton_modificar);
            }
        }

        new verificarGet().execute();*/

    }

    public void abmCliente (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityABMCliente.class);
        startActivity(intent);
    }

    /*private class verificarGet extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String authentication = "";
            try {
                authentication = EncodeBase64.encodeUserPassword("vendedor", "Pass01.");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String result = HttpUrlConnection.sendGet("http://192.168.1.3:8080/cliente/", authentication);
            Log.d("Respuesta del server: ", result);
            return null;
        }
    }*/
}
