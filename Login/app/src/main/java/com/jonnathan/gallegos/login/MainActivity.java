package com.jonnathan.gallegos.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button ingresar, reristrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ControlComponentes();
        EventosBotones();
        mAuth = FirebaseAuth.getInstance();
    }

    private void ControlComponentes(){
        email = (EditText) findViewById(R.id.etEmailMP);
        password = (EditText) findViewById(R.id.etPasswordMP);
        ingresar = (Button) findViewById(R.id.btnIngresarMP);
        reristrar = (Button) findViewById(R.id.btnRegistrarMP);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void EventosBotones(){
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarCampos();
            }
        });
        reristrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro_user = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(registro_user);
            }
        });
    }

    private void ValidarCampos(){
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(this, "Debe ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
        }else{
            VerificarUserAcces();
        }
    }
    private void VerificarUserAcces(){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()){
                                Intent pasoView = new Intent(getApplicationContext(), VistaPrincipal.class);
                                //pasoView.putExtra("clave", email.getText().toString());
                                Toast.makeText(getBaseContext(), "Algo pasa"+email.getText().toString(), Toast.LENGTH_SHORT).show();
                                startActivity(pasoView);
                                password.setText("");
                                Toast.makeText(getBaseContext(), "Algo"+email.getText().toString(), Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(MainActivity.this, "El correo electronico no a sido verificado.", Toast.LENGTH_SHORT).show();
                            }

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

}