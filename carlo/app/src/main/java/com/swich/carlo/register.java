package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class register extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText address;
    EditText phone;
    EditText pass;
    EditText cpass;
    EditText upi;
    Button btn;
    RadioGroup rg;
    RadioButton rb;
    TextView error;
    TextView head;
    String newString;
    AwesomeValidation valid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        valid=new AwesomeValidation(ValidationStyle.BASIC);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("role");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("role");
        }
         head=(TextView)findViewById(R.id.head);
        head.setText("Signup:"+newString);



        rg=(RadioGroup)findViewById(R.id.rg);

        btn=(Button) findViewById(R.id.btn);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.add);
        phone=(EditText)findViewById(R.id.mobile);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.cpass);
        upi=(EditText)findViewById(R.id.UPPID) ;
        error=(TextView)findViewById(R.id.error);
        valid.addValidation(this,R.id.name,"[a-zA-Z\\s]+",R.string.validname);
        valid.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invaidEmail);
        valid.addValidation(this,R.id.add,".{6,}",R.string.invalidadd);
        valid.addValidation(this,R.id.pass,".{6,}",R.string.invalidPass);
        valid.addValidation(this,R.id.mobile,"[0-9]{10}",R.string.invalidphone);
        valid.addValidation(this,R.id.UPPID,"[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}",R.string.invalidupi);

        valid.addValidation(this,R.id.pass,R.id.cpass,R.string.cpass);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (valid.validate()) {

                    if (rg.getCheckedRadioButtonId() == -1) {
                        error.setText("Please select your gender");
                        error.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                    }

                    String na = String.valueOf(name.getText());
                    String em = String.valueOf(email.getText());
                    String add = String.valueOf(address.getText());
                    String ph = String.valueOf(phone.getText());
                    String p = String.valueOf(pass.getText());
                    String cp = String.valueOf(cpass.getText());
                    String gen = (String) rb.getText();
                    String upiid = String.valueOf(upi.getText());

                    if (!p.equals(cp)) {
                        error.setText("Please enter same value in password and confrim password");
                        error.setVisibility(View.VISIBLE);
                        return;
                    }


                    connect(na, em, ph, add, p, gen, newString, upiid);

                }
            }
        });



    }

  public void connect(final String name, final String email, final String phone, final String address, final String password, final String gender, final String role,final String upi){

     String URLline = getString(R.string.host)+"insert.php";


      StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      if(response.equals("200")){
                          Intent i=new Intent(register.this,login.class);
                          startActivity(i);
                          finish();
                      }
                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      //displaying the error in toast if occurrs

                      Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                  }
              }){

          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
             Map<String,String> params=new HashMap<String,String>();
              params.put("name",name);
              params.put("email",email);
              params.put("phone",phone);
              params.put("password",password);
              params.put("address",address);
              params.put("role",role);
              params.put("gender", gender);
              params.put("upi", upi);
              return params;
          }
      };

      // request queue
      RequestQueue requestQueue = Volley.newRequestQueue(this);

      requestQueue.add(stringRequest);

  }
}
