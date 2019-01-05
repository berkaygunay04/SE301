package com.company.talha.vote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.PropertyName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class viewprofile extends AppCompatActivity {
    TextView showname;
    TextView showsurname;
    Button edit;
    String url;
    ImageView userimage;
    TextView mail;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference dbUser2=FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
    private DatabaseReference dbUser=FirebaseDatabase.getInstance().getReference("Kullanıcılar");
    Toolbar toolbar345;
    Button freeze;
    private Uri filepath;
    FirebaseStorage storage;
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        userimage = (ImageView) findViewById(R.id.userimg);
        edit = (Button) findViewById(R.id.edit);
        toolbar345=(Toolbar)findViewById(R.id.toolba345);
        storage=FirebaseStorage .getInstance();
        storageRef=storage.getReference();
        setSupportActionBar(toolbar345);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        showname = (TextView) findViewById(R.id.name);
        freeze=(Button)findViewById(R.id.freeze);
        name(showname);
        showsurname = (TextView) findViewById(R.id.surname);
        surname(showsurname);
        mail = (TextView) findViewById(R.id.mail);
        mail(mail);
        dbUser2.child("image").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     url=dataSnapshot.getValue(String.class);
                     if(url!=null){
                         Picasso.get().load(url).into(userimage);
                     }
            }
             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
            });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewprofile.this, UpdateProfile.class);
                intent.putExtra("imageurl", url.toString());
                startActivity(intent);

            }
        });
        freeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(viewprofile.this);
                builder1.setTitle("Freeze Profile");
                builder1.setMessage("Are you sure about freezing your profile?");
                builder1.setCancelable(false);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        dbUser.child(currentUser.getUid()).child("Freeze").setValue(true);
                        dbUser.child(currentUser.getUid()).child("Free").setValue("Dondurmak istiyorum");
                        Intent authIntent=new Intent(viewprofile.this,MainPage.class);
                        startActivity(authIntent);
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder1.show();


            }
        });
    }
    public void name(final TextView view){
        dbUser2.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                view.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void surname(final TextView view){
        dbUser2.child("surname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                view.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void mail(final TextView view){
        dbUser2.child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                view.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(viewprofile.this, ProfileMenu.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
