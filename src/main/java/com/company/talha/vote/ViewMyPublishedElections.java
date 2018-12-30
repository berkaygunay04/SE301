package com.company.talha.vote;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewMyPublishedElections extends AppCompatActivity {
    String id;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Elections");
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;
    String choosen;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_published_elections);
        radio1 = (RadioButton) findViewById(R.id.radiobutton11);
        radio2 = (RadioButton) findViewById(R.id.radiobutton22);
        radio3 = (RadioButton) findViewById(R.id.radiobutton33);
        textView=(TextView) findViewById(R.id.selectiontext1);
        selection();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals(choosen)) {
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            if (ds1.getKey().equals(id)){
                                String name = ds1.child("Option1").getValue(String.class);
                                String date = ds1.child("Option2").getValue(String.class);
                                String type = ds1.child("Option3").getValue(String.class);
                                String question = ds1.child("Question").getValue(String.class);
                                radio1.setText(name);
                                radio2.setText(date);
                                radio3.setText(type);
                                textView.setText(question);
                            }
                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void selection(){
        if (getIntent().getExtras() != null) {
            // selection = (getIntent().getExtras().getString("selection"));
            id = (getIntent().getExtras().getString("electionId"));
            choosen = (getIntent().getExtras().getString("category"));
            //just test for selection post   Toast.makeText(Election.this, "Selection " + selection.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
