package com.company.talha.vote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

/**
 * The type My published elections.
 */
public class MyPublishedElections extends AppCompatActivity {
    /**
     * The Database.
     */
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * The My ref.
     */
    DatabaseReference myRef = database.getReference("Elections");
    /**
     * The My ref 1.
     */
    DatabaseReference myRef1 = database.getReference("Kullanıcılar");
    /**
     * The List of elections.
     */
    ArrayList<ElectionObject> listOfElections = new ArrayList<>();
    /**
     * The M auth.
     */
    FirebaseAuth mAuth;
    /**
     * The Adapter.
     */
    PublishedAdaptor adapter;
    /**
     * The List view.
     */
    ListView listView;
    /**
     * The Selection.
     */
    public String Selection;
    /**
     * The Id.
     */
    String id, /**
     * The Kullanici election ıd.
     */
    kullaniciElectionId;
    /**
     * The L.
     */
    boolean l=false;
    /**
     * The Name.
     */
    String name, /**
     * The Date.
     */
    date, /**
     * The Type.
     */
    type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_published_elections);
        listView = (ListView) findViewById(R.id.published);
        mAuth = FirebaseAuth.getInstance();
        adapter=new PublishedAdaptor(this);
        listView.setAdapter(adapter);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals("Sport")) {

                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                             name = ds1.child("ElectionName").getValue(String.class);
                             date = ds1.child("DateType").getValue(String.class);
                              type = ds1.child("Type").getValue(String.class);
                            myRef1.child(mAuth.getCurrentUser().getUid()).child("Elections").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds33 : dataSnapshot.getChildren()) {
                                                kullaniciElectionId=ds33.getKey();
                                                System.out.println("id :"+ id+"KullanıcıElectionId:"+kullaniciElectionId);

                                                if(id.equals(kullaniciElectionId)){
                                                    l=true;
                                                }
                                                if(l){
                                                    if(type!=null){
                                                        if (type.equals("User Type")) {
                                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
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
                    }

                }

                // listOfElections.add(new Elections("Fatma vs Ayşegül","Time remain : 11 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Selma vs Selen","Time remain : 10 days 3 hour",R.drawable.user1));
                // listOfElections.add(new Elections("Derya vs Fatmagül","Time remain : 10 days 3 hour",R.drawable.adnmin));
                // listOfElections.add(new Elections("Seher vs Ayşenur","Time remain : 9 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Elif vs Deniz","Time remain : 5 days 3 hour",R.drawable.adnmin));
                //listOfElections.add(new Elections("Esra vs Esmanur","Time remain : 4 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Ece vs Ceyda","Time remain : 3 days 3 hour",R.drawable.user1));
                //listOfElections.add(new Elections("Elif vs Naz","Time remain : 2 days 3 hour",R.drawable.adnmin));
                //listOfElections.add(new Elections("Esra vs Esma","Time remain : 3 days 3 hour",R.drawable.adnmin));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Test ok  Toast.makeText(vote.this,listOfElections.get(i).name, Toast.LENGTH_SHORT).show();
                Selection = listOfElections.get(i).name;
                id = listOfElections.get(i).id.toString();
                selector();
            }
        });

    }

    /**
     * Selector.
     */
    public void selector() {
        Intent intent = new Intent(this, ViewMyPublishedElections.class);
        // intent.putExtra("selection",Selection.toString());
        intent.putExtra("electionId", id.toString());
        startActivity(intent);

    }

    /**
     * The type Published adaptor.
     */
    class PublishedAdaptor extends BaseAdapter {
        /**
         * The User ınflater.
         */
        LayoutInflater userInflater;
        /**
         * The List of elections.
         */
        ArrayList<ElectionObject> ListOfElections = new ArrayList();

        /**
         * Instantiates a new Published adaptor.
         *
         * @param activity the activity
         */
        PublishedAdaptor(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        /**
         * Add ıtem.
         *
         * @param item the item
         */
        public void addItem(final ElectionObject item) {
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
}
