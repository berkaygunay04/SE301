package com.company.talha.vote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPublishedElections extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Elections");
    DatabaseReference myRef3 = database.getReference("DeleteElections");
    DatabaseReference myRef1 = database.getReference("Kullanıcılar");
    DatabaseReference myRef4 = database.getReference("RejectedElections");
    ArrayList<ElectionObject> listOfElections = new ArrayList<>();
    ArrayList<ElectionObject> listOfRejectedElections = new ArrayList<>();
    ArrayList<ElectionObject> listOfDeletedElections = new ArrayList<>();
    FirebaseAuth mAuth;
    PublishedAdaptor adapter;
    DeletedAdapter deletedAdapter;
    RejectedAdapter rejectedAdapter;
    ListView listView,deleted,rejected;
    public String Selection;
    ArrayList<String> electionids;
    ArrayList<String> reasons,reasonsDeleted;
    ArrayList<String> choosen,choesedeleted,chosenrejected;
    String id,a,
    kullaniciElectionId;
    boolean l=false;
    String name,choosencategory,
    date,
    type,reasonss,reasonsD;
    TextView appliedText,deletedText,rejectedText;
    Button appiedele,deletedele,rejectedele;
    Toolbar toolbar355;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_published_elections);
        listView = (ListView) findViewById(R.id.published);
        deleted = (ListView) findViewById(R.id.deleted);
        rejected = (ListView) findViewById(R.id.rejected);
        appiedele=(Button)findViewById(R.id.appliedele);
        deletedele=(Button)findViewById(R.id.deletedele);
        rejectedele=(Button)findViewById(R.id.rejectedele);
        appliedText=(TextView)findViewById(R.id.appliedText);
        deletedText=(TextView)findViewById(R.id.deletedTxt);
        rejectedText=(TextView)findViewById(R.id.rejectedTxt);
        mAuth = FirebaseAuth.getInstance();
        adapter=new PublishedAdaptor(this);
        deletedAdapter=new DeletedAdapter(this);
        listView.setAdapter(adapter);
        deleted.setAdapter(deletedAdapter);
        rejectedAdapter=new RejectedAdapter(this);
        rejected.setAdapter(rejectedAdapter);
        toolbar355=(Toolbar)findViewById(R.id.toolba34);
        setSupportActionBar(toolbar355);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        appiedele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deletedText.getVisibility()==deletedText.VISIBLE){
                    deletedText.setVisibility(View.INVISIBLE);
                }
                if(rejectedText.getVisibility()==rejectedText.VISIBLE){
                    rejectedText.setVisibility(View.INVISIBLE);
                }
                if(listOfElections.isEmpty()){
                    appliedText.setVisibility(View.VISIBLE);
                }
                listView.setVisibility(View.VISIBLE);
                deleted.setVisibility(View.INVISIBLE);
                rejected.setVisibility(View.INVISIBLE);
            }
        });
        deletedele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appliedText.getVisibility()==appliedText.VISIBLE){
                    appliedText.setVisibility(View.INVISIBLE);
                }
                if(rejectedText.getVisibility()==rejectedText.VISIBLE){
                    rejectedText.setVisibility(View.INVISIBLE);
                }
                if(listOfDeletedElections.isEmpty()){
                    deletedText.setVisibility(View.VISIBLE);
                }
                listView.setVisibility(View.INVISIBLE);
                rejected.setVisibility(View.INVISIBLE);
                deleted.setVisibility(View.VISIBLE);

            }
        });
        rejectedele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appliedText.getVisibility()==appliedText.VISIBLE){
                    appliedText.setVisibility(appliedText.INVISIBLE);
                }
                if(deletedText.getVisibility()==deletedText.VISIBLE){
                    deletedText.setVisibility(View.INVISIBLE);
                }
                if(listOfRejectedElections.isEmpty()){
                    rejectedText.setVisibility(View.VISIBLE);
                }
                rejected.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                deleted.setVisibility(View.INVISIBLE);

            }
        });
        electionids=new ArrayList<>();
        choosen=new ArrayList<>();
        choesedeleted=new ArrayList<>();
        chosenrejected=new ArrayList<>();
        reasons=new ArrayList<>();
        reasonsDeleted=new ArrayList<>();

                myRef1.child(mAuth.getCurrentUser().getUid()).child("Elections").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        electionids=new ArrayList<>();
                        adapter.deleteAll();
                        for (DataSnapshot ds33 : dataSnapshot.getChildren()) {
                            kullaniciElectionId=ds33.getKey();
                            System.out.println("id :"+ id+"KullanıcıElectionId:"+kullaniciElectionId);
                            electionids.add(kullaniciElectionId);
                           /* if(id.equals(kullaniciElectionId)){
                                l=true;
                            }
                            if(l){
                                if(type!=null){
                                    if (type.equals("User Type")) {
                                        adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                        listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                    }
                                }
                                l=false;
                            }*/
                        }

                        myRef.addValueEventListener(new ValueEventListener() {//applied elections
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                choosen=new ArrayList<>();
                                adapter.deleteAll();
                                listOfElections=new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    a=ds.getKey();
                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                        id = ds1.getKey();
                                        name = ds1.child("ElectionName").getValue(String.class);
                                        date = ds1.child("DateType").getValue(String.class);
                                        type = ds1.child("Type").getValue(String.class);
                                        for(int i=0;i<electionids.size();i++)
                                        if (id.equals(electionids.get(i))) {
                                            if (type != null) {
                                                if (type.equals("User Type")) {
                                                    adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                                    listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                                    choosen.add(a);
                                                }
                                            }
                                        }
                                    }
                                }
                                if(listOfElections.isEmpty()){
                                    appliedText.setVisibility(View.VISIBLE);
                                }
                              }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                        myRef3.addValueEventListener(new ValueEventListener() {//deletedlar burda listeye ekleniyor
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                listOfDeletedElections=new ArrayList<>();
                                choesedeleted=new ArrayList<>();
                                deletedAdapter.deleteAll();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    a=ds.getKey();
                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                        id = ds1.getKey();
                                        name = ds1.child("ElectionName").getValue(String.class);
                                        date = ds1.child("DateType").getValue(String.class);
                                        reasonsD=ds1.child("Reason").getValue(String.class);
                                        for(int i=0;i<electionids.size();i++)
                                            if (id.equals(electionids.get(i))) {
                                                        deletedAdapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                                        listOfDeletedElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                                        choesedeleted.add(a);
                                                        reasonsDeleted.add(reasonsD);
                                            }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        myRef4.addValueEventListener(new ValueEventListener() {//rejectedlar burda listeye ekleniyor
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                listOfRejectedElections=new ArrayList<>();
                                chosenrejected=new ArrayList<>();
                                rejectedAdapter.deleteAll();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    a=ds.getKey();
                                    for (DataSnapshot ds1 : ds.getChildren()) {
                                        id = ds1.getKey();
                                        name = ds1.child("ElectionName").getValue(String.class);
                                        date = ds1.child("DateType").getValue(String.class);
                                        reasonss=ds1.child("Reason").getValue(String.class);
                                        for(int i=0;i<electionids.size();i++)
                                            if (id.equals(electionids.get(i))) {
                                                rejectedAdapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                                listOfRejectedElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                                chosenrejected.add(a);
                                              //  rejectedAdapter.addReason(reasonss);
                                                reasons.add(reasonss);
                                            }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                // listOfElections.add(new Elections("Fatma vs Ayşegül","Time remain : 11 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Selma vs Selen","Time remain : 10 days 3 hour",R.drawable.user1));
                // listOfElections.add(new Elections("Derya vs Fatmagül","Time remain : 10 days 3 hour",R.drawable.adnmin));
                // listOfElections.add(new Elections("Seher vs Ayşenur","Time remain : 9 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Elif vs Deniz","Time remain : 5 days 3 hour",R.drawable.adnmin));
                //listOfElections.add(new Elections("Esra vs Esmanur","Time remain : 4 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Ece vs Ceyda","Time remain : 3 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Elif vs Naz","Time remain : 2 days 3 hour",R.drawable.adnmin));
                //listOfElections.add(new Elections("Esra vs Esma","Time remain : 3 days 3 hour",R.drawable.adnmin));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Test ok  Toast.makeText(vote.this,listOfElections.get(i).name, Toast.LENGTH_SHORT).show();
                Selection = listOfElections.get(i).name;
                id = listOfElections.get(i).id.toString();
                choosencategory=choosen.get(i).toString();
                selector();
            }
        });
        deleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Selection = listOfDeletedElections.get(position).name;
                id = listOfDeletedElections.get(position).id.toString();
                choosencategory=choesedeleted.get(position).toString();
                selector();
            }
        });
        rejected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Selection = listOfRejectedElections.get(position).name;
                id = listOfRejectedElections.get(position).id.toString();
                choosencategory=chosenrejected.get(position).toString();
                selector();
            }
        });

    }

    public void selector() {
        Intent intent = new Intent(this, ViewMyPublishedElections.class);
        // intent.putExtra("selection",Selection.toString());
        intent.putExtra("electionId", id.toString());
        intent.putExtra("category", choosencategory.toString());
        startActivity(intent);

    }

    class PublishedAdaptor extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<ElectionObject> ListOfElections = new ArrayList();

        PublishedAdaptor(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final ElectionObject item) {
            ListOfElections.add(item);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return ListOfElections.size();
        }
        public void deleteAll() {
            ListOfElections=new ArrayList<>();
        }
        @Override
        public Object getItem(int i) {
            return ListOfElections.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ElectionObject elections = ListOfElections.get(i);
            View myView = userInflater.inflate(R.layout.published_items, null);
            TextView election = (TextView) myView.findViewById(R.id.elections1);
            TextView time = (TextView) myView.findViewById(R.id.time1);
            ImageView imageView = (ImageView) myView.findViewById(R.id.image1);
            election.setText(elections.name);
            time.setText(elections.des);
            imageView.setImageResource(elections.image);

            return myView;
        }
    }

    class DeletedAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<ElectionObject> ListOfElections = new ArrayList();

        DeletedAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final ElectionObject item) {
            ListOfElections.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return ListOfElections.size();
        }
        public void deleteAll() {
            ListOfElections=new ArrayList<>();
        }
        @Override
        public Object getItem(int i) {
            return ListOfElections.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (i);
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ElectionObject elections = ListOfElections.get(i);
            View myView = userInflater.inflate(R.layout.deleted_items, null);
            TextView election = (TextView) myView.findViewById(R.id.elections1);
            TextView reason = (TextView) myView.findViewById(R.id.reason1);
            ImageView imageView = (ImageView) myView.findViewById(R.id.image1);
            ImageButton image=(ImageButton)myView.findViewById(R.id.helpbutton);
            election.setText(elections.name);
            reason.setText("Reason: "+reasonsDeleted.get(i));
            imageView.setImageResource(elections.image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editintent=new Intent(MyPublishedElections.this,EditDeleted.class);
                    editintent.putExtra("electionid", ListOfElections.get(i).id.toString());
                    editintent.putExtra("category", choesedeleted.get(i).toString());
                    startActivity(editintent);
                }
            });

            return myView;
        }
    }

    class RejectedAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<ElectionObject> ListOfElections = new ArrayList();
       // ArrayList<String> reasonAdapter = new ArrayList();

        RejectedAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final ElectionObject item) {
            ListOfElections.add(item);
            notifyDataSetChanged();
        }
        /*   public void addReason(final String item) {
            reasonAdapter.add(item);
            notifyDataSetChanged();
        }*/
        @Override
        public int getCount() {
            return ListOfElections.size();
        }
        public void deleteAll() {
            ListOfElections=new ArrayList<>();
        }
        @Override
        public Object getItem(int i) {
            return ListOfElections.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (i);
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ElectionObject elections = ListOfElections.get(i);
            View myView = userInflater.inflate(R.layout.rejected_items, null);
            TextView election = (TextView) myView.findViewById(R.id.elections1);
            ImageView imageView = (ImageView) myView.findViewById(R.id.image1);
            TextView reason = (TextView) myView.findViewById(R.id.reason);
            ImageButton imagebutton=(ImageButton) myView.findViewById(R.id.editbutton);
            election.setText(elections.name);
            imageView.setImageResource(elections.image);
            reason.setText("Reason: "+reasons.get(i));
            imagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent editintent=new Intent(MyPublishedElections.this,EditElection.class);
                    editintent.putExtra("electionid", ListOfElections.get(i).id.toString());
                    editintent.putExtra("category", chosenrejected.get(i).toString());
                    startActivity(editintent);
                }
            });

            return myView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(MyPublishedElections.this, ProfileMenu.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
