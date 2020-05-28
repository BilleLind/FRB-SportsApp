package com.example.eksamensprojekt.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Besked;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class BeskedAdapter extends RecyclerView.Adapter<BeskedAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Besked> beskedList;
    private String billedeURL;

    FirebaseUser firebaseBruger;

    public BeskedAdapter(Context context, List<Besked> beskedList, String billedeURL) {
        this.beskedList = beskedList;
        this.context = context;
        this.billedeURL = billedeURL;
    }

    @NonNull
    @Override
    public BeskedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // vælger om at den skal vises i view 1 (højre) eller 0 (venstre)
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_hoejre, parent, false);
            return new BeskedAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_venstre, parent, false);
            return new BeskedAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BeskedAdapter.ViewHolder holder, int position) {

        Besked besked =  beskedList.get(position);

        holder.visBesked.setText(besked.getBesked());

        /*TODO if (billedeURL.equals("default")){
            holder.profile_billede.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mcontext).load(billedeURL).into(holder.profile_billede);
        } */



    }

    @Override
    public int getItemCount() {
        return beskedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView visBesked;
        public ImageView profilBillede;

        public ViewHolder(View itemView) {
            super(itemView);

            visBesked = itemView.findViewById(R.id.vis_besked);
            profilBillede = itemView.findViewById(R.id.profile_billede);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseBruger = FirebaseAuth.getInstance().getCurrentUser();
        if (beskedList.get(position).getAfsender().equals(firebaseBruger.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
