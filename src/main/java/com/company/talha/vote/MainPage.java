package com.company.talha.vote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.Date;

/**
 * The type Main page.
 */
public class MainPage extends AppCompatActivity {
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The Leaders 1.
     */
    ArrayList<Leaders> leaders1;
    /**
     * The Leaders 2.
     */
    ArrayList<Leaders> leaders2;
    /**
     * The Leader adapter.
     */
    LeaderAdapter LeaderAdapter;
    /**
     * The Toolbar 1.
     */
    Toolbar toolbar1;
    /**
     * The Sdf 2.
     */
    SimpleDateFormat sdf2;
    /**
     * The Image button 4.
     */
    ImageButton imageButton4, /**
     * The Image button 5.
     */
    imageButton5, /**
     * The Image button 6.
     */
    imageButton6;
    /**
     * The My ref 1.
     */
    DatabaseReference myRef1 = database.getReference("Elections");
    /**
     * The My ref 3.
     */
    DatabaseReference myRef3 = database.getReference("Elections");
    /**
     * The My ref 2.
     */
    DatabaseReference myRef2 = database.getReference();
    /**
     * The Image view.
     */
    ImageView imageView, /**
     * The Adres 14 k.
     */
    adres14k;
    /**
     * The Tarih.
     */
    Date tarih = new Date();
    /**
     * The Text view 1.
     */
    TextView textView1;
    /**
     * The Leaderlayout.
     */
    RelativeLayout leaderlayout, /**
     * The Anasayfa.
     */
    anasayfa;
    /**
     * The Butonlar.
     */
    LinearLayout butonlar,yazilar;
    /**
     * The Leaderlist.
     */
    ListView leaderlist;
    /**
     * The Id.
     */
    String id;

    /**
     * The Category.
     */
    String category;
    boolean k=false;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference menuL2 = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar");
    private DatabaseReference menuL3 = FirebaseDatabase.getInstance().getReference().child("Leaders");
    private DatabaseReference dbUser2=FirebaseDatabase.getInstance().getReference("Kullanıcılar");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        sdf2 = new SimpleDateFormat("dd");

        LeaderAdapter=new LeaderAdapter(this);
        leaderlist=(ListView)findViewById(R.id.leaderlist);
        leaders2=new ArrayList<>();
        leaderlist.setAdapter(LeaderAdapter);
        leaders1=new ArrayList<Leaders>();
        leaderlayout=(RelativeLayout)findViewById(R.id.leadership);
        leaderlayout.setVisibility(View.INVISIBLE);
        imageButton4=(ImageButton)findViewById(R.id.imageButton4);
        imageButton5=(ImageButton)findViewById(R.id.imageButton5);
        imageButton6=(ImageButton)findViewById(R.id.imageButton6);

        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        anasayfa=(RelativeLayout)findViewById(R.id.anasayfa);
        butonlar=(LinearLayout) findViewById(R.id.butonlar);
        yazilar=(LinearLayout) findViewById(R.id.yazilar);
        imageView=(ImageView)findViewById(R.id.imageView);
        textView1=(TextView) findViewById(R.id.textView);
        final TextView leaderheader=new TextView(this);
        leaderheader.setText("LEADERS");
        adres14k=(ImageView)findViewById(R.id.adres14k);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderlayout.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                textView1.setVisibility(View.INVISIBLE);
                yazilar.setVisibility(View.INVISIBLE);
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()==null){
                    Toast.makeText(MainPage.this,"Lütfen giriş yapınız!",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intentApply=new Intent(MainPage.this,ApplyElections.class);
                    startActivity(intentApply);

                }
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVote=new Intent(MainPage.this,Elections.class);
                startActivity(intentVote);
            }
        });
        adres14k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaderlayout.setVisibility(View.INVISIBLE);
                butonlar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                yazilar.setVisibility(View.VISIBLE);
            }
        });
        if (mAuth.getCurrentUser() != null) {
            menuL2.child(mAuth.getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    if(value.equals("")){
                        Intent intent12=new Intent(MainPage.this,Login.class);
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
                            String finishdate = ds1.child("finishDate").getValue(String.class).substring(8,10);
                            Integer a,b;
                            a=Integer.parseInt(sdf2.format(tarih));
                            b=Integer.parseInt(finishdate);
                            if(a>b){
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

    /**
     * The type Leader adapter.
     */
    class LeaderAdapter extends BaseAdapter {
        /**
         * The User ınflater.
         */
        LayoutInflater userInflater;
        /**
         * The List of elections.
         */
        ArrayList<Leaders> ListOfElections = new ArrayList();

        /**
         * Instantiates a new Leader adapter.
         *
         * @param activity the activity
         */
        LeaderAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        /**
         * Add ıtem.
         *
         * @param item the item
         */
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

        /**
         * Delete all.
         */
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

    /**
     * Sort array list array list.
     *
     * @param k the k
     * @return the array list
     */
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
