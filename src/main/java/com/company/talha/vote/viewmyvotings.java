package com.company.talha.vote;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
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
import java.util.HashMap;
import java.util.List;

public class viewmyvotings extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Elections");
    DatabaseReference myRef3 = database.getReference("Results");
    DatabaseReference myRef1 = database.getReference("Kullanıcılar");
    FirebaseAuth mAuth;

    private ExpandableListView expandableListView,viewcontinuedListView;
    private ExpandableListAdapter listAdapter;
    private ExpandableListAdapter continuedAdapter;
    private List<ViewMyVote> listDataHeader,listDataHeader1;
    private HashMap<ViewMyVote, List> listHash,listHash1;
    ImageView publisher;
    ImageView status;
    public String Selection;
    String id;
    ArrayList<String> voteid;
    ArrayList<String> voteidchoice;
    public String electioname;
    public String option1;
    public String option2;
    public String option3;
    public String publisherdata;
    public String statusdata;
    String name;
    String date;
    String type;
    String kullaniciVoteId;
    Toolbar toolbar3;
    boolean create=false;
    boolean l=false;
    int k;

    int viewvotecount=0;
    int viewvotecount1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmyvotings);
        create=true;
        voteid=new ArrayList<>();
        voteidchoice=new ArrayList<>();
        mAuth =FirebaseAuth.getInstance();
        k=this.getResources().getColor(R.color.colorAccent);
        expandableListView = (ExpandableListView) findViewById(R.id.viewvote);
        expandableListView.setGroupIndicator(null);
        expandableListView.setDividerHeight(0);
        viewcontinuedListView = (ExpandableListView) findViewById(R.id.viewcontinuedvote);
        viewcontinuedListView.setGroupIndicator(null);
        viewcontinuedListView.setDividerHeight(0);
        Button continuedelection =(Button) findViewById(R.id.continuedelections);
        Button comleteelection =(Button)findViewById(R.id.completeelections);
        toolbar3=(Toolbar)findViewById(R.id.toolbaelections);
        setSupportActionBar(toolbar3);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //data headerin alt başlıklarını yüklemek için
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listDataHeader1 = new ArrayList<>();
        listHash1 = new HashMap<>();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        continuedAdapter = new ExpandableListAdapter(this, listDataHeader1, listHash1);//biten
        viewcontinuedListView.setAdapter(continuedAdapter);
        expandableListView.setAdapter(listAdapter);
        myRef1.child(mAuth.getCurrentUser().getUid()).child("Votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds33 : dataSnapshot.getChildren()) {
                    kullaniciVoteId=ds33.getKey();
                    System.out.println("id :"+ id+"KullanıcıElectionId:"+kullaniciVoteId);
                    voteid.add(kullaniciVoteId);
                    String choice=ds33.child("SelectedOption").getValue(String.class);
                    voteidchoice.add(choice);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        id = ds1.getKey();
                        name = ds1.child("ElectionName").getValue(String.class);
                        date = ds1.child("DateType").getValue(String.class);
                        type = ds1.child("Type").getValue(String.class);
                        option1=ds1.child("Option1").getValue(String.class);
                        option2=ds1.child("Option2").getValue(String.class);
                        option3=ds1.child("Option3").getValue(String.class);
                        for(int i=0;i<voteid.size();i++){
                          if(id.equals(voteid.get(i))){
                        l=true;
                    }
                    if(l){

                        if(type!=null){
                            if (type.equals("Admin Type")) {
                                listAdapter.addItem(new ViewMyVote(R.drawable.adnmin,name, option1,option2,option3,voteidchoice.get(i),R.drawable.proses));
                                List election = new ArrayList();
                                election.add(option1);
                                election.add(option2);
                                election.add(option3);
                                listHash.put(listDataHeader.get(viewvotecount),election);
                                viewvotecount++;
                                l=false;
                            }else{
                                listAdapter.addItem(new ViewMyVote(R.drawable.user1,name, option1,option2,option3,voteidchoice.get(i),R.drawable.proses));
                                List election = new ArrayList();
                                election.add(option1);
                                election.add(option2);
                                election.add(option3);
                                listHash.put(listDataHeader.get(viewvotecount),election);
                                viewvotecount++;
                                l=false;
                            }
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



        publisher = findViewById(R.id.publisher);
        status = findViewById(R.id.status);
        continuedelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//continued elections tıklandı
                expandableListView.setVisibility(View.VISIBLE);
                viewcontinuedListView.setVisibility(View.INVISIBLE);
            }
        });

        comleteelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableListView.setVisibility(View.INVISIBLE);
                viewcontinuedListView.setVisibility(View.VISIBLE);
               //completed election bitti{
                if(create){
                    myRef3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    id = ds1.getKey();
                                    name = ds1.child("ElectionName").getValue(String.class);
                                    date = ds1.child("DateType").getValue(String.class);
                                    type = ds1.child("Type").getValue(String.class);
                                    option1=ds1.child("Option1").getValue(String.class);
                                    option2=ds1.child("Option2").getValue(String.class);
                                    option3=ds1.child("Option3").getValue(String.class);
                                    for(int i=0;i<voteid.size();i++){
                                        if(id.equals(voteid.get(i))){
                                            l=true;
                                        }
                                        if(l){

                                            if(type!=null){
                                                if (type.equals("Admin Type")) {
                                                    continuedAdapter.addItem(new ViewMyVote(R.drawable.adnmin,name, option1,option2,option3,voteidchoice.get(i),R.drawable.proses));
                                                    List election1 = new ArrayList();
                                                    election1.add(option1);
                                                    election1.add(option2);
                                                    election1.add(option3);
                                                    listHash1.put(listDataHeader1.get(viewvotecount1),election1);
                                                    viewvotecount1++;
                                                    l=false;
                                                }else{
                                                    continuedAdapter.addItem(new ViewMyVote(R.drawable.user1,name, option1,option2,option3,voteidchoice.get(i),R.drawable.proses));
                                                    List election1 = new ArrayList();
                                                    election1.add(option1);
                                                    election1.add(option2);
                                                    election1.add(option3);
                                                    listHash1.put(listDataHeader1.get(viewvotecount1),election1);
                                                    viewvotecount1++;
                                                    l=false;
                                                }
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
                    create=false;
                }


            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(viewmyvotings.this, ProfileMenu.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }







/*
    public void initData() {
        //Dataheader ve child yüklemek için
        ViewMyVote a;
        String c="Bitti";
        if(publisherdata.equals("Admin Election")){
            if(c.equals("Devam Ediyor")){
                a =new ViewMyVote(R.drawable.adnmin,electioname,option1,option2,option3,option2,R.drawable.proses);
            }else{
                a =new ViewMyVote(R.drawable.adnmin,electioname,option1,option2,option3,option2,R.drawable.endelection);
            }
        }else{
            if(c.equals("Devam Ediyor")){
                a =new ViewMyVote(R.drawable.user1,electioname,option1,option2,option3,option2,R.drawable.proses);
            }else{
                a=new ViewMyVote(R.drawable.user1,electioname,option1,option2,option3,option2,R.drawable.endelection);
            }

        }
        listDataHeader.add(a);

    }
*/
}

class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ViewMyVote> listDataHeader;
    private HashMap<ViewMyVote, List> listHashMap;


    public ExpandableListAdapter(Context context, List listDataHeader, HashMap<ViewMyVote, List> listHashMap) {

        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    public void addItem(final ViewMyVote item){
        listDataHeader.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {

        return listDataHeader.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {

        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = listDataHeader.get(groupPosition).getName();
        int headerImage = (int)listDataHeader.get(groupPosition).getTur();
        int headerImage2 = (int)listDataHeader.get(groupPosition).getStatus();
        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_vote_listgroup, null);
        }
        ImageView publisher =convertView.findViewById(R.id.publisher);
        ImageView status = convertView.findViewById(R.id.status);
        TextView lblListHeader = convertView.findViewById(R.id.viewvotemain);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        publisher.setImageResource(headerImage);
        publisher.setImageResource(headerImage);
        status.setImageResource(headerImage2);
        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_vote_listitem, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.viewvotesub);
        txtListChild.setText(childText);
        if(txtListChild.getText().toString().equals(listDataHeader.get(groupPosition).getUsersChoice())){
            txtListChild.setText(childText+"  (My Vote)");
        }
      /*  if(listDataHeader.get(groupPosition).getUsersChoice().equals(childText)){
            //    txtListChild.setTextColor(listDataHeader.get(groupPosition));
        }*/

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

}
