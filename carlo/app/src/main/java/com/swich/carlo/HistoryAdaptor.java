package com.swich.carlo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HistoryAdaptor extends RecyclerView.Adapter<HistoryAdaptor.ViewHolder> {
    Context context;
    History h=new History();
    private Booking his[];
    public  HistoryAdaptor(Booking his[]){
        this.his=his;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


         context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View hiss = inflater.inflate(R.layout.history ,parent, false);
        ViewHolder viewHolder=new ViewHolder(hiss);

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.car.setText(his[position].getCarComp()+" "+his[position].getCarMODEL());
        holder.rs.setText( "Rs "+ String.valueOf(his[position].getPrice()));


        holder.btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                h.del(his[position].getBookID(),his[position].getCarID(),context);


            }
        });
        Date date = new Date(Long.valueOf(his[position].getPickUp()) * 1000L);

        long time = new Timestamp(System.currentTimeMillis()).getTime();
        long pick=Long.valueOf(his[position].getPickUp()) *1000L;
             if (time>pick){

                 holder.btn.setVisibility(View.GONE);
             }
    }

    @Override
    public int getItemCount() {
        return his.length;
    }


    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView car;
        public TextView rs;
        public Button btn;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            car =(TextView) itemView.findViewById(R.id.car);
            rs =(TextView) itemView.findViewById(R.id.rs);
            btn=(Button) itemView.findViewById(R.id.btn);

        }
    }

}
