package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class Listbook extends AppCompatActivity {
JSONArray array;
    MyListData[] myListData = null;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbook);



        login();


    }
    public void login(){

        String URLline = getString(R.string.host)+"/getcarlist.php";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                        @Override
                    public void onResponse(String response) {
                              try {

                                array=new JSONArray(response);
                                myListData=new MyListData[array.length()];
                                JSONObject obj;
                                for(int x=0;x<array.length();x++){
                                   obj=array.getJSONObject(x);
                                   myListData[x]=new MyListData(obj.getString("carID"),obj.getString("car_company"),obj.getString("car_model"),obj.getString("description"),obj.getString("ownerID"),obj.getString("photoparth"),obj.getString("perkmprice"));
                                }

                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                                MyListAdapter adapter = new MyListAdapter(myListData);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Listbook.this));
                                recyclerView.setAdapter(adapter);


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

                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }

}
