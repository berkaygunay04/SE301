package com.company.talha.vote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Elections extends AppCompatActivity {//bütün electionları burda basıyoruz
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Elections");
    DatabaseReference myRef1 = database.getReference("Elections");
    DatabaseReference myRef5 = database.getReference("Results");
    FirebaseAuth mAuth;
    ArrayList<ElectionObject> listOfElections = new ArrayList<>();
    ElectionsAdapter adapter;
    PopularAdapter adapterPopular;
    ResultAdapter resultAdapter;
    public String Selection;
    String id,
    u;
    Button sport,
    history,
    culture,
    science,
    cinema,
    art,
    categories;
    Button popularElections,
    resultsElections;
    Toolbar toolbarelections;
    EditText ürünara;
    String choosencategory;
    String choosenCategoryPopular;
    ListView listView,
    popularlistview,
    resultListView;
    TextView thereisno;
    ArrayList<PopularElections> populars;
    ArrayList<String> popularsID;
    ArrayList<String> CategoryPopular;
    boolean k=false;
    LinearLayout butonlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elections);
        populars=new ArrayList<>();
        popularsID=new ArrayList<>();
        CategoryPopular=new ArrayList<>();
        sport=(Button)findViewById(R.id.sport);//
        history=(Button)findViewById(R.id.history);//
        culture=(Button)findViewById(R.id.culture);//
        science=(Button)findViewById(R.id.science);
        cinema=(Button)findViewById(R.id.cinema);//
        art=(Button)findViewById(R.id.art);//
        popularElections=(Button)findViewById(R.id.popularelections);
        resultsElections=(Button)findViewById(R.id.results);
        categories=(Button)findViewById(R.id.categories);
        ürünara=(EditText)findViewById(R.id.ürünara);
        butonlar=(LinearLayout)findViewById(R.id.butonlar);
        mAuth = FirebaseAuth.getInstance();
        thereisno=(TextView)findViewById(R.id.noelection);
        listView = (ListView) findViewById(R.id.listView);
        popularlistview = (ListView) findViewById(R.id.popularE);
        resultListView=(ListView)findViewById(R.id.resutsE);
        toolbarelections=(Toolbar)findViewById(R.id.toolbaelections);
        setSupportActionBar(toolbarelections);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

        } else if (mAuth != null) {

        }
        adapter=new ElectionsAdapter(this);
        listView.setAdapter(adapter);
        adapterPopular=new PopularAdapter(this);
        popularlistview.setAdapter(adapterPopular);
        resultAdapter=new ResultAdapter(this);
        resultListView.setAdapter(resultAdapter);
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.INVISIBLE);
                ürünara.setVisibility(View.INVISIBLE);
                thereisno.setVisibility(View.INVISIBLE);
                resultListView.setVisibility(View.INVISIBLE);
                butonlar.setVisibility(View.VISIBLE);
                popularlistview.setVisibility(View.INVISIBLE);
                adapter.deleteAll();

            }
        });
        myRef1.addValueEventListener(new ValueEventListener() {//popular elections
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        choosenCategoryPopular=ds.getKey();
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String name = ds1.child("ElectionName").getValue(String.class);
                            String date = ds1.child("DateType").getValue(String.class);
                            String type = ds1.child("Type").getValue(String.class);
                            Integer votec=ds1.child("VoteCount").getValue(Integer.class);
                            if(type!=null){
                                if (type.equals("Admin Type")) {
                                    PopularElections popular=new PopularElections(name,date,votec,R.drawable.adnmin);
                                    populars.add(popular);
                                    popularsID.add(id);
                                    CategoryPopular.add(choosenCategoryPopular);
                                }else{
                                    PopularElections popular=new PopularElections(name,date,votec,R.drawable.user1);
                                    populars.add(popular);
                                    popularsID.add(id);
                                    CategoryPopular.add(choosenCategoryPopular);
                                }
                            }
                        }
                     }
                     if(populars.size()>3){
                         ArrayList<PopularElections> b=sortPopulars(populars);
                         adapterPopular.addItem(b.get(0));
                         adapterPopular.addItem(b.get(1));
                         adapterPopular.addItem(b.get(2));
                     }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        popularElections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.INVISIBLE);
                ürünara.setVisibility(View.INVISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                thereisno.setVisibility(View.INVISIBLE);
                resultListView.setVisibility(View.INVISIBLE);
                popularlistview.setVisibility(View.VISIBLE);

            }
        });
        resultsElections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.INVISIBLE);
                ürünara.setVisibility(View.INVISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                thereisno.setVisibility(View.INVISIBLE);
                popularlistview.setVisibility(View.INVISIBLE);
                resultListView.setVisibility(View.VISIBLE);

            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k=false;
                ürünara.getText().clear();
                adapter.deleteAll();
                k=true;
                listView.setVisibility(View.VISIBLE);
                ürünara.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                choosencategory="Sport";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.deleteAll();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String category = ds.getKey();

                            if (category.equals("Sport")) {

                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    String name = ds1.child("ElectionName").getValue(String.class);
                                    String date = ds1.child("DateType").getValue(String.class);
                                    String type = ds1.child("Type").getValue(String.class);
                                    if(type!=null){
                                        if (type.equals("Admin Type")) {
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                                        }else{
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                        }
                                    }
                                }
                            }

                        }
                        if(adapter.ElectionadapterEmpty()){
                            thereisno.setVisibility(View.VISIBLE);
                        }else{
                            thereisno.setVisibility(View.INVISIBLE);
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
            }
        });
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k=false;
                ürünara.getText().clear();
                adapter.deleteAll();
                k=true;
                listView.setVisibility(View.VISIBLE);
                ürünara.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                choosencategory="Cinema";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.deleteAll();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String category = ds.getKey();

                            if (category.equals("Cinema")) {

                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    String name = ds1.child("ElectionName").getValue(String.class);
                                    String date = ds1.child("DateType").getValue(String.class);
                                    String type = ds1.child("Type").getValue(String.class);
                                    if(type!=null){
                                        if (type.equals("Admin Type")) {
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                                        }else{
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                        }
                                    }
                                }
                            }

                        }
                        if(adapter.ElectionadapterEmpty()){
                            thereisno.setVisibility(View.VISIBLE);
                        }else{
                            thereisno.setVisibility(View.INVISIBLE);
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
            }
        });
        culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k=false;
                ürünara.getText().clear();
                adapter.deleteAll();
                k=true;
                listView.setVisibility(View.VISIBLE);
                ürünara.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                choosencategory="Culture";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.deleteAll();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String category = ds.getKey();

                            if (category.equals("Culture")) {

                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    String name = ds1.child("ElectionName").getValue(String.class);
                                    String date = ds1.child("DateType").getValue(String.class);
                                    String type = ds1.child("Type").getValue(String.class);
                                    if(type!=null){
                                        if (type.equals("Admin Type")) {
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                                        }else{
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                        }
                                    }
                                }
                            }

                        }
                        if(adapter.ElectionadapterEmpty()){
                            thereisno.setVisibility(View.VISIBLE);
                        }else{
                            thereisno.setVisibility(View.INVISIBLE);
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
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k=false;
                ürünara.getText().clear();
                adapter.deleteAll();
                k=true;
                listView.setVisibility(View.VISIBLE);
                ürünara.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                choosencategory="History";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.deleteAll();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String category = ds.getKey();

                            if (category.equals("History")) {

                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    String name = ds1.child("ElectionName").getValue(String.class);
                                    String date = ds1.child("DateType").getValue(String.class);
                                    String type = ds1.child("Type").getValue(String.class);
                                    if(type!=null){
                                        if (type.equals("Admin Type")) {
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                                        }else{
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                        }
                                    }
                                }
                            }

                        }
                        if(adapter.ElectionadapterEmpty()){
                            thereisno.setVisibility(View.VISIBLE);
                        }else{
                            thereisno.setVisibility(View.INVISIBLE);
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
            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k=false;
                ürünara.getText().clear();
                adapter.deleteAll();
                k=true;
                listView.setVisibility(View.VISIBLE);
                ürünara.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                choosencategory="Science";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.deleteAll();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String category = ds.getKey();

                            if (category.equals("Science")) {

                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    String name = ds1.child("ElectionName").getValue(String.class);
                                    String date = ds1.child("DateType").getValue(String.class);
                                    String type = ds1.child("Type").getValue(String.class);
                                    if(type!=null){
                                        if (type.equals("Admin Type")) {
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                                        }else{
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                        }
                                    }
                                }
                            }

                        }
                        if(adapter.ElectionadapterEmpty()){
                            thereisno.setVisibility(View.VISIBLE);
                        }else{
                            thereisno.setVisibility(View.INVISIBLE);
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
            }
        });
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k=false;
                ürünara.getText().clear();
                adapter.deleteAll();
                k=true;
                listView.setVisibility(View.VISIBLE);
                ürünara.setVisibility(View.VISIBLE);
                butonlar.setVisibility(View.INVISIBLE);
                choosencategory="Art";
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        adapter.deleteAll();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String category = ds.getKey();

                            if (category.equals("Art")) {

                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    String name = ds1.child("ElectionName").getValue(String.class);
                                    String date = ds1.child("DateType").getValue(String.class);
                                    String type = ds1.child("Type").getValue(String.class);
                                    if(type!=null){
                                        if (type.equals("Admin Type")) {
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                                        }else{
                                            adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                            listOfElections.add(new ElectionObject(name, date, R.drawable.user1, id));
                                        }
                                    }
                                }
                            }

                        }
                        if(adapter.ElectionadapterEmpty()){
                            thereisno.setVisibility(View.VISIBLE);
                        }else{
                            thereisno.setVisibility(View.INVISIBLE);
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
            }
        });



    ürünara.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.toString().equals("") && k){
                initList();
            }else{
                if(k){
                    adapter.deleteAll();
                    searchItem(charSequence.toString());
                }
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

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
        popularlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Test ok  Toast.makeText(vote.this,listOfElections.get(i).name, Toast.LENGTH_SHORT).show();
                Selection = populars.get(i).getName().toString();
                id = popularsID.get(i);
                choosenCategoryPopular=CategoryPopular.get(i).toString();
                selectorPopular();
            }
        });




        myRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    for (DataSnapshot ds1 : ds.getChildren()) {
                        id = ds1.getKey();
                        String type=ds1.child("Type").getValue(String.class);
                        String name = ds1.child("ElectionName").getValue(String.class);
                        String question = ds1.child("Question").getValue(String.class);
                        String option1 = ds1.child("Option1").getValue(String.class);
                        String option2 = ds1.child("Option2").getValue(String.class);
                        String option3 = ds1.child("Option3").getValue(String.class);
                        Integer option1C=ds1.child("Option1C").getValue(Integer.class);
                        Integer option2C=ds1.child("Option2C").getValue(Integer.class);
                        Integer option3C=ds1.child("Option3C").getValue(Integer.class);
                        if(type!=null){
                            if (type.equals("Admin Type")) {
                                Results result=new Results(name,question,option1,option2,option3,option1C,option2C,option3C,R.drawable.adnmin);
                                resultAdapter.addItem(result);
                            }else{
                                Results result=new Results(name,question,option1,option2,option3,option1C,option2C,option3C,R.drawable.user1);
                                resultAdapter.addItem(result);
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

    public ArrayList<PopularElections> sortPopulars(ArrayList<PopularElections> k){
        ArrayList<PopularElections> a=new ArrayList<>();
        int max=0;
        int p=0;

        for(int i=0;i<k.size();i++) {
            if (k.get(i).getVoteCount() > max) {
                max = k.get(i).getVoteCount();
                a.add(k.get(i));
                a.set(0, k.get(i));
                p=i;
            }
        }
        k.remove(p);
        max=0;
        for(int i=0;i<k.size();i++) {
            if (k.get(i).getVoteCount() > max) {
                max = k.get(i).getVoteCount();
                a.add(k.get(i));
                a.set(1, k.get(i));
                p=i;
            }
        }
        k.remove(p);
        max=0;
        for(int i=0;i<k.size();i++) {
            if (k.get(i).getVoteCount() > max) {
                max = k.get(i).getVoteCount();
                a.add(k.get(i));
                a.set(2, k.get(i));
            }
        }
        return a;
    }

    public void selector() {
        Intent intent = new Intent(this, Election.class);
        // intent.putExtra("selection",Selection.toString());
        intent.putExtra("electionid", id.toString());
        intent.putExtra("category", choosencategory.toString());
        startActivity(intent);

    }
    public void selectorPopular() {
        Intent intent = new Intent(this, Election.class);
        // intent.putExtra("selection",Selection.toString());
        intent.putExtra("electionid", id.toString());
        intent.putExtra("category", choosenCategoryPopular.toString());
        startActivity(intent);

    }

    class ElectionsAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<ElectionObject> ListOfElections = new ArrayList(50);

        ElectionsAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final ElectionObject item) {
            ListOfElections.add(item);
            notifyDataSetChanged();
        }

        public void deleteAll() {
            ListOfElections=new ArrayList<>();
            notifyDataSetChanged();
        }

        public boolean ElectionadapterEmpty() {
            if(ListOfElections.size()==0){
                return true;
            }else{
                return false;
            }
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
            View myView = userInflater.inflate(R.layout.items, null);
            TextView election = (TextView) myView.findViewById(R.id.elections);
            TextView time = (TextView) myView.findViewById(R.id.time);
            ImageView imageView = (ImageView) myView.findViewById(R.id.image);
            election.setText(elections.name);
            time.setText(elections.des);
            imageView.setImageResource(elections.image);

            return myView;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Elections.this, MainPage.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchItem(String textTo){
        u=textTo;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals(choosencategory)) {

                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String name = ds1.child("ElectionName").getValue(String.class);
                            String date = ds1.child("DateType").getValue(String.class);
                            String type = ds1.child("Type").getValue(String.class);
                            if(name.toLowerCase().contains(u.toLowerCase()) || name.toUpperCase().contains(u.toUpperCase())){
                                if (type.equals("Admin Type")) {
                                    adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                }else{
                                    adapter.addItem(new ElectionObject(name, date, R.drawable.user1, id));
                                }
                            }
                        }

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new ElectionsAdapter(this);
        listView.setAdapter(adapter);

    }


    public void initList(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals(choosencategory)) {

                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String name = ds1.child("ElectionName").getValue(String.class);
                            String date = ds1.child("DateType").getValue(String.class);
                            String type = ds1.child("Type").getValue(String.class);

                            if (type.equals("Admin Type")) {
                                adapter.addItem(new ElectionObject(name, date, R.drawable.adnmin, id));
                                listOfElections.add(new ElectionObject(name, date, R.drawable.adnmin, id));
                            }else{
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
        adapter = new ElectionsAdapter(this);
        listView.setAdapter(adapter);
    }

    class PopularAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<PopularElections> ListOfPopulars = new ArrayList();

        PopularAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final PopularElections item) {
            ListOfPopulars.add(item);
            notifyDataSetChanged();
        }

        public void deleteAll() {
            ListOfPopulars=new ArrayList<>();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return ListOfPopulars.size();
        }

        @Override
        public Object getItem(int i) {
            return ListOfPopulars.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            PopularElections Popular = ListOfPopulars.get(i);
            View myView = userInflater.inflate(R.layout.popular_items, null);
            TextView election = (TextView) myView.findViewById(R.id.electionsp);
            TextView time = (TextView) myView.findViewById(R.id.timep);
            TextView votecount = (TextView) myView.findViewById(R.id.votecount);
            ImageView imageView = (ImageView) myView.findViewById(R.id.imagep);

            election.setText(Popular.getName());
            votecount.setText("Vote Count: "+Popular.getVoteCount());
            time.setText(Popular.getDate());
            imageView.setImageResource(Popular.getImage());


            return myView;
        }
    }

    class ResultAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<Results> ListOfPopulars = new ArrayList();

        ResultAdapter(Activity activity) {
            userInflater = (LayoutInflater) activity.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final Results item) {
            ListOfPopulars.add(item);
            notifyDataSetChanged();
        }

        public void deleteAll() {
            ListOfPopulars=new ArrayList<>();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return ListOfPopulars.size();
        }

        @Override
        public Object getItem(int i) {
            return ListOfPopulars.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Results Results = ListOfPopulars.get(i);
            View myView = userInflater.inflate(R.layout.result_items, null);
            TextView name = (TextView) myView.findViewById(R.id.electioname);
            TextView question = (TextView) myView.findViewById(R.id.question);
            TextView option1 = (TextView) myView.findViewById(R.id.option1);
            TextView option2 = (TextView) myView.findViewById(R.id.option2);
            TextView option3 = (TextView) myView.findViewById(R.id.option3);
            ImageView imageView = (ImageView) myView.findViewById(R.id.imagep);
            double p1=Results.getOption1C();
            double p2=Results.getOption2C();
            double p3=Results.getOption3C();
            double a1=(p1/(p1+p2+p3))*100;
            double a2=(p2/(p1+p2+p3))*100;
            double a3=(p3/(p1+p2+p3))*100;

           String b1=String.valueOf(a1);
            if(b1.length()>3){
                b1=b1.substring(0,4);
            }
            String b2=String.valueOf(a2);
            if(b2.length()>3){
               b2=b2.substring(0,4);
            }
            String b3=String.valueOf(a3);
            if(b3.length()>3){
                  b3=b3.substring(0,4);
            }


            name.setText(Results.getName());
            question.setText(Results.getQuestion());
            option1.setText(Results.getOption1() + "  % " + b1);
            option2.setText(Results.getOption2() + "  % " + b2);
            option3.setText(Results.getOption3() + "  % " + b3);
            imageView.setImageResource(Results.getImage());

            return myView;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Elections.this, MainPage.class);
        startActivity(intent);
        finish();
    }
}
