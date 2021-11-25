package com.swich.carlo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OwnerDetails extends AppCompatActivity {
    EditText name, email, phone, upi, add;
    RadioGroup rg;
    RadioButton m, f;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        upi = (EditText) findViewById(R.id.upi);
        add = (EditText) findViewById(R.id.add);
        rg = (RadioGroup) findViewById(R.id.rg);
        m = (RadioButton) findViewById(R.id.male);
        f = (RadioButton) findViewById(R.id.female);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            response = null;
        } else {
            response = extras.getString("data");

        }

        try {

            JSONObject obj = new JSONObject(response);

            phone.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText()));
                    if (ActivityCompat.checkSelfPermission(OwnerDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                10);

                        return;
                    }
                    startActivity(intent);

                }
            });

            name.setText(obj.getString("Name"));
            add.setText(obj.getString("address"));
            phone.setText(obj.getString("ph"));
            email.setText(obj.getString("email"));
            upi.setText(obj.getString("UPIID"));
            String gen=obj.getString("gender");
            if(gen.equals("Male")) {

                m.setChecked(true);
            }else {
                f.setChecked(true);
            }






        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone.getText()));
            startActivity(intent);


        } else {

                Toast.makeText(getApplicationContext(),"unable to make call denied permission",Toast.LENGTH_LONG).show();
        }
        return;


        // other 'switch' lines to check for other
        // permissions this app might request

    }

}
