package com.company.talha.vote;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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

/**
 * The type Viewmyvotings.
 */
public class viewmyvotings extends AppCompatActivity {
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
     * The M auth.
     */
    FirebaseAuth mAuth;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private List<ViewMyVote> listDataHeader;
    private HashMap<ViewMyVote, List> listHash;
    /**
     * The Publisher.
     */
    ImageView publisher;
    /**
     * The Status.
     */
    ImageView status;
    /**
     * The Selection.
     */
    public String Selection;
    /**
     * The Id.
     */
    String id;
    /**
     * The Voteid.
     */
    ArrayList<String> voteid;
    /**
     * The Voteidchoice.
     */
    ArrayList<String> voteidchoice;
    /**
     * The Electioname.
     */
    public String electioname;
    /**
     * The Option 1.
     */
    public String option1;
    /**
     * The Option 2.
     */
    public String option2;
    /**
     * The Option 3.
     */
    public String option3;
    /**
     * The Publisherdata.
     */
    public String publisherdata;
    /**
     * The Statusdata.
     */
    public String statusdata;
    /**
     * The Name.
     */
    String name;
    /**
     * The Date.
     */
    String date;
    /**
     * The Type.
     */
    String type;
    /**
     * The Kullanici vote ıd.
     */
    String kullaniciVoteId;

    /**
     * The L.
     */
    boolean l=false;

    /**
     * The K.
     */
    int k;

    /**
     * The Viewvotecount.
     */
    int viewvotecount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmyvotings);
        voteid=new ArrayList<>();
        voteidchoice=new ArrayList<>();
        mAuth =FirebaseAuth.getInstance();
        k=this.getResources().getColor(R.color.colorAccent);
        expandableListView = (ExpandableListView) findViewById(R.id.viewvote);
        expandableListView.setGroupIndicator(null);
        expandableListView.setDividerHeight(0);
        Button continuedelection =(Button) findViewById(R.id.continuedelections);
        Button comleteelection =(Button)findViewById(R.id.completeelections);

        //data headerin alt başlıklarını yüklemek için
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
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
                  /*  if(id.equals(kullaniciVoteId)){
                        l=true;
                    }
                    if(l){
                        String choice=ds33.child("SelectedOption").getValue(String.class);
                        if(type!=null){
                            if (type.equals("Admin Type")) {
                                listAdapter.addItem(new ViewMyVote(R.drawable.adnmin,name, option1,option2,option3,choice,R.drawable.proses));
                                List election = new ArrayList();
                                election.add(option1);
                                election.add(option2);
                                election.add(option3);
                                listHash.put(listDataHeader.get(viewvotecount),election);
                                viewvotecount++;
                            }else{
                                listAdapter.addItem(new ViewMyVote(R.drawable.user1,name, option1,option2,option3,choice,R.drawable.proses));
                                List election = new ArrayList();
                                election.add(option1);
                                election.add(option2);
                                election.add(option3);
                                listHash.put(listDataHeader.get(viewvotecount),election);
                                viewvotecount++;
                            }
                        }
                    }*/
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

        publisher = findViewById(R.id.publisher);
        status = findViewById(R.id.status);
        continuedelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        comleteelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewmyvotings.this, "View just completed elections on the expandablelist", Toast.LENGTH_SHORT).show();
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

/**
 * The type Expandable list adapter.
 */
class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ViewMyVote> listDataHeader;
    private HashMap<ViewMyVote, List> listHashMap;


    /**
     * Instantiates a new Expandable list adapter.
     *
     * @param context        the context
     * @param listDataHeader the list data header
     * @param listHashMap    the list hash map
     */
    public ExpandableListAdapter(Context context, List listDataHeader, HashMap<ViewMyVote, List> listHashMap) {

        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    /**
     * Add ıtem.
     *
     * @param item the item
     */
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
        if(listDataHeader.get(groupPosition).getUsersChoice().equals(childText)){
            //    txtListChild.setTextColor(listDataHeader.get(groupPosition));
        }

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}
