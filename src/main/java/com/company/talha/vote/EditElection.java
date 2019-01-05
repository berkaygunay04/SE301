package com.company.talha.vote;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditElection extends AppCompatActivity {
    private DatabaseReference apply = FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef3 = database.getReference("RejectedElections");
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference dynamic = database.getReference("Category");
    SimpleDateFormat sdf,
            sdf2;
    Date tarih = new Date();
        String id;
         int day1;
         int month1;
         int year1;
        String choosen;
        Button editbutton;
        Button pickdate;
        TextView etTarih;
        Spinner spinner;
    ArrayList<String> plants1;
        EditText electionname1,electionquestion,electionOption1,electionOption2,electionOption3;
    DatabaseReference myRef = database.getReference("RejectedElections");
    private DatabaseReference menuL5= FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_election);
        selection();
        spinner = (Spinner)findViewById(R.id.spinner1);
        electionname1=(EditText)findViewById(R.id.electionname1);
        electionquestion=(EditText)findViewById(R.id.question1);
        electionOption1=(EditText)findViewById(R.id.option11);
        electionOption2=(EditText)findViewById(R.id.option21);
        electionOption3=(EditText)findViewById(R.id.option31);
        pickdate=(Button)findViewById(R.id.pickdate1);
        editbutton=(Button)findViewById(R.id.applybutton1);
        etTarih=(TextView)findViewById(R.id.edittext_tarih1);
        etTarih.setPaintFlags(etTarih.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        plants1=new ArrayList<String>();
        dynamic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plants1=new ArrayList<String>();
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    String dynamicCategory=ds1.getValue(String.class);
                    if(dynamicCategory!=null){
                        plants1.add(dynamicCategory);
                    }
                }
                ArrayAdapter<String> spinnerArrayAdaptor = new ArrayAdapter<String>(EditElection.this,R.layout.spinner_item,plants1);
                spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(spinnerArrayAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.text.DecimalFormat nft = new
                        java.text.DecimalFormat("#00.###");
                nft.setDecimalSeparatorAlwaysShown(false);

                sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                sdf2.format(tarih);
                int yearnow= Integer.parseInt(sdf2.format(tarih).substring(0,4));
                int monthnow= Integer.parseInt(sdf2.format(tarih).substring(5,7));
                int daynow= Integer.parseInt(sdf2.format(tarih).substring(8,10));
                if(year1==yearnow){
                    if(month1==monthnow){
                        if(day1>daynow){
                            if(electionname1.getText().length()<=0 || electionquestion.getText().length()<=0 ||electionOption1.getText().length()<=0 ||electionOption2.getText().length()<=0 ||electionOption3.getText().length()<=0){
                                Toast.makeText(getApplicationContext(), "Please fill the all blanks!", Toast.LENGTH_SHORT).show();
                            }else{
                                //String id=apply.push().getKey();
                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));//başlangıç
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("finishDate").setValue(nft.format(day1)+"-"+nft.format(month1)+"-"+year1);//bitiş
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Type").setValue("User Type");
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(electionOption1.getText().toString());
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(electionOption2.getText().toString());
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(electionOption3.getText().toString());

                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1C").setValue(0);
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2C").setValue(0);
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3C").setValue(0);

                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(electionname1.getText().toString());
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(electionquestion.getText().toString());
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("AdminOnay").setValue(false);
                                apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Rejected").setValue(true);

                                myRef3.child(choosen).child(id).child("DateType").setValue(null);
                                myRef3.child(choosen).child(id).child("ElectionName").setValue(null);
                                myRef3.child(choosen).child(id).child("Option1").setValue(null);
                                myRef3.child(choosen).child(id).child("Option2").setValue(null);
                                myRef3.child(choosen).child(id).child("Option3").setValue(null);
                                myRef3.child(choosen).child(id).child("Question").setValue(null);
                                myRef3.child(choosen).child(id).child("Reason").setValue(null);
                                myRef3.child(choosen).child(id).child("finishDate").setValue(null);


                                AlertDialog.Builder builder1=new AlertDialog.Builder(EditElection.this);
                                builder1.setTitle("Confirmation");
                                builder1.setMessage("Your election edit is received to us!");
                                builder1.setCancelable(false);
                                builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent authIntent=new Intent(EditElection.this,MainPage.class);
                                        startActivity(authIntent);
                                    }
                                });
                                builder1.show();
                            }
                        }
                        if(day1<=daynow){
                            Toast.makeText(getApplicationContext(), "You entered unvalid date!", Toast.LENGTH_SHORT).show();
                        }
                    }if(month1>monthnow){
                        if(electionname1.getText().length()<=0 || electionquestion.getText().length()<=0 ||electionOption1.getText().length()<=0 ||electionOption2.getText().length()<=0 ||electionOption3.getText().length()<=0){
                            Toast.makeText(getApplicationContext(), "Please fill the all blanks!", Toast.LENGTH_SHORT).show();
                        }else{
                            //String id=apply.push().getKey();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));//başlangıç
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("finishDate").setValue(nft.format(day1)+"-"+nft.format(month1)+"-"+year1);//bitiş
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Type").setValue("User Type");
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(electionOption1.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(electionOption2.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(electionOption3.getText().toString());

                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1C").setValue(0);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2C").setValue(0);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3C").setValue(0);

                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(electionname1.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(electionquestion.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("AdminOnay").setValue(false);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Rejected").setValue(true);

                            myRef3.child(choosen).child(id).child("DateType").setValue(null);
                            myRef3.child(choosen).child(id).child("ElectionName").setValue(null);
                            myRef3.child(choosen).child(id).child("Option1").setValue(null);
                            myRef3.child(choosen).child(id).child("Option2").setValue(null);
                            myRef3.child(choosen).child(id).child("Option3").setValue(null);
                            myRef3.child(choosen).child(id).child("Question").setValue(null);
                            myRef3.child(choosen).child(id).child("Reason").setValue(null);
                            myRef3.child(choosen).child(id).child("finishDate").setValue(null);

                            AlertDialog.Builder builder1=new AlertDialog.Builder(EditElection.this);
                            builder1.setTitle("Confirmation");
                            builder1.setMessage("Your election edit is received to us!");
                            builder1.setCancelable(false);
                            builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent authIntent=new Intent(EditElection.this,MainPage.class);
                                    startActivity(authIntent);
                                }
                            });
                            builder1.show();
                        }
                    }if(month1<monthnow){
                        Toast.makeText(getApplicationContext(), "You entered unvalid date!", Toast.LENGTH_SHORT).show();
                    }
                }
                if(year1>yearnow){
                    if(electionname1.getText().length()<=0 || electionquestion.getText().length()<=0 ||electionOption1.getText().length()<=0 ||electionOption2.getText().length()<=0 ||electionOption3.getText().length()<=0){
                        Toast.makeText(getApplicationContext(), "Please fill the all blanks!", Toast.LENGTH_SHORT).show();
                    }else{
                       // String id=apply.push().getKey();
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));//başlangıç
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("finishDate").setValue(nft.format(day1)+"-"+nft.format(month1)+"-"+year1);//bitiş
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Type").setValue("User Type");
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(electionOption1.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(electionOption2.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(electionOption3.getText().toString());

                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1C").setValue(0);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2C").setValue(0);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3C").setValue(0);

                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(electionname1.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(electionquestion.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("AdminOnay").setValue(false);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Rejected").setValue(true);

                        myRef3.child(choosen).child(id).child("DateType").setValue(null);
                        myRef3.child(choosen).child(id).child("ElectionName").setValue(null);
                        myRef3.child(choosen).child(id).child("Option1").setValue(null);
                        myRef3.child(choosen).child(id).child("Option2").setValue(null);
                        myRef3.child(choosen).child(id).child("Option3").setValue(null);
                        myRef3.child(choosen).child(id).child("Question").setValue(null);
                        myRef3.child(choosen).child(id).child("Reason").setValue(null);
                        myRef3.child(choosen).child(id).child("finishDate").setValue(null);

                        AlertDialog.Builder builder1=new AlertDialog.Builder(EditElection.this);
                        builder1.setTitle("Confirmation");
                        builder1.setMessage("Your election apply is received to us!");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent authIntent=new Intent(EditElection.this,MainPage.class);
                                startActivity(authIntent);
                            }
                        });
                        builder1.show();
                    }
                }
                if(year1<yearnow){
                    Toast.makeText(getApplicationContext(), "You entered unvalid date!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int yil = takvim.get(Calendar.YEAR);
                int ay = takvim.get(Calendar.MONTH);
                int gun = takvim.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(EditElection.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // ay değeri 0 dan başladığı için (Ocak=0, Şubat=1,..,Aralık=11)
                        // değeri 1 artırarak gösteriyoruz.
                        month += 1;
                        // year, month ve dayOfMonth değerleri seçilen tarihin değerleridir.
                        // Edittextte bu değerleri gösteriyoruz.
                        etTarih.setText(dayOfMonth + "/" + month + "/" + year);
                        day1=dayOfMonth;
                        month1=month;
                        year1=year;
                    }
                }, yil, ay, gun);
// datepicker açıldığında set edilecek değerleri buraya yazıyoruz.
// şimdiki zamanı göstermesi için yukarıda tanımladığımız değişkenleri kullanıyoruz.

// dialog penceresinin button bilgilerini ayarlıyoruz ve ekranda gösteriyoruz.
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals(choosen)){
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            if (ds1.getKey().equals(id)) {
                                String name = ds1.child("Option1").getValue(String.class);
                                String date = ds1.child("Option2").getValue(String.class);
                                String type = ds1.child("Option3").getValue(String.class);
                                String question = ds1.child("Question").getValue(String.class);
                                String electionName = ds1.child("ElectionName").getValue(String.class);
                                electionOption1.setText(name);
                                electionOption2.setText(date);
                                electionOption3.setText(type);
                                electionquestion.setText(question);
                                electionname1.setText(electionName);
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
            id = (getIntent().getExtras().getString("electionid"));
            choosen = (getIntent().getExtras().getString("category"));
            //just test for selection post   Toast.makeText(Election.this, "Selection " + selection.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
