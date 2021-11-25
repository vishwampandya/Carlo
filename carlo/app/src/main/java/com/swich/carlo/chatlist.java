package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

public class chatlist extends AppCompatActivity {

    ListView list;
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;
    String regID;
String users[];
String regIDS[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        list=findViewById(R.id.msglist);
        String user=  pref.getString("user", null);
        try {
            JSONObject obj=new JSONObject(user);
            regID=obj.getString("regID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getUsers();

    }
    public void getUsers(){

        String URLline = getString(R.string.host)+"getChatList.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ar=new JSONArray(response);
                              users=new String[ar.length()];
                             regIDS=new String[ar.length()];
                            for(int x=0;x<ar.length();x++) {
                                JSONObject obj = new JSONObject(String.valueOf(ar.get(x)));
                                users[x]=obj.getString("Name");
                                regIDS[x]=obj.getString("senderId");
                            }
                            ArrayAdapter <String> adptor=new ArrayAdapter<>(chatlist.this,android.R.layout.simple_list_item_1,users);
                            list.setAdapter(adptor);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                                    //Toast.makeText(chatlist.this,regIDS[postion],Toast.LENGTH_LONG).show();
                                       getUserData(regIDS[postion]);
                                }
                            });
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
                params.put("regID",regID);
                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
    public void getUserData(final String id){
        String URLline = getString(R.string.host)+"getUserDetail.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent i=new Intent(chatlist.this,Chat.class);
                        i.putExtra("data",response);
                        startActivity(i);
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
                params.put("regID",id);
                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}
