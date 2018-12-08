package com.example.ogedaysunar.se301;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
        setContentView(R.layout.forgotpassword);

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
                                    Toast.makeText(getApplicationContext(), "E-mail Sended.",Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), "Upss! Somethings is wrong.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
