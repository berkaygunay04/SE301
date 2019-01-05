package com.company.talha.vote;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainPage extends AppCompatActivity {
    private DatabaseReference dbUser=FirebaseDatabase.getInstance().getReference("Kullanıcılar");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<Leaders> leaders1;
    ArrayList<Leaders> leaders2;
    LeaderAdapter LeaderAdapter;
    GridLayout gridLayout1;
    Toolbar toolbar1;
    SimpleDateFormat sdf2,sdf3;
    ImageButton imageButton4,
    imageButton5,
    imageButton6;
    DatabaseReference myRef1 = database.getReference("Elections");
    DatabaseReference myRef3 = database.getReference("Elections");
    DatabaseReference myRef2 = database.getReference();
    ImageView imageView,
    adres14k;
    Date tarih = new Date();
    TextView textView1;
    RelativeLayout leaderlayout,
    anasayfa;
    LinearLayout butonlar,yazilar;
    ListView leaderlist;
    String id;

    String category;
    boolean k=false;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference menuL2 = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar");
    private DatabaseReference menuL3 = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar");
    private DatabaseReference dbUser2=FirebaseDatabase.getInstance().getReference("Kullanıcılar");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        sdf2 = new SimpleDateFormat("dd");
        sdf3 = new SimpleDateFormat("MM");
        gridLayout1=(GridLayout)findViewById(R.id.bistro);
        LeaderAdapter=new LeaderAdapter(this);
        leaderlist=(ListView)findViewById(R.id.leaderlist);
        leaders2=new ArrayList<>();
        leaderlist.setAdapter(LeaderAdapter);
        leaders1=new ArrayList<Leaders>();
        leaderlayout=(RelativeLayout)findViewById(R.id.leadership);
        leaderlayout.setVisibility(View.INVISIBLE);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        anasayfa=(RelativeLayout)findViewById(R.id.anasayfa);
        imageView=(ImageView)findViewById(R.id.imageView);
        textView1=(TextView) findViewById(R.id.textView);
        final TextView leaderheader=new TextView(this);
        leaderheader.setText("LEADERS");
        adres14k=(ImageView)findViewById(R.id.adres14k);
        setSingleItem(gridLayout1);

        adres14k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaderlayout.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                gridLayout1.setVisibility(View.VISIBLE);
            }
        });
        if (mAuth.getCurrentUser() != null) {
            menuL2.child(mAuth.getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String value = dataSnapshot.getValue(String.class);
                        if(value==null){
                            mAuth.signOut();
                        }else{
                            if(value.equals("")){
                                Intent intent12=new Intent(MainPage.this,Login.class);
                                startActivity(intent12);
                            }else{
                                getSupportActionBar().setTitle(value);
                            }

                        }



                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            getSupportActionBar().setTitle("I-Voted");
        }
        if (mAuth.getCurrentUser() != null) {
       menuL3.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Boolean freeze=dataSnapshot.child("Freeze").getValue(Boolean.class);
               String free=dataSnapshot.child("Free").getValue(String.class);
               if(freeze!=null && free!=null){
               if(freeze && !free.equals("Açmak istiyorum")){
                   AlertDialog.Builder builder1=new AlertDialog.Builder(MainPage.this);
                   builder1.setTitle("Your Profile Freezed!");
                   builder1.setMessage("Please repeat sign in or create new account!");
                   builder1.setCancelable(false);
                   builder1.setPositiveButton("Sıgn In", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           mAuth.signOut();
                           Intent authIntent=new Intent(MainPage.this,Login.class);
                           startActivity(authIntent);
                       }

                   });
                   builder1.setNegativeButton("ReFreeze", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           FirebaseUser currentUser = mAuth.getCurrentUser();
                           dbUser.child(currentUser.getUid()).child("Free").setValue("Açmak istiyorum");

                       }
                   });
                   builder1.show();
               }
               if(freeze && free.equals("Açmak istiyorum")){
                   AlertDialog.Builder builder2=new AlertDialog.Builder(MainPage.this);
                   builder2.setTitle("Refreezing Operation");
                   builder2.setMessage("We are doing your profile Refreeze.Please wait 1 or 2 hours!");
                   builder2.setCancelable(false);
                   builder2.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           finish();
                       }
                   });

                   builder2.show();
               }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        }



        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.Profile:
                            Intent intent = new Intent(MainPage.this, ProfileMenu.class);
                            startActivity(intent);
                        break;
                    case R.id.signin2:
                            startActivity(new Intent(MainPage.this, Login.class));
                        break;
                    case R.id.signin3:
                        startActivity(new Intent(MainPage.this, Register.class));
                        break;
                }
                return false;
            }
        });

        dbUser2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LeaderAdapter.deleteAll();
                leaderlist.addHeaderView(leaderheader);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Integer a=ds.child("UserVoteCount").getValue(Integer.class);
                    String name1=ds.child("name").getValue(String.class);
                    String surname1=ds.child("surname").getValue(String.class);
                    if(a!=null){
                        Leaders leader=new Leaders(name1,surname1,a);
                        leaders2.add(leader);
                    }
                }
                ArrayList<Leaders> b=sortArrayList(leaders2);
                LeaderAdapter.addItem(b.get(0));
                LeaderAdapter.addItem(b.get(1));
                LeaderAdapter.addItem(b.get(2));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        k=true;
        if(k){
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {//popular elections
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        category=ds.getKey();
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String finishdate = ds1.child("finishDate").getValue(String.class).substring(0,2);
                            String finishmonth=ds1.child("finishDate").getValue(String.class).substring(3,5);
                            Integer a,b,c,cnow;
                            a=Integer.parseInt(sdf2.format(tarih));
                            b=Integer.parseInt(finishdate);
                            c=Integer.parseInt(finishmonth);
                            cnow=Integer.parseInt(sdf3.format(tarih));
                             System.out.println("cnow"+cnow);
                            System.out.println("c"+c);
                            if(a>b && c==cnow){
                                Boolean admin = ds1.child("AdminOnay").getValue(Boolean.class);
                                String dateType=ds1.child("DateType").getValue(String.class);
                                Boolean durum=ds1.child("Durum").getValue(Boolean.class);
                                String Electionname=ds1.child("ElectionName").getValue(String.class);
                                String Option1=ds1.child("Option1").getValue(String.class);
                                Integer Option1C=ds1.child("Option1C").getValue(Integer.class);
                                String Option2=ds1.child("Option2").getValue(String.class);
                                Integer Option2C=ds1.child("Option2C").getValue(Integer.class);
                                String Option3=ds1.child("Option3").getValue(String.class);
                                Integer Option3C=ds1.child("Option3C").getValue(Integer.class);
                                String Question=ds1.child("Question").getValue(String.class);
                                String Type=ds1.child("Type").getValue(String.class);
                                Integer VoteCount=ds1.child("VoteCount").getValue(Integer.class);
                                String finishdate1=ds1.child("finishDate").getValue(String.class);



                                myRef2.child("Results").child(category).child(id).child("AdminOnay").setValue(admin);
                                myRef2.child("Results").child(category).child(id).child("DateType").setValue(dateType);
                                myRef2.child("Results").child(category).child(id).child("ElectionName").setValue(Electionname);
                                myRef2.child("Results").child(category).child(id).child("Option1").setValue(Option1);
                                myRef2.child("Results").child(category).child(id).child("Option1C").setValue(Option1C);
                                myRef2.child("Results").child(category).child(id).child("Option2").setValue(Option2);
                                myRef2.child("Results").child(category).child(id).child("Option2C").setValue(Option2C);
                                myRef2.child("Results").child(category).child(id).child("Option3").setValue(Option3);
                                myRef2.child("Results").child(category).child(id).child("Option3C").setValue(Option3C);
                                myRef2.child("Results").child(category).child(id).child("Question").setValue(Question);
                                myRef2.child("Results").child(category).child(id).child("Type").setValue(Type);
                                myRef2.child("Results").child(category).child(id).child("VoteCount").setValue(VoteCount);
                                myRef2.child("Results").child(category).child(id).child("finishDate").setValue(finishdate1);
                                myRef2.child("Results").child(category).child(id).child("Durum").setValue(durum);




                                myRef3.child(category).child(id).child("AdminOnay").setValue(null);
                                myRef3.child(category).child(id).child("DateType").setValue(null);
                                myRef3.child(category).child(id).child("Durum").setValue(null);
                                myRef3.child(category).child(id).child("ElectionName").setValue(null);
                                myRef3.child(category).child(id).child("Option1").setValue(null);
                                myRef3.child(category).child(id).child("Option1C").setValue(null);
                                myRef3.child(category).child(id).child("Option2").setValue(null);
                                myRef3.child(category).child(id).child("Option2C").setValue(null);
                                myRef3.child(category).child(id).child("Option3").setValue(null);
                                myRef3.child(category).child(id).child("Option3C").setValue(null);
                                myRef3.child(category).child(id).child("Question").setValue(null);
                                myRef3.child(category).child(id).child("Type").setValue(null);
                                myRef3.child(category).child(id).child("VoteCount").setValue(null);
                                myRef3.child(category).child(id).child("finishDate").setValue(null);

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            k=false;
        }

    }
    private void setSingleItem(GridLayout gridLayout){
        for(int i=0;i<gridLayout.getChildCount();i++){
            CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI==0){
                        leaderlayout.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                        textView1.setVisibility(View.INVISIBLE);
                        gridLayout1.setVisibility(View.INVISIBLE);
                    }else if(finalI==1){
                        Intent intentVote=new Intent(MainPage.this,Elections.class);
                        if(getSupportActionBar()!=null){
                            intentVote.putExtra("nameUser", getSupportActionBar().getTitle().toString());
                        }
                        startActivity(intentVote);
                    }else if(finalI==2){
                        if(mAuth.getCurrentUser()==null){
                            Toast.makeText(MainPage.this,"Please Login to create elections!",Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intentApply=new Intent(MainPage.this,ApplyElections.class);
                            startActivity(intentApply);

                        }
                    }
                }
            });
        }
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
                        startActivity(new Intent(MainPage.this, ProfileMenu.class));//menü sayfasına gidiyor
                        finish();
                    }
                    if (mAuth.getCurrentUser() == null) {
                        startActivity(new Intent(MainPage.this, Register.class));
                    }
                    //  msg="Profile";
                    break;
            }
            if (mAuth.getCurrentUser() == null) {
                switch (item.getItemId()) {
                    case R.id.signin2:
                        startActivity(new Intent(MainPage.this, Login.class));
                        break;
                    case R.id.signin3:
                        startActivity(new Intent(MainPage.this, Register.class));
                        break;
                }
            }
            // String msg="";
            //  msg="Profile";
        }
        return super.onOptionsItemSelected(item);
    }

    class LeaderAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<Leaders> ListOfElections = new ArrayList();

        LeaderAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final Leaders item) {
            ListOfElections.add(item);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return ListOfElections.size();
        }

        @Override
        public Object getItem(int i) {
            return ListOfElections.get(i);
        }

        public void deleteAll() {
            ListOfElections=new ArrayList<>();
            notifyDataSetChanged();
        }
        @Override
        public long getItemId(int i) {
            return (i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Leaders leaders1 = ListOfElections.get(i);
            View myView = userInflater.inflate(R.layout.leader_items, null);
            TextView leadername = (TextView) myView.findViewById(R.id.leadername);
            TextView leadersurname = (TextView) myView.findViewById(R.id.leadersurname);
            TextView leadervote = (TextView) myView.findViewById(R.id.leadervote);

            leadername.setText(leaders1.getName().toString());
            leadersurname.setText(leaders1.getSurname().toString());
            leadervote.setText("Vote Count= "+ leaders1.getVoteCount());

            return myView;
        }
    }

    public ArrayList<Leaders> sortArrayList(ArrayList<Leaders> k){
        ArrayList<Leaders> a=new ArrayList<>();
        int max=0;
        int p=0;

        for(int i=0;i<k.size();i++) {
            if (k.get(i).voteCount > max) {
                max = k.get(i).voteCount;
                a.add(k.get(i));
                a.set(0, k.get(i));
                p=i;
            }
        }
        k.remove(p);
        max=0;
    for(int i=0;i<k.size();i++) {
        if (k.get(i).voteCount > max) {
            max = k.get(i).voteCount;
            a.add(k.get(i));
            a.set(1, k.get(i));
            p=i;
        }
    }
    k.remove(p);
    max=0;
    for(int i=0;i<k.size();i++) {
        if (k.get(i).voteCount > max) {
            max = k.get(i).voteCount;
            a.add(k.get(i));
            a.set(2, k.get(i));
        }
    }
        return a;
}

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        this.finish();
        //super.onBackPressed();
        //Main12Activity.this.finish();
    }
}
