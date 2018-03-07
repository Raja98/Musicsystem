package com.example.su.musicsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by su on 31/1/18.
 */

public class Viewadapter extends  RecyclerView.Adapter<Viewadapter.MyViewHolder>  {
    Context context;
    ArrayList<Detailshow> stringArrayList;
    public Viewadapter(Context context,ArrayList<Detailshow> stringArrayList){
        this.context=context;
        this.stringArrayList=stringArrayList;
    }

    @Override
    public Viewadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rowpage, parent, false));
    }

    @Override
    public void onBindViewHolder(Viewadapter.MyViewHolder holder, final int position) {
        holder.songs.setText(stringArrayList.get(position).getTitle());

        String songs_duration=stringArrayList.get(position).getDuration();

        if(String.valueOf(songs_duration) != null){
            try{
                Long time = Long.valueOf(songs_duration);
                long seconds = time/1000;
                long minutes = seconds/60;
                seconds = seconds % 60;

                if(seconds<10){
                    String csongs_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
                    holder.time.setText(csongs_duration);
                }else{
                    String ccsongs_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
                    holder.time.setText(ccsongs_duration);
                }
            }catch(NumberFormatException e){

            }
        }else{
            String nothing = "0";
            holder.time.setText("0");
        }


        holder.total_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path1=stringArrayList.get(position).getPath();
                String TItle=stringArrayList.get(position).getTitle();
                String duration=stringArrayList.get(position).getDuration();
                Log.d("pathvalue",path1);

                Intent i= new Intent(context,Musicactivity.class);
                i.putExtra("path",path1);
                i.putExtra("title",TItle);
                i.putExtra("duration",duration);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

         TextView songs,time;
         LinearLayout total_linear;
        public MyViewHolder(View itemView) {
            super(itemView);
            songs=(TextView)itemView.findViewById(R.id.songs);
            time=(TextView)itemView.findViewById(R.id.time);
            total_linear=(LinearLayout)itemView.findViewById(R.id.total_linear);
        }
    }
}
