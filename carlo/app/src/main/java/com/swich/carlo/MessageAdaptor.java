package com.swich.carlo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.ViewHolder>{
    SharedPreferences pref ; // 0 - for private mode
    SharedPreferences.Editor editor;

    Context context;
    ArrayList<Message> msg;
    MessageAdaptor(ArrayList<Message> msg){
        this.msg=msg;
    }

 public void addMsg(Message msg){

        this.msg.add(msg);
     notifyDataSetChanged();
 }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View hiss = inflater.inflate(R.layout.chatmessage ,parent, false);
        MessageAdaptor.ViewHolder viewHolder=new MessageAdaptor.ViewHolder(hiss);

        return  viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //holder.msg.setText(msg.get(position).getMessage());
        pref =context.getSharedPreferences("MyPref", 0); // 0 - for private mode
       String user=  pref.getString("user", null); // getting String

        try {
            JSONObject obj=new JSONObject(user);
            if(msg.get(position).getSenderID().equals(obj.getString("regID"))){
                holder.msg2.setText(msg.get(position).getMessage());
                holder.msg2.setVisibility(View.VISIBLE);
            }else{
                holder.msg.setText(msg.get(position).getMessage());
                holder.msg.setVisibility(View.VISIBLE);
            }

            //            if(!(msg.get(position).getSenderID()==obj.getString("regID"))){
//                holder.msg2.setText(msg.get(position).getMessage());
//                holder.msg2.setVisibility(View.VISIBLE);
//            }else{
//                holder.msg.setText(msg.get(position).getMessage());
//                holder.msg.setVisibility(View.VISIBLE);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView msg,msg2;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            msg =(TextView) itemView.findViewById(R.id.msg1);
            msg2=(TextView) itemView.findViewById(R.id.msg2);

        }
    }
}
