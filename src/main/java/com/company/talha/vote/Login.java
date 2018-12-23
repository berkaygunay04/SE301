package com.company.talha.vote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Login.
 */
public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    /**
     * The A.
     */
    TextView a;
    Button forgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        forgot=(Button)findViewById(R.id.forgotPass);
        if(mAuth!=null){
            mAuth.signOut();
        }
        a=(TextView)findViewById(R.id.registertext);
        SpannableString content = new SpannableString("If you haven't account Register");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        a.setText(content);
        forgot.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
            Intent intent = new Intent(Login.this, forgotPassword.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            }
            });
        Button clickButton = (Button) findViewById(R.id.login);
        final EditText edt1 = (EditText) findViewById(R.id.email);
        final EditText edt2 = (EditText) findViewById(R.id.password);
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
                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(mAuth.getCurrentUser().isEmailVerified()){
                                            if (task.isSuccessful()) {
                                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Notifications");
                                                reference.child("token").setValue(FirebaseInstanceId.getInstance().getToken());
                                                Toast.makeText(getApplicationContext(), "Your Login is Success",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Login.this, MainPage.class)
                                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                finish();
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "Login Failed",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Please Verify Your Email!",Toast.LENGTH_SHORT).show();
                                        }
                                        // ...
                                    }
                                });

                }
            }
        });
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Open activity 15.
     *
     * @param view the view
     */
    public void openActivity15(View view) {
        Intent register = new Intent(Login.this, Register.class);
        startActivity(register);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this, MainPage.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
