package ude.edu.uy.controldeplagas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

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





    public void login (){
        String usuario = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();

    }
}
