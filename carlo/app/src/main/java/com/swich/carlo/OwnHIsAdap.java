package com.swich.carlo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OwnHIsAdap extends RecyclerView.Adapter<OwnHIsAdap.ViewHolder> {

    ArrayList<Booking> bookings;
    Context context;
    AlertDialog.Builder builder;


    OwnHIsAdap(ArrayList<Booking> bookings){
        this.bookings=bookings;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View hiss = inflater.inflate(R.layout.ownhis ,parent, false);
        OwnHIsAdap.ViewHolder viewHolder=new OwnHIsAdap.ViewHolder(hiss);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.car.setText(bookings.get(position).getCarComp() +" " + bookings.get(position).getCarMODEL() );
        holder.rs.setText("RS: " + bookings.get(position).getPrice());
        holder.drop.setText("Drop Time: "+bookings.get(position).getdropDate());
        holder.pickup.setText("Pickup Time"+bookings.get(position).getpickDate());
        Picasso.get().load("http://carlo01.000webhostapp.com/uploadedFiles/"+bookings.get(position).getPath()+".JPG").into(holder.img);
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Did you get your car??")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            endTrip(bookings.get(position).getCarID(),bookings.get(position).getBookID());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        final AlertDialog alert = builder.create();

        long now = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = Instant.now().toEpochMilli();
        }
        Log.d("here...drop.....",bookings.get(position).drop);
        Log.d("here...now.....",String.valueOf(now));

        if(now <=Long.valueOf(bookings.get(position).getDrop())*1000L){
            holder.tripbtn.setVisibility(View.GONE);
        }

        holder.tripbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setTitle("End Tirp?");
                alert.show();
            }
        });
        holder.userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api call get user Detail then change intent

                getDeitals(bookings.get(position).getUserID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.bookings.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView car;
        public TextView rs;
        public TextView drop;
        public TextView pickup;
        public ImageView img;
        public Button tripbtn;
        public Button userbtn;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            car =(TextView) itemView.findViewById(R.id.carname);
            rs =(TextView) itemView.findViewById(R.id.price);
            drop=(TextView) itemView.findViewById(R.id.drop);
            pickup=(TextView) itemView.findViewById(R.id.pick);
            img=(ImageView)itemView.findViewById(R.id.carimg);
            tripbtn=(Button) itemView.findViewById(R.id.carbtn);
            userbtn=(Button) itemView.findViewById(R.id.userinfo);

        }
    }


    public void getDeitals(final String regID){

        String URLline = "http://carlo01.000webhostapp.com/getUser.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent i=new Intent(context,OwnerDetails.class);
                        i.putExtra("data",response);
                        context.startActivity(i);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs

                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);

    }


    public void endTrip(final String carID,final  String bookID){

        String URLline = "http://carlo01.000webhostapp.com/getCarBack.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("200")){
                            Intent i=new Intent(context,OwnerDash.class);
                            context.startActivity(i);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs

                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
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

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(stringRequest);

    }


}
