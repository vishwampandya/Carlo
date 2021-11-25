package com.swich.carlo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class OwnerDash extends AppCompatActivity {
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;
    CardView profile;
    CardView addcar;
    CardView feedback;
    CardView history;
    CardView chat;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences("MyPref", 0);
                preferences.edit().remove("user").commit();
                Intent i=new Intent(this,login.class);
                startActivity(i);
                finish();

                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dash);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String user=  pref.getString("user", null); // getting String

        if(user==null){
            Intent i=new Intent(OwnerDash.this,login.class);
            startActivity(i);
            finish();
            return;
        }
        profile=(CardView) findViewById(R.id.profile);
        feedback=(CardView) findViewById(R.id.feedback);
        addcar=(CardView) findViewById(R.id.addcar);
        history=(CardView) findViewById(R.id.history);
        chat=(CardView) findViewById(R.id.chatlist);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OwnerDash.this,OwnerHis.class);
                startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(OwnerDash.this,chatlist.class);
               startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OwnerDash.this,Profile.class);
                startActivity(i);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OwnerDash.this,Feedback.class);
                startActivity(i);
            }
        });

       addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(OwnerDash.this,Addcar.class);
                startActivity(i);
            }
        });

    }





}








































