package voted.tnc.ivoted;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Election extends AppCompatActivity {//Election View
    public String selection;
    String id;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;
    TextView textView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Elections");
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);
        setContentView(R.layout.activity_election);
        selection();
        ListView listView = (ListView) findViewById(R.id.listView);
        textView=(TextView) findViewById(R.id.selectiontext);
        radio1 = (RadioButton) findViewById(R.id.radiobutton1);
        radio2 = (RadioButton) findViewById(R.id.radiobutton2);
        radio3 = (RadioButton) findViewById(R.id.radiobutton3);
        Button button = (Button) findViewById(R.id.vote);

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
    }
    public void selection(){
        if (getIntent().getExtras() != null) {
            // selection = (getIntent().getExtras().getString("selection"));
            id = (getIntent().getExtras().getString("electionid"));
            //just test for selection post   Toast.makeText(Election.this, "Selection " + selection.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
