package com.company.talha.vote;

import android.content.Entity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.webkit.WebView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Admin");
    TextView a;
    String adminemail;
    Button forgot;
  String email;
    String parola;
    WebView k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        StrictMode.ThreadPolicy policy=StrictMode.ThreadPolicy.LAX;
        StrictMode.setThreadPolicy(policy);
        forgot=(Button)findViewById(R.id.forgotPass);
       k =new WebView(this);
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
                email = edt1.getText().toString();
                parola = edt2.getText().toString();

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

                                        if(mAuth.getCurrentUser()!=null){
                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            if (task.isSuccessful()) {
                                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");
                                                reference.child("token").setValue(FirebaseInstanceId.getInstance().getToken());
                                                Toast.makeText(getApplicationContext(), "Your Login is Success", Toast.LENGTH_SHORT).show();
                                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                                            adminemail = ds1.child("email").getValue(String.class);
                                                        }
                                                        if (adminemail.equals(email)) {

                                           /*     try {
                                                    getClient();
                                                    HttpParams params=new BasicHttpParams();
                                                    params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
                                                    HttpClient httpClient=new DefaultHttpClient(params);
                                                    HttpPost httpPost  =new HttpPost("https://nefisss.net/AdminPanel-301/login.html");
                                                    List<NameValuePair> pairs=new ArrayList<>();
                                                    pairs.add(new BasicNameValuePair("email",email));
                                                    pairs.add(new BasicNameValuePair("pass",parola));
                                                    httpPost.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
                                                    HttpResponse response=httpClient.execute(httpPost);
                                                    HttpEntity entity=response.getEntity();
                                                    String response1=EntityUtils.toString(entity);
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                } catch (ClientProtocolException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }*/
                                                            onPageFinished(k, "https://nefisss.net/AdminPanel-301/login.html");

                                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://nefisss.net/AdminPanel-301/login.html")).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            finish();
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);

                                                        } else {
                                                            Intent intent = new Intent(Login.this, MainPage.class)
                                                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            finish();
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please Verify Your Email!", Toast.LENGTH_SHORT).show();
                                        }
                                        // ...
                                    }else{
                                            Toast.makeText(getApplicationContext(), "Please Email or Password is wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

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
    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript: {" +
                "document.getElementById('email').value = '" + email + "';" +
                "document.getElementById('pass').value = '" + parola + "';" +
                "document.getElementById('buttonClick').click();" +
                "};");
    }

    public void openActivity15(View view) {
        Intent register = new Intent(Login.this, Register.class);
        startActivity(register);
    }
    public DefaultHttpClient getClient()
    {
        DefaultHttpClient ret = null;

        //sets up parameters
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        params.setBooleanParameter("http.protocol.expect-continue", false);

        //registers schemes for both http and https
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        registry.register(new Scheme("https", sslSocketFactory, 443));

        ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
        ret = new DefaultHttpClient(manager, params);
        return ret;
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
