package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

EditText name,email,phone,upi,add;
Button edit,save;
RadioGroup rg;
RadioButton m,f;
String id;
JSONObject obj;
AwesomeValidation valid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        valid=new AwesomeValidation(ValidationStyle.BASIC);
        email=(EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
       upi=(EditText) findViewById(R.id.upi);
        add=(EditText) findViewById(R.id.add);
        save=(Button)findViewById(R.id.save);
        edit=(Button)findViewById(R.id.edit);
        rg=(RadioGroup)findViewById(R.id.rg);
        m=(RadioButton)findViewById(R.id.male);
        f=(RadioButton)findViewById(R.id.female);
        name= (EditText) findViewById(R.id.name);
        valid.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invaidEmail);
        valid.addValidation(this,R.id.phone, "[0-9]{10}",R.string.invalidphone);
        valid.addValidation(this,R.id.upi, "[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}",R.string.invalidupi);
        valid.addValidation(this,R.id.add, ".{6,}",R.string.invalidadd);
        valid.addValidation(this,R.id.name, "[a-zA-Z\\s]+",R.string.validname);



        final SharedPreferences pref ; // 0 - for private mode
        final SharedPreferences.Editor editor;

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        String user=  pref.getString("user", null); // getting String
           try {

             obj=new JSONObject(user);
            name.setText(obj.getString("Name"));
            add.setText(obj.getString("address"));
            phone.setText(obj.getString("ph"));
            email.setText(obj.getString("email"));
            upi.setText(obj.getString("UPIID"));
            String gen=obj.getString("gender");
         id=obj.getString("regID");
            if(gen.equals("Male")) {

               m.setChecked(true);
            }else {
                f.setChecked(true);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                add.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
                upi.setEnabled(true);
                m.setEnabled(true);
                f.setEnabled(true);
                save.setEnabled(true);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid.validate()) {
                    final String na = String.valueOf(name.getText());
                    final String ad = String.valueOf(add.getText());
                    final String em = String.valueOf(email.getText());
                    final String ph = String.valueOf(phone.getText());
                    final String u = String.valueOf(upi.getText());

                    String gen = null;
                    if (rg.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    m = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                    gen = String.valueOf(m.getText());


                    //


                    String URLline = getString(R.string.host) + "updateUser.php";


                    final String finalGen = gen;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (!response.equals("404")) {

                                        editor.putString("user", response);
                                        editor.commit();
                                        Intent i = null;
                                        try {
                                            if (obj.getString("role").equals("user"))
                                                i = new Intent(Profile.this, Dashboard.class);
                                            else
                                                i = new Intent(Profile.this, OwnerDash.class);
                                        } catch (JSONException e) {

                                        }
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Some thing went wrong", Toast.LENGTH_LONG);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //displaying the error in toast if occurrs

                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", na);
                            params.put("email", em);
                            params.put("phone", ph);
                            params.put("add", ad);
                            params.put("gender", finalGen);
                            params.put("upi", u);
                            params.put("regID", id);
                            return params;
                        }
                    };

                    // request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);

                    requestQueue.add(stringRequest);

                    //


                }
            }
        });

    }
}
