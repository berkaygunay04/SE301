package com.company.talha.vote;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ApplyElections extends AppCompatActivity {
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    EditText ElectionName,
    Question,
    Option1,
    Option2,
    Option3;
    int day1;
    int month1;
    int year1;
    TextView etTarih;
    private DatabaseReference apply = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference menuL5= FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
    Spinner spinner,spinner1,spinner2,spinner3,spinner4;
    Date tarih = new Date();

    SimpleDateFormat sdf,
    sdf2,
    sdf3;
    Button b,pickdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_elections);
        spinner = (Spinner)findViewById(R.id.spinner);
      /*  spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);*/
        pickdate=(Button)findViewById(R.id.pickdate);
        etTarih=(TextView)findViewById(R.id.edittext_tarih);
        etTarih.setPaintFlags(etTarih.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        b=(Button)findViewById(R.id.applybutton);
        String[] plants = new String[]{
                "Sport",
                "Culture",
                "Art",
                "History",
                "Science",
                "Cinema"
        };
      /*  String[] days = new String[]{
                "01", "02", "03", "04", "05", "06", "08", "09", "10", "11", "12", "13" ,"14", "15", "16", "17", "18", "19","20", "21", "22"
                ,"23", "24", "25", "26", "27", "28","30", "31"
        };
        final String[] month = new String[]{
                "01", "02", "03", "04", "05", "06"  ,"07", "08", "09", "10", "11", "12"
        };
        String[] years = new String[]{
                "2018", "2019", "2020"
        };*/
pickdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Calendar takvim = Calendar.getInstance();
        int yil = takvim.get(Calendar.YEAR);
        int ay = takvim.get(Calendar.MONTH);
        int gun = takvim.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(ApplyElections.this, new DatePickerDialog.OnDateSetListener() {
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
        ArrayAdapter<String> spinnerArrayAdaptor = new ArrayAdapter<String>(this,R.layout.spinner_item,plants);
        spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdaptor);

       /* ArrayAdapter<String> spinnerArrayAdaptor1 = new ArrayAdapter<String>(this,R.layout.spinner_item,days);
        spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(spinnerArrayAdaptor1);

        ArrayAdapter<String> spinnerArrayAdaptor2 = new ArrayAdapter<String>(this,R.layout.spinner_item,month);
        spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
        spinner2.setAdapter(spinnerArrayAdaptor2);

        ArrayAdapter<String> spinnerArrayAdaptor3 = new ArrayAdapter<String>(this,R.layout.spinner_item,years);
        spinnerArrayAdaptor.setDropDownViewResource(R.layout.spinner_item);
        spinner3.setAdapter(spinnerArrayAdaptor3);*/


        ElectionName = (EditText)findViewById(R.id.electionname);
        Question = (EditText)findViewById(R.id.question);
        Option1 = (EditText)findViewById(R.id.option1);
        Option2 = (EditText)findViewById(R.id.option2);
        Option3 = (EditText)findViewById(R.id.option3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*day1=Integer.parseInt(spinner1.getSelectedItem().toString());
             month1=Integer.parseInt(spinner2.getSelectedItem().toString());
             year1=Integer.parseInt(spinner3.getSelectedItem().toString());*/
             /*   if(day1<10){
                  String b="0"+day1;
                        day1=Integer.parseInt(b);
                        }
                if(month1<10){
                    String c="0"+month1;
                    month1=Integer.parseInt(c);
                }*/
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
                           if(ElectionName.getText().length()<=0 || Question.getText().length()<=0 ||Option1.getText().length()<=0 ||Option2.getText().length()<=0 ||Option3.getText().length()<=0){
                               Toast.makeText(getApplicationContext(), "Please fill the all blanks!", Toast.LENGTH_SHORT).show();
                           }else{
                               String id=apply.push().getKey();
                               SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));//başlangıç
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("finishDate").setValue(nft.format(day1)+"-"+nft.format(month1)+"-"+year1);//bitiş
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Type").setValue("User Type");
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(Option1.getText().toString());
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(Option2.getText().toString());
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(Option3.getText().toString());

                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1C").setValue(0);
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2C").setValue(0);
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3C").setValue(0);

                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(ElectionName.getText().toString());
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(Question.getText().toString());
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("AdminOnay").setValue(false);
                               apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Rejected").setValue(false);
                               menuL5.child("Elections").child(id).setValue(id);
                               AlertDialog.Builder builder1=new AlertDialog.Builder(ApplyElections.this);
                               builder1.setTitle("Confirmation");
                               builder1.setMessage("Your election apply is received to us!");
                               builder1.setCancelable(false);
                               builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       Intent authIntent=new Intent(ApplyElections.this,MainPage.class);
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
                        if(ElectionName.getText().length()<=0 || Question.getText().length()<=0 ||Option1.getText().length()<=0 ||Option2.getText().length()<=0 ||Option3.getText().length()<=0){
                            Toast.makeText(getApplicationContext(), "Please fill the all blanks!", Toast.LENGTH_SHORT).show();
                        }else{
                            String id=apply.push().getKey();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));//başlangıç
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("finishDate").setValue(nft.format(day1)+"-"+nft.format(month1)+"-"+year1);//bitiş
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Type").setValue("User Type");
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(Option1.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(Option2.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(Option3.getText().toString());

                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1C").setValue(0);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2C").setValue(0);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3C").setValue(0);

                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(ElectionName.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(Question.getText().toString());
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("AdminOnay").setValue(false);
                            apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Rejected").setValue(false);
                            menuL5.child("Elections").child(id).setValue(id);
                            AlertDialog.Builder builder1=new AlertDialog.Builder(ApplyElections.this);
                            builder1.setTitle("Confirmation");
                            builder1.setMessage("Your election apply is received to us!");
                            builder1.setCancelable(false);
                            builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent authIntent=new Intent(ApplyElections.this,MainPage.class);
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
                    if(ElectionName.getText().length()<=0 || Question.getText().length()<=0 ||Option1.getText().length()<=0 ||Option2.getText().length()<=0 ||Option3.getText().length()<=0){
                        Toast.makeText(getApplicationContext(), "Please fill the all blanks!", Toast.LENGTH_SHORT).show();
                    }else{
                        String id=apply.push().getKey();
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("DateType").setValue(sdf1.format(tarih));//başlangıç
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("finishDate").setValue(nft.format(day1)+"-"+nft.format(month1)+"-"+year1);//bitiş
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Type").setValue("User Type");
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("VoteCount").setValue(0);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1").setValue(Option1.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2").setValue(Option2.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3").setValue(Option3.getText().toString());

                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option1C").setValue(0);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option2C").setValue(0);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Option3C").setValue(0);

                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("ElectionName").setValue(ElectionName.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Question").setValue(Question.getText().toString());
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("AdminOnay").setValue(false);
                        apply.child("WaitingElections").child(spinner.getSelectedItem().toString()).child(id).child("Rejected").setValue(false);
                        menuL5.child("Elections").child(id).setValue(id);
                        AlertDialog.Builder builder1=new AlertDialog.Builder(ApplyElections.this);
                        builder1.setTitle("Confirmation");
                        builder1.setMessage("Your election apply is received to us!");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent authIntent=new Intent(ApplyElections.this,MainPage.class);
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


    }
}
