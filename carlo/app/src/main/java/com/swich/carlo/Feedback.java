package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Feedback extends AppCompatActivity {
String regId;
EditText msg;
Button btn;
AwesomeValidation valid;
    JSONObject obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        final SharedPreferences pref ; // 0 - for private mode
        final SharedPreferences.Editor editor;
        msg=(EditText)findViewById(R.id.textArea);
        btn=(Button)findViewById(R.id.btn);
        valid=new AwesomeValidation(ValidationStyle.BASIC);
        valid.addValidation(this,R.id.textArea,".{20,}",R.string.invalidfeed);



        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        String user=  pref.getString("user", null); // getting String
        if(user!=null){
            try {
                obj=new JSONObject(user);
                regId=obj.getString("regID");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              if(valid.validate()){
               final String m= String.valueOf(msg.getText());


               String URLline = getString(R.string.host)+"feedback.php";


               StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {

                               if(response.equals("200")){
                                   Intent i=null;
                                   try {
                                       if(obj.getString("role").equals("user")){
                                            i=new Intent(Feedback.this,Dashboard.class);

                                       }else {
                                           i=new Intent(Feedback.this,OwnerDash.class);

                                       }
                                       startActivity(i);
                                       finish();
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }

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
                       params.put("msg",m);
                       params.put("regID",regId);
                       return params;
                   }
               };

               // request queue
               RequestQueue requestQueue = Volley.newRequestQueue(Feedback.this);

               requestQueue.add(stringRequest);


           }
           }
       });

    }
}
