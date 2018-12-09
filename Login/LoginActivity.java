package com.example.ogedaysunar.se301;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        a=(TextView)findViewById(R.id.registertext);
        SpannableString content = new SpannableString("If you haven't account Register");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        a.setText(content);
        if (user != null) {
            // User is signed in
            Intent intocan = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intocan);
        } else {
            // No user is signed in
        }


        Button clickButton = (Button) findViewById(R.id.login);
        final EditText edt1 = (EditText) findViewById(R.id.email);
        final EditText edt2 = (EditText) findViewById(R.id.password);

        Button forgotPass = (Button) findViewById(R.id.forgotPass);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt1.getText().toString();
                String parola = edt2.getText().toString();

                if(TextUtils.isEmpty(email) || !isEmailValid(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(parola)){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_SHORT).show();
                }

                else if(parola.length() < 5){
                    Toast.makeText(getApplicationContext(),"Your password is wrong",Toast.LENGTH_SHORT).show();

                }else{
                    mAuth.signInWithEmailAndPassword(email, parola)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Your Login is Success",Toast.LENGTH_SHORT).show();
                                        Intent intocan = new Intent(LoginActivity.this, Main4Activity.class);
                                        startActivity(intocan);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Login Failed",Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(LoginActivity.this, forgotPassword.class);
                startActivity(intocan);
            }
        });
    }
 public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public void openActivity15(View view) {
        Intent register = new Intent(Main3Activity.this, MainActivity.class);
        startActivity(register);
    }

}
