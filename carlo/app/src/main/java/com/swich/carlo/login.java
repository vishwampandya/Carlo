package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class login extends AppCompatActivity {
    TextView rau;
    TextView rao;
    Button btn;
    EditText email;
    EditText password;
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;
    AwesomeValidation valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rau=(TextView) findViewById(R.id.rau);
        rao=(TextView) findViewById(R.id.rao);
        valid=new AwesomeValidation(ValidationStyle.BASIC);

        rau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(login.this, register.class);

                i.putExtra("role", "user");
                startActivity(i);
                finish();


            }
        });
        rao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, register.class);

                i.putExtra("role", "owner");
                startActivity(i);
                finish();

            }
        });
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        btn=(Button)findViewById(R.id.btn);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);

        valid.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invaidEmail);
        valid.addValidation(this,R.id.pass,".{6,}",R.string.invalidPass);
         String user=  pref.getString("user", null); // getting String
        String role = null;
        if(user!=null){
            try {
                JSONObject obj=new JSONObject(user);
                   role=obj.getString("role");
            } catch (JSONException e) {
                e.printStackTrace();
            }
                if(role.equals("user")) {
                    Intent i = new Intent(login.this, Dashboard.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(login.this, OwnerDash.class);
                    startActivity(i);
                    finish();
                }
            }

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (valid.validate()) {
                    String em = String.valueOf(email.getText());
                    String pass = String.valueOf(password.getText());
                    if (em.trim() != "" && pass.trim() != "" && em != null && pass != null) {
                        login(em, pass);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong format", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }
        });


    }

    public void login(final String email, final String password){

        String URLline = getString(R.string.host)+"login.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            editor.putString("user",response);
                        editor.commit(); // commit changes
                        try {
                            JSONObject obj=new JSONObject(response);
                            String role=obj.getString("role");
                            if(role.equals("user")) {
                                Intent i = new Intent(login.this, Dashboard.class);
                                startActivity(i);
                                finish();
                            }else{
                                Intent i = new Intent(login.this, OwnerDash.class);
                                startActivity(i);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}
