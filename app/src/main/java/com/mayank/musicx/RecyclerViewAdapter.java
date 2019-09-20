package com.mayank.musicx;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public RecyclerViewAdapter(ArrayList<Songs> songs,Activity activity) {
        this.songs = songs;
        this.activity=activity;
    }

    private ArrayList<Songs> songs=new ArrayList<>();
    Activity activity;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_song_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs song=songs.get(position);
        if(song!=null)
        {
            holder.textView.setText(song.getName());
        }

    }

    @Override
    public int getItemCount() {
        return songs.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
        public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.tvTitle);
        imageView=itemView.findViewById(R.id.imgImage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,PlaySong.class);
                intent.putParcelableArrayListExtra("SONGS",songs);
                intent.putExtra("POSITION",getLayoutPosition());
                 activity.startActivity(intent);
            }
        });
    }
}
}
