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

public class CreateAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailuser, passwordOne, repetPassword;
    private Button btnCreateAcco, btncanceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        AsignacionControles();
        ControlEventos();
        mAuth = FirebaseAuth.getInstance();
    }

    private void AsignacionControles(){
        emailuser = (EditText) findViewById(R.id.etTextEmailAddress);
        passwordOne = (EditText) findViewById(R.id.etTextPassword);
        repetPassword = (EditText) findViewById(R.id.etTextPasswordRepeat);
        btnCreateAcco = (Button) findViewById(R.id.btnCreateAccountVC);
        btncanceButton = (Button) findViewById(R.id.btnCancelAccountCA);
    }

    private void ControlEventos(){
        btnCreateAcco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUserValidar();
            }
        });
        btncanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelar = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cancelar);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void RegistrarUserValidar(){
        if(emailuser.getText().toString().isEmpty() || passwordOne.getText().toString().isEmpty() || repetPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Verifique si todos los campos estan llenos.", Toast.LENGTH_SHORT).show();
        }else{
            if(passwordOne.getText().toString().equals(repetPassword.getText().toString())){
                RegistroUser();
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void RegistroUser(){
        mAuth.createUserWithEmailAndPassword(emailuser.getText().toString(), passwordOne.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            Toast.makeText(getApplicationContext(), "Cuenta creada satisfactoriamente, Verifique su acceso.", Toast.LENGTH_SHORT).show();
                            Intent LoginUser = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(LoginUser);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(getApplicationContext(), "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(getApplicationContext(), "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(getApplicationContext(), "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(getApplicationContext(), "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                emailuser.setError("La dirección de correo electrónico está mal formateada.");
                emailuser.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(getApplicationContext(), "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                passwordOne.setError("la contraseña es incorrecta ");
                passwordOne.requestFocus();
                passwordOne.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(getApplicationContext(), "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(getApplicationContext(),"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(getApplicationContext(), "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(getApplicationContext(), "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                emailuser.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                emailuser.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(getApplicationContext(), "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(getApplicationContext(), "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(getApplicationContext(), "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(getApplicationContext(), "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(getApplicationContext(), "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(getApplicationContext(), "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(getApplicationContext(), "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                passwordOne.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                passwordOne.requestFocus();
                break;

        }

    }
}