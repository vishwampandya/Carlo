package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {
String response;
RecyclerView res;
ArrayList <Message>mes=new ArrayList<>();
MessageAdaptor adaptor;
EditText newmsg;
Button btn;
    JSONObject obj;
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;
    JSONObject userobj;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
    res=findViewById(R.id.chatRec);

    btn=(Button) findViewById(R.id.send);
    newmsg=(EditText)findViewById(R.id.newmsg);
    pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
    editor = pref.edit();

    String user=  pref.getString("user", null); // getting String
    try {
         userobj=new JSONObject(user);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    if (extras == null) {
            response = null;
        } else {
            response = extras.getString("data");

        }


        try {
              obj=new JSONObject(response);
            setTitle(obj.getString("Name"));
            loadmsg();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msg= String.valueOf(newmsg.getText()).trim();
                    if(msg!=null && (msg.length() >0) ){
                        try {
                            sendmsg(msg);
                            adaptor.addMsg(new Message(msg,userobj.getString("regID"),obj.getString("regID")));
                            newmsg.setText("");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }



}

public void sendmsg(final String msg){

        String URLline = getString(R.string.host)+"sendmsg.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("200")){
                            Toast.makeText(getApplicationContext(),"message Sent",Toast.LENGTH_SHORT);

                        }
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG);
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
                params.put("msg",msg);
                try {
                    params.put("senderID",userobj.getString("regID"));
                    params.put("receiverID",obj.getString("regID"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }

  public void loadmsg(){
      String URLline = getString(R.string.host)+"getAllMsg.php";


      StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {

                      try {
                          JSONArray ar=new JSONArray(response);
                          for(int x=0; x<ar.length();x++){
                             JSONObject resobj=new JSONObject(String.valueOf(ar.get(x)));
                             mes.add(new Message(resobj.getString("message"),resobj.getString("senderId"),resobj.getString("reciverID")));
                          }

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                      adaptor=new MessageAdaptor(mes);
                      res.setHasFixedSize(true);
                      res.setLayoutManager(new LinearLayoutManager(Chat.this));
                      res.setAdapter(adaptor);
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
              ;
              try {
                  params.put("userID",userobj.getString("regID"));

              } catch (JSONException e) {
                  e.printStackTrace();
              }
              return params;
          }
      };

      // request queue
      RequestQueue requestQueue = Volley.newRequestQueue(this);

      requestQueue.add(stringRequest);
  }


}
