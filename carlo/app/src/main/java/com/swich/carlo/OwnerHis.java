package com.swich.carlo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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


public class OwnerHis extends AppCompatActivity {

    ArrayList<Booking> arrayList;
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;
    String ownID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_his);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        String user=  pref.getString("user", null); // getting String
        try {
            JSONObject obj=new JSONObject(user);
            ownID=obj.getString("regID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrayList=new ArrayList<>();
        getDeitals();



    }
    public void getDeitals(){

        String URLline = getString(R.string.host)+"getOwnHis.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr=new JSONArray(response);
                            for(int x=0;x<arr.length();x++){
                                JSONObject obj=new JSONObject(String.valueOf(arr.get(x)));

                         Booking  book=new Booking(obj.getString("carID"),obj.getString("car_model"),obj.getString("car_company"), obj.getString("price"),obj.getString("drop_car"),obj.getString("pickUP"),obj.getString("bookID"));
                                book.setPath(obj.getString("photoparth"));
                                book.setUserID(obj.getString("userID"));
                                arrayList.add(book);
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recown);

                                //MyListAdapter adapter = new MyListAdapter(myListData);
                                OwnHIsAdap adapter=new OwnHIsAdap(arrayList);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OwnerHis.this));
                                recyclerView.setAdapter(adapter);

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
                params.put("ownID",ownID);

                return params;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}
