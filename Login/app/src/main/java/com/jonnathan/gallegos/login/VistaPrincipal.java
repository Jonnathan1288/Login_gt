package com.jonnathan.gallegos.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class VistaPrincipal extends AppCompatActivity {

    private TextView tvsalida;
    private Button btnSalirView;
    //private ImageView imgViewS;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Asignacion();

        if(user != null){
            String gmail = user.getEmail();
            String username = user.getDisplayName();

            tvsalida.setText("Bienvenido "+gmail);

            //Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.ic_launcher_background).into(imgViewS);
        }else{
            getApplicationContext();
        }
        //SalidaVista();
        eventosBortones();
    }

    private void Asignacion(){
        tvsalida = (TextView) findViewById(R.id.tvsalida);
        btnSalirView = (Button) findViewById(R.id.btnsalirVMP);
        //imgViewS = (ImageView) findViewById(R.id.ImgViewPicture);
    }

    private void eventosBortones(){
        btnSalirView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    private void SalidaVista(){
        String salida = getIntent().getStringExtra("clave");
        tvsalida.setText("Bienvenido "+salida);
    }
}