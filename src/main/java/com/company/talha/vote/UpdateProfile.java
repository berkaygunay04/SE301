package com.company.talha.vote;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The type Update profile.
 */
public class UpdateProfile extends AppCompatActivity {
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference dbUser2=FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
    /**
     * The Updatename.
     */
    EditText Updatename, /**
     * The Updatesurname.
     */
    Updatesurname;
    /**
     * The Updateemail.
     */
    TextView Updateemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        final EditText newpassword =(EditText) findViewById(R.id.newpassword);
        final EditText againpassword =(EditText) findViewById(R.id.againpassword);
        TextView changepassword =(TextView)findViewById(R.id.changepassword);
        Updatename =(EditText)findViewById(R.id.name);
        setText(Updatename);
        Updatesurname =(EditText)findViewById(R.id.surname);
        setText1(Updatesurname);
        Updateemail=(TextView)findViewById(R.id.emailUpdate);
        setText2(Updateemail);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpassword.getVisibility()==newpassword.VISIBLE){
                    newpassword.setVisibility(View.INVISIBLE);
                    againpassword.setVisibility(View.INVISIBLE);
                }else{
                    newpassword.setVisibility(View.VISIBLE);
                    againpassword.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * Set text.
     *
     * @param view the view
     */
    public void setText(final EditText view){
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

    /**
     * Set text 1.
     *
     * @param view the view
     */
    public void setText1(final EditText view){
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

    /**
     * Set text 2.
     *
     * @param view the view
     */
    public void setText2(final TextView view){
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

}
