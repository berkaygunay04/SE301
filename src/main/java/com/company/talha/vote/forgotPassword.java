package com.company.talha.vote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class forgotPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();


        Button clickButton = (Button) findViewById(R.id.doneButton);
        final EditText edt1 = (EditText) findViewById(R.id.email);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt1.getText().toString();
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AlertDialog.Builder builder1=new AlertDialog.Builder(forgotPassword.this);
                                    builder1.setTitle("Forget Password Email sended!");
                                    builder1.setMessage("Please check your email account!");
                                    builder1.setCancelable(false);
                                    builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent authIntent=new Intent(forgotPassword.this,Login.class);
                                            startActivity(authIntent);
                                        }
                                    });
                                    builder1.show();

                                }else{
                                    Toast.makeText(getApplicationContext(), "Upss! Somethings is wrong.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}