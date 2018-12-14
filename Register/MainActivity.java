package com.company.talha.vote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;
    private DatabaseReference dbUser=FirebaseDatabase.getInstance().getReference("Kullanıcılar");
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
                if(a.length()>10 && name.length()>=2 && surname.length()>=2 && isEmailValid(a.getText().toString())){

                    String d =a.getText().toString();
                    String e=b.getText().toString();
                    if(e.length()>8 && isValidPassword(b.getText().toString())){


                    String f=confirmpassword.getText().toString();
                    if(e.equals(f)){
                      mAuth.createUserWithEmailAndPassword(d,e).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              FirebaseUser currentUser = mAuth.getCurrentUser();
                              if(currentUser!=null){
                                  currentUser.sendEmailVerification();
                              }
                              FirebaseUser user = task.getResult().getUser();
                              User user1=new User(a.getText().toString(),name.getText().toString(),surname.getText().toString());
                              dbUser.child(user.getUid()).setValue(user1);
                              demDownload1.execute();
                              circularProgressButton.setEnabled(false);
                              AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                              builder1.setTitle("Onay Linki");
                              builder1.setMessage("Lütfen email adresinize gelen linki onaylayınız!");
                              builder1.setCancelable(false);
                              builder1.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {
                                      Intent authIntent=new Intent(MainActivity.this,Main3Activity.class);
                                      startActivity(authIntent);
                                  }
                              });
                              builder1.show();
                          }
                      });
                    }else{
                        Toast.makeText(MainActivity.this,"Passwords are not matching!",Toast.LENGTH_SHORT).show();
                    }
                    }else{
                        Toast.makeText(MainActivity.this,"Your password is not valid",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Please fill all the required blanks!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
