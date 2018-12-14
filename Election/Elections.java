package voted.tnc.ivoted;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    FirebaseAuth mAuth;
    ArrayList<ElectionObject> listOfElections = new ArrayList<>();
    ElectionsAdapter adapter;
    public String Selection;
    String id,u;
    Toolbar toolbarelections;
    EditText ürünara;
    ListView listView;
    TextView thereisno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elections);
        ürünara=(EditText)findViewById(R.id.ürünara);
        mAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.listView);
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
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();

                    if (category.equals("Sport")) {

                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String name = ds1.child("ElectionName").getValue(String.class);
                            String date = ds1.child("DateType").getValue(String.class);
                            String type = ds1.child("Type").getValue(String.class);

                            if(type!=null){
                              if (type.equals("Admin Election")) {
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
                 if(listOfElections.isEmpty()){
                    thereisno.setVisibility(View.VISIBLE);
                }else{
                    thereisno.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ürünara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    initList();
                }else{
                    searchItem(charSequence.toString());
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

    }

    public void selector() {
        Intent intent = new Intent(this, Election.class);
        // intent.putExtra("selection",Selection.toString());
        intent.putExtra("electionid", id.toString());
        startActivity(intent);

    }

    class ElectionsAdapter extends BaseAdapter {
        LayoutInflater userInflater;
        ArrayList<ElectionObject> ListOfElections = new ArrayList();

        ElectionsAdapter(Activity activity) {
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
                Intent intent = new Intent(Elections.this, Main4Activity.class);
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

                    if (category.equals("Sport")) {

                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String name = ds1.child("ElectionName").getValue(String.class);
                            String date = ds1.child("DateType").getValue(String.class);
                            String type = ds1.child("Type").getValue(String.class);
                            if(name.toLowerCase().contains(u.toLowerCase()) || name.toUpperCase().contains(u.toUpperCase())){
                                if (type.equals("Admin Election")) {
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

                    if (category.equals("Sport")) {

                        for (DataSnapshot ds1 : ds.getChildren()) {
                            id = ds1.getKey();
                            String name = ds1.child("ElectionName").getValue(String.class);
                            String date = ds1.child("DateType").getValue(String.class);
                            String type = ds1.child("Type").getValue(String.class);

                          if (type.equals("Admin Election")) {
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
}

