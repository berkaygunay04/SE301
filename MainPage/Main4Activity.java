package com.company.talha.vote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    ArrayList<String> leaders;
    Toolbar toolbar1;
    ImageButton ımageButton4;
    ImageView ımageView,adres14k;
    TextView textView1;
    RelativeLayout leaderlayout,anasayfa;
    LinearLayout butonlar;
    ListView leaderlist;
    ArrayAdapter<String> mAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference menuL2 = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar");
    private DatabaseReference menuL3 = FirebaseDatabase.getInstance().getReference().child("Leaders");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        leaderlist=(ListView)findViewById(R.id.leaderlist);
        leaders=new ArrayList<String>();
        leaderlayout=(RelativeLayout)findViewById(R.id.leadership);
        leaderlayout.setVisibility(View.INVISIBLE);
        ımageButton4=(ImageButton)findViewById(R.id.imageButton4);
        mAuth=FirebaseAuth.getInstance();
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        anasayfa=(RelativeLayout)findViewById(R.id.anasayfa);
        butonlar=(LinearLayout) findViewById(R.id.butonlar);
        ımageView=(ImageView)findViewById(R.id.imageView);
        textView1=(TextView) findViewById(R.id.textView);
        final TextView leaderheader=new TextView(this);
        leaderheader.setText("LEADERS");
        adres14k=(ImageView)findViewById(R.id.adres14k);
        ımageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderlayout.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                ımageView.setVisibility(View.INVISIBLE);
                textView1.setVisibility(View.INVISIBLE);
            }
        });
        adres14k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaderlayout.setVisibility(View.INVISIBLE);
                butonlar.setVisibility(View.VISIBLE);
                ımageView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
            }
        });
        if (mAuth.getCurrentUser() != null) {
            menuL2.child(mAuth.getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    if(value.equals("")){
                        Intent intent12=new Intent(Main4Activity.this,Main3Activity.class);
                        startActivity(intent12);
                    }else{
                        getSupportActionBar().setTitle(value);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            getSupportActionBar().setTitle("I-Voted");
        }
        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.Profile:
                            Intent intent = new Intent(Main4Activity.this, Main5Activity.class);
                            startActivity(intent);
                        break;
                    case R.id.sıgnın2:
                            startActivity(new Intent(Main4Activity.this, Main3Activity.class));
                        break;
                }
                return false;
            }
        });
        menuL3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds3 : ds2.getChildren()) {
                        String a = ds3.getValue(String.class);
                        leaders.add(a);
                        leaderlist.addHeaderView(leaderheader);
                        mAdapter = new ArrayAdapter<>(Main4Activity.this,R.layout.simple_list,leaders);
                        leaderlist.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAuth.getCurrentUser() != null) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main2, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mAuth.getCurrentUser() != null) {
            switch (item.getItemId()) {
                case R.id.Profile:
                    mAuth = FirebaseAuth.getInstance();
                    if (mAuth.getCurrentUser() != null) {
                        startActivity(new Intent(Main4Activity.this, Main5Activity.class));//menü sayfasına gidiyor
                        finish();
                    }
                    if (mAuth.getCurrentUser() == null) {
                        startActivity(new Intent(Main4Activity.this, MainActivity.class));
                    }
                    //  msg="Profile";
                    break;
            }
            if (mAuth.getCurrentUser() == null) {
                switch (item.getItemId()) {
                    case R.id.sıgnın2:
                        startActivity(new Intent(Main4Activity.this, MainActivity.class));
                        break;
                }
            }
            // String msg="";
            //  msg="Profile";
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
        //super.onBackPressed();
        //Main12Activity.this.finish();
    }
}
