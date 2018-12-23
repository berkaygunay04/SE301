package com.company.talha.vote;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * The type Election.
 */
public class Election extends AppCompatActivity {//Election View
    /**
     * The constant Channel_1_ID.
     */
    public  static  final String Channel_1_ID="channel1";
    /**
     * The constant Channel_2_ID.
     */
    public  static  final String Channel_2_ID="channel2";
    private NotificationManagerCompat notificationManagerCompat;
    /**
     * The Selection.
     */
    public String selection;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    /**
     * The Id.
     */
    String id, /**
     * The Idelection.
     */
    idelection, /**
     * The Anonymi.
     */
    Anonymi;
    /**
     * The B.
     */
    Button b;
    /**
     * The Value.
     */
    Integer value, /**
     * The Valuecount.
     */
    valuecount , /**
     * The Opt 2.
     */
    opt2, /**
     * The Opt 3.
     */
    opt3;
    /**
     * The Radio 1.
     */
    RadioButton radio1;
    /**
     * The Radio 2.
     */
    RadioButton radio2;
    /**
     * The Radio 3.
     */
    RadioButton radio3;
    /**
     * The Text view.
     */
    TextView textView;
    /**
     * The Voteboolean.
     */
    boolean voteboolean=true;
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The Anonym.
     */
    DatabaseReference Anonym = database.getReference();
    /**
     * The My ref.
     */
    DatabaseReference myRef = database.getReference("Elections");
    /**
     * The M auth.
     */
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference menuL5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);
        radioGroup=(RadioGroup)findViewById(R.id.selection);
        selection();
        if(mAuth!=null){
            if(mAuth.getCurrentUser()!=null)
            menuL5= FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        textView=(TextView) findViewById(R.id.selectiontext);
        radio1 = (RadioButton) findViewById(R.id.radiobutton1);
        radio2 = (RadioButton) findViewById(R.id.radiobutton2);
        radio3 = (RadioButton) findViewById(R.id.radiobutton3);
        b= (Button) findViewById(R.id.vote);
        notificationManagerCompat=NotificationManagerCompat.from(this);
        createNotification();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals("Sport")) {
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
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null){
                    menuL5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String category = ds.getKey();

                                if (category.equals("Votes")) {

                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                        idelection = ds1.getKey();
                                        if(idelection.equals(id)){
                                            voteboolean=false;
                                        }
                                    }
                                }
                            }
                            if(voteboolean){
                                Integer uservotecount =dataSnapshot.child("UserVoteCount").getValue(Integer.class);
                                uservotecount++;
                                menuL5.child("UserVoteCount").setValue(uservotecount);
                                int selectid= radioGroup.getCheckedRadioButtonId();
                                radioButton = (RadioButton) findViewById(selectid);
                                String voteoption=  radioButton.getText().toString();
                                if(voteoption.equals(radio1.getText().toString())) {
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                String category = ds.getKey();

                                                if (category.equals("Sport")) {
                                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                                        value= ds1.child("Option1C").getValue(Integer.class);
                                                        valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                        value++;
                                                        valuecount++;
                                                        myRef.child("Sport").child(id).child("Option1C").setValue(value);
                                                        myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                        menuL5.child("Votes").child(id).child("SelectedOption").setValue(radio1.getText().toString());
                                                        sendOnChannel1();
                                                        startActivity(new Intent(Election.this, Elections.class));
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                                if(voteoption.equals(radio2.getText().toString())) {
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                String category = ds.getKey();

                                                if (category.equals("Sport")) {
                                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                                        value= ds1.child("Option2C").getValue(Integer.class);
                                                        valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                        value++;
                                                        valuecount++;
                                                        myRef.child("Sport").child(id).child("Option2C").setValue(value);
                                                        myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                        menuL5.child("Votes").child(id).child("SelectedOption").setValue(radio2.getText().toString());
                                                        sendOnChannel1();
                                                        startActivity(new Intent(Election.this, Elections.class));
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                                if(voteoption.equals(radio3.getText().toString())) {
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                String category = ds.getKey();

                                                if (category.equals("Sport")) {
                                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                                        value= ds1.child("Option3C").getValue(Integer.class);
                                                        valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                        value++;
                                                        valuecount++;
                                                        myRef.child("Sport").child(id).child("Option3C").setValue(value);
                                                        myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                        menuL5.child("Votes").child(id).child("SelectedOption").setValue(radio3.getText().toString());
                                                        sendOnChannel1();
                                                        startActivity(new Intent(Election.this, Elections.class));
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }else{
                                Toast.makeText(Election.this,"You already voted this election!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }else{
                    SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    boolean idHasBeenGenerated = prefs.getBoolean("idgenerated", false);

                    if(!idHasBeenGenerated){
                        String AnonymID =Anonym.push().getKey();
                        int selectid1= radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectid1);
                        String voteoption1=  radioButton.getText().toString();
                        if(voteoption1.equals(radio1.getText().toString())) {
                            Anonym.child("AnonymUsers").child(AnonymID).child(id).setValue(radio1.getText().toString());
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String category = ds.getKey();

                                        if (category.equals("Sport")) {
                                            for (DataSnapshot ds1 : ds.getChildren()) {
                                                value= ds1.child("Option1C").getValue(Integer.class);
                                                valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                value++;
                                                valuecount++;
                                                myRef.child("Sport").child(id).child("Option1C").setValue(value);
                                                myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                sendOnChannel1();
                                                startActivity(new Intent(Election.this, Elections.class));
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if(voteoption1.equals(radio2.getText().toString())){
                            Anonym.child("AnonymUsers").child(AnonymID).child(id).setValue(radio2.getText().toString());
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String category = ds.getKey();

                                        if (category.equals("Sport")) {
                                            for (DataSnapshot ds1 : ds.getChildren()) {
                                                value= ds1.child("Option2C").getValue(Integer.class);
                                                valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                value++;
                                                valuecount++;
                                                myRef.child("Sport").child(id).child("Option2C").setValue(value);
                                                myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                sendOnChannel1();
                                                startActivity(new Intent(Election.this, Elections.class));
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if(voteoption1.equals(radio3.getText().toString())){
                            Anonym.child("AnonymUsers").child(AnonymID).child(id).setValue(radio3.getText().toString());
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String category = ds.getKey();

                                        if (category.equals("Sport")) {
                                            for (DataSnapshot ds1 : ds.getChildren()) {
                                                value= ds1.child("Option3C").getValue(Integer.class);
                                                valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                value++;
                                                valuecount++;
                                                myRef.child("Sport").child(id).child("Option3C").setValue(value);
                                                myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                sendOnChannel1();
                                                startActivity(new Intent(Election.this, Elections.class));
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        SharedPreferences.Editor editor=prefs.edit();
                        editor.putBoolean("idgenerated", true);
                        editor.putString("uid",AnonymID);
                        editor.commit();
                    }else{
                         Anonymi = prefs.getString("uid", "IDyok");
                        Anonym.child("AnonymUsers").child(Anonymi).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            idelection = ds.getKey();
                                            if(idelection.equals(id)){
                                                voteboolean=false;
                                            }
                                }
                                if(voteboolean){
                                    int selectid1= radioGroup.getCheckedRadioButtonId();
                                    radioButton = (RadioButton) findViewById(selectid1);
                                    String voteoption1=  radioButton.getText().toString();
                                    if(voteoption1.equals(radio1.getText().toString())) {
                                        Anonym.child("AnonymUsers").child(Anonymi).child(id).setValue(radio1.getText().toString());
                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                    String category = ds.getKey();

                                                    if (category.equals("Sport")) {
                                                        for (DataSnapshot ds1 : ds.getChildren()) {
                                                            value= ds1.child("Option1C").getValue(Integer.class);
                                                            valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                            value++;
                                                            valuecount++;
                                                            myRef.child("Sport").child(id).child("Option1C").setValue(value);
                                                            myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                            sendOnChannel1();
                                                            startActivity(new Intent(Election.this, Elections.class));
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                    if(voteoption1.equals(radio2.getText().toString())){
                                        Anonym.child("AnonymUsers").child(Anonymi).child(id).setValue(radio2.getText().toString());
                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                    String category = ds.getKey();

                                                    if (category.equals("Sport")) {
                                                        for (DataSnapshot ds1 : ds.getChildren()) {
                                                            value= ds1.child("Option2C").getValue(Integer.class);
                                                            valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                            value++;
                                                            valuecount++;
                                                            myRef.child("Sport").child(id).child("Option2C").setValue(value);
                                                            myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                            sendOnChannel1();
                                                            startActivity(new Intent(Election.this, Elections.class));
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    if(voteoption1.equals(radio3.getText().toString())){
                                        Anonym.child("AnonymUsers").child(Anonymi).child(id).setValue(radio3.getText().toString());
                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                    String category = ds.getKey();

                                                    if (category.equals("Sport")) {
                                                        for (DataSnapshot ds1 : ds.getChildren()) {
                                                            value= ds1.child("Option3C").getValue(Integer.class);
                                                            valuecount=ds1.child("VoteCount").getValue(Integer.class);
                                                            value++;
                                                            valuecount++;
                                                            myRef.child("Sport").child(id).child("Option3C").setValue(value);
                                                            myRef.child("Sport").child(id).child("VoteCount").setValue(valuecount);
                                                            sendOnChannel1();
                                                            startActivity(new Intent(Election.this, Elections.class));
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                    }

                                }else{
                                    Toast.makeText(Election.this,"You already voted this election!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

        });
    }

    /**
     * Selection.
     */
    public void selection(){
        if (getIntent().getExtras() != null) {
            // selection = (getIntent().getExtras().getString("selection"));
            id = (getIntent().getExtras().getString("electionid"));
            //just test for selection post   Toast.makeText(Election.this, "Selection " + selection.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void createNotification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel(
                    Channel_1_ID,
                    "Oy Bildirim",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Oy Bildirim");
            NotificationChannel channel2=new NotificationChannel(
                    Channel_2_ID,
                    "Oy Bildirim 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("Oy Bildirim 2");

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

    /**
     * Send on channel 1.
     */
    public void sendOnChannel1(){
        Notification notification=new NotificationCompat.Builder(Election.this,Channel_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Tebrikler")
                .setContentText("Oyunuz Başarı ile Kaydedildi")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.plucky))
                .build();
        notificationManagerCompat.notify(new Random().nextInt(),notification);
    }
}
