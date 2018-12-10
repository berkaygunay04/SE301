package com.example.berkaygunay.ivoted;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplyElections extends AppCompatActivity {
    EditText ElectionName, Question, Option1, Option2, Option3;
    private DatabaseReference apply = FirebaseDatabase.getInstance().getReference();
    Spinner spinner;
    Date tarih = new Date();


    SimpleDateFormat sdf,sdf2,sdf3;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_elections);
        spinner = (Spinner)findViewById(R.id.spinner);
        b=(Button)findViewById(R.id.applybutton);
        String[] plants = new String[]{
          "Sport",
          "Culture",
                "Art",
                "History",
                "Science",
                "Cinema"


        };
        ArrayAdapter<String> spinnerArrayAdaptor = new ArrayAdapter<String>(this,R.layout.spinner_item,plants);
        spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdaptor);
        ElectionName = (EditText)findViewById(R.id.electionname);
        Question = (EditText)findViewById(R.id.question);
        Option1 = (EditText)findViewById(R.id.option1);
        Option2 = (EditText)findViewById(R.id.option2);
        Option3 = (EditText)findViewById(R.id.option3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                sdf2.format(tarih);
                String id=apply.push().getKey();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(Option1.getText().toString());
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(Option2.getText().toString());
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(Option3.getText().toString());
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(ElectionName.getText().toString());
                apply.child("Elections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(Question.getText().toString());
                AlertDialog.Builder builder1=new AlertDialog.Builder(ApplyElections.this);
                builder1.setTitle("Confirmation");
                builder1.setMessage("Your election apply is received to us!");
                builder1.setCancelable(false);
                builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent authIntent=new Intent(ApplyElections.this,MainActivity.class);
                        startActivity(authIntent);
                    }
                });
                builder1.show();
            }
        });


    }
}
