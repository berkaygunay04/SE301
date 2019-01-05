package com.company.talha.vote;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class EditDeleted extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference dynamic = database.getReference("Category");
    Spinner spinner;
    String choosen,id;
    Button pickdate;
    ArrayList<String> plants1;
    int day1;
    int month1;
    int year1;
    TextView etTarih;
    TextView electionname11,electionquestion11,electionOption11,electionOption21,electionOption31;
    DatabaseReference myRef = database.getReference("DeleteElections");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deleted);
        selection();
        etTarih=(TextView)findViewById(R.id.edittext_tarih11);
        etTarih.setPaintFlags(etTarih.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        pickdate=(Button)findViewById(R.id.pickdate11);
        electionname11=(TextView)findViewById(R.id.electionname11);
        electionquestion11=(TextView)findViewById(R.id.question11);
        electionOption11=(TextView)findViewById(R.id.option11);
        electionOption21=(TextView)findViewById(R.id.option21);
        electionOption31=(TextView)findViewById(R.id.option31);
        spinner = (Spinner)findViewById(R.id.spinnerdeleted);
          plants1=new ArrayList<String>();
      /*  String[] plants = new String[]{
                "Sport",
                "Culture",
                "Art",
                "History",
                "Science",
                "Cinema"
        };*/
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
                ArrayAdapter<String> spinnerArrayAdaptor = new ArrayAdapter<String>(EditDeleted.this,R.layout.spinner_item,plants1);
                spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(spinnerArrayAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                                electionOption11.setText("Option 1: "+name);
                                electionOption21.setText("Option 2: "+date);
                                electionOption31.setText("Option 3: "+type);
                                electionquestion11.setText("Election Question: "+question);
                                electionname11.setText("Election Name: "+electionName);
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int yil = takvim.get(Calendar.YEAR);
                int ay = takvim.get(Calendar.MONTH);
                int gun = takvim.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(EditDeleted.this, new DatePickerDialog.OnDateSetListener() {
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
