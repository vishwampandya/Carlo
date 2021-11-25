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

public class Dashboard extends AppCompatActivity {
CardView prof;
CardView book;
CardView feed;
CardView his;
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;

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

        setContentView(R.layout.activity_dashboard);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String user=  pref.getString("user", null); // getting String

        if(user==null){
            Intent i=new Intent(Dashboard.this,login.class);
            startActivity(i);
            finish();
            return;
        }


        prof=(CardView) findViewById(R.id.profile);
        feed=(CardView) findViewById(R.id.feedback);
        book=(CardView) findViewById(R.id.book);
        his=(CardView) findViewById(R.id.history);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,Profile.class);
                startActivity(i);
            }
        });

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,Feedback.class);
                startActivity(i);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,Listbook.class);
                startActivity(i);
            }
        });
        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Dashboard.this,History.class);
                startActivity(i);
            }
        });



    }


}
