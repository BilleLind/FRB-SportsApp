package com.example.eksamensprojekt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.model.Bruger;
import com.example.eksamensprojekt.model.Chat;
import com.example.eksamensprojekt.view.BeskedActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class BeskedAdapter extends RecyclerView.Adapter<BeskedAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 0;
    private Context mcontext;
    private List<Chat> mChat;
    private String billedeURL;

    FirebaseUser fireBruger;

    public BeskedAdapter(Context mcontext, List<Chat> mChat, String billedeURL) {
        this.mChat = mChat;
        this.mcontext = mcontext;
        this.billedeURL = billedeURL;
    }

    @NonNull
    @Override
    public BeskedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {


            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_hoejre, parent, false);
            return new BeskedAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_item_venstre, parent, false);
            return new BeskedAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BeskedAdapter.ViewHolder holder, int position) {

        Chat chat =  mChat.get(position);

        holder.vis_besked.setText(chat.getBesked());

        if (billedeURL.equals("default")){
            holder.profile_billede.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mcontext).load(billedeURL).into(holder.profile_billede);
        }
        


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView vis_besked;
        public ImageView profile_billede;

        public ViewHolder(View itemView) {
            super(itemView);

            vis_besked = itemView.findViewById(R.id.vis_besked);
            profile_billede = itemView.findViewById(R.id.profile_billede);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fireBruger = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getAfsender().equals(fireBruger.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
