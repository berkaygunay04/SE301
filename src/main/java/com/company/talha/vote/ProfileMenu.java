package com.company.talha.vote;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * The type Profile menu.
 */
public class ProfileMenu extends ListActivity {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    /**
     * The Models.
     */
    ArrayList<Model> models;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if extending Activity
        //setContentView(R.layout.activity_main);

        // 1. pass context and data to the custom adapter
        MyAdapter adapter = new MyAdapter(this, generateData());

        // if extending Activity 2. Get ListView from activity_main.xml
        //ListView listView = (ListView) findViewById(R.id.listview);

        // 3. setListAdapter
        //listView.setAdapter(adapter); if extending Activity
        setListAdapter(adapter);
    }

    private ArrayList<Model> generateData(){
        models = new ArrayList<Model>();
        models.add(new Model("Profile Settings"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Profile Settings","1"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"My Votes","2"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Applied Elections","12"));
        models.add(new Model("About"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Web","12"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"User Agreement","12"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Contact","12"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Developers","12"));
        models.add(new Model(R.drawable.ic_done_white_48dp,"                     Log Out","12"));
        return models;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(models.get(position).getTitle().equals("                     Log Out")){

        }else{
            Toast.makeText(this, "Seçtiniz: "+models.get(position).getTitle()+ " " , Toast.LENGTH_LONG).show();
        }

        if(models.get(position).getTitle()=="                     Log Out"){
            AlertDialog.Builder builder1=new AlertDialog.Builder(ProfileMenu.this);
            builder1.setTitle("Log Out!");
            builder1.setMessage("Çıkış yapmak istediğinize emin misiniz?");
            builder1.setCancelable(false);
            builder1.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mAuth.signOut();
                    Intent intent = new Intent(ProfileMenu.this, MainPage.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            builder1.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder1.show();

        }
        if(models.get(position).getTitle()=="Applied Elections"){
            Intent intent = new Intent(ProfileMenu.this, MyPublishedElections.class);//Geliştiriciler
            startActivity(intent);
        }
        if(models.get(position).getTitle()=="My Votes"){
            Intent intent = new Intent(ProfileMenu.this, viewmyvotings.class);//Geliştiriciler
            startActivity(intent);
        }
        if(models.get(position).getTitle()=="Profile Settings"){
            Intent intent = new Intent(ProfileMenu.this, viewprofile.class);//Geliştiriciler
            startActivity(intent);
        }
        if(models.get(position).getTitle()=="User Agreement"){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://nefisss.net/sozlesme.html"));
            startActivity(intent);
        }
        if(models.get(position).getTitle()=="Developers"){
            Intent intent = new Intent(ProfileMenu.this, Developers.class);//Geliştiriciler
            startActivity(intent);
        }
        if(models.get(position).getTitle()=="Contact"){
            Intent intent = new Intent(ProfileMenu.this, Contact.class);//İletişim
            startActivity(intent);
        }
        //Object o = this.getListAdapter().getItem(position);
        //String pen = o.toString();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileMenu.this, MainPage.class);
        startActivity(intent);
        this.finish();
    }
}
