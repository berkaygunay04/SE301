package com.company.talha.vote;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;
 EditText a,b;
 EditText name,surname,confirmpassword;
    CircularProgressButton circularProgressButton;
    Timer time;
    AsyncTask<String,String,String> demDownload1;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        surname=(EditText)findViewById(R.id.surname);
        confirmpassword=(EditText)findViewById(R.id.confirmpass);
        a=(EditText)findViewById(R.id.email);
        b=(EditText)findViewById(R.id.password);

        circularProgressButton=(CircularProgressButton)findViewById(R.id.kodgir);
        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demDownload1=new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... voids) {
                        try{
                            Thread.sleep(1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return "done";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if(s.equals("done")){
                            Toast.makeText(MainActivity.this,"İşlem Başarılı",Toast.LENGTH_SHORT).show();
                            circularProgressButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));
                        }
                    }
                };
                if(a.length()>10 && name.length()>5 && surname.length()>5){
                    String d =a.getText().toString();
                    String e=b.getText().toString();
                    String f=confirmpassword.getText().toString();
                    if(e.equals(f)){
                      mAuth.createUserWithEmailAndPassword(d,e).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              FirebaseUser currentUser = mAuth.getCurrentUser();
                              if(currentUser!=null){
                                  currentUser.sendEmailVerification();
                              }
                              demDownload1.execute();
                              circularProgressButton.setEnabled(false);
                          }
                      });
                    }else{
                        Toast.makeText(MainActivity.this,"Passwords are not matching!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Please fill all the required blanks!",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
