package com.company.talha.vote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * The type Contact.
 */
public class Contact extends AppCompatActivity {
    /**
     * The Text view 76.
     */
    TextView textView76;
    /**
     * The Toolbar 76.
     */
    Toolbar toolbar76;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        textView76=(TextView)findViewById(R.id.burgertel);
        String link1 = "<a href=\"tel:5330223497\">(0533) 022 34 97</a>";
        String message = link1;
        Spanned myMessage = Html.fromHtml(message);
        textView76.setText(myMessage);
        textView76.setMovementMethod(LinkMovementMethod.getInstance());
        toolbar76=(Toolbar)findViewById(R.id.toolba76);
        setSupportActionBar(toolbar76);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Contact.this, ProfileMenu.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
