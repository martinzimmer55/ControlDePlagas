package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtABM;
    private ImageView imgABM;
    private Button btnPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtABM = (TextView) findViewById(R.id.txtABM);
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


    }

    public void abmCliente (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityABMCliente.class);
        startActivity(intent);
    }
}
