package com.company.talha.vote;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Main5Activity extends ListActivity {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
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
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Profil Bilgileri","1"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"My Votes","2"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Applied Elections","12"));
        models.add(new Model("About"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Web","12"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Kullanıcı Sözleşmesi","12"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"İletişim","12"));
        models.add(new Model(R.drawable.ic_menu_black_24dp,"Geliştiriciler","12"));
        models.add(new Model(R.drawable.ic_done_white_48dp,"                     Çıkış Yap","12"));
        return models;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(models.get(position).getTitle().equals("                     Çıkış Yap")){

        }else{
            Toast.makeText(this, "Seçtiniz: "+models.get(position).getTitle()+ " " , Toast.LENGTH_LONG).show();
        }

        if(models.get(position).getTitle()=="                     Çıkış Yap"){
            AlertDialog.Builder builder1=new AlertDialog.Builder(Main5Activity.this);
            builder1.setTitle("Çıkış!");
            builder1.setMessage("Çıkış yapmak istediğinize emin misiniz?");
            builder1.setCancelable(false);
            builder1.setPositiveButton("Çıkış Yap", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mAuth.signOut();
                    Intent intent = new Intent(Main5Activity.this, Main4Activity.class);
                    startActivity(intent);
                }
            });
            builder1.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder1.show();

        }
        //Object o = this.getListAdapter().getItem(position);
        //String pen = o.toString();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main5Activity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }
}
