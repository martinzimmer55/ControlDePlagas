package ude.edu.uy.controldeplagas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by goliath on 17/11/2017.
 */

public class ActivityABMCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abm_cliente);
    }


    public void altaCliente (View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityAltaCliente.class);
        startActivity(intent);
    }
}
