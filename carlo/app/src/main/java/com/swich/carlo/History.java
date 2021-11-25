package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class History extends AppCompatActivity {

     SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;
    String userID;
    RecyclerView res;
    HistoryAdaptor adaptor;
    Booking book[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        res=(RecyclerView) findViewById(R.id.rec);

        String user=  pref.getString("user", null); // getting String
        try {
            JSONObject obj=new JSONObject(user);
            userID=obj.getString("regID");
        } catch (JSONException e) {
            e.printStackTrace();
        }


login();

    }
    public void login(){

        String URLline = getString(R.string.host)+"his.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray ar=new JSONArray(response);
                            book=new Booking[ar.length()];


                            for(int x=0;x<ar.length();x++){
                                JSONObject obj=new JSONObject(String.valueOf(ar.get(x)));
                                book[x]=new Booking(obj.getString("carID"),obj.getString("car_model"),obj.getString("car_company"),obj.getString("price"),obj.getString("drop_car"),obj.getString("pickUP"),obj.getString("bookID"));

                            }
                            adaptor=new HistoryAdaptor(book);
                            res.setHasFixedSize(true);
                            res.setLayoutManager(new LinearLayoutManager(History.this));
                            res.setAdapter(adaptor);


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
               params.put("userID",userID);
                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
    public void del(final String bookID, final String carID, final Context con){

        String URLline ="http://carlo01.000webhostapp.com/delBook.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(con, response, Toast.LENGTH_LONG).show();
                        if(response=="200"){
                           Intent i=new Intent(con,Dashboard.class);
                           startActivity(i);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(con, error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("bookID",bookID);
                params.put("carID",carID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(con);

        requestQueue.add(stringRequest);

    }

}
