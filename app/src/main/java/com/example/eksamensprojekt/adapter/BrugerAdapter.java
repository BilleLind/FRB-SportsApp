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

import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.model.Bruger;
import com.example.eksamensprojekt.view.BeskedActivity;

import java.util.List;

public class BrugerAdapter extends RecyclerView.Adapter<BrugerAdapter.ViewHolder> {

    private Context mcontext;
    private List<Bruger> mBrugere;

    public BrugerAdapter(Context mcontext, List<Bruger> mBrugere) {
        this.mBrugere = mBrugere;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.bruger_item, parent, false);
        return new BrugerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Bruger bruger = mBrugere.get(position);
        holder.fornavn.setText(bruger.getFornavn());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, BeskedActivity.class);
                intent.putExtra("brugerid", bruger.getBrugerId());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBrugere.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fornavn;
        public ImageView profile_billede;

        public ViewHolder(View itemView) {
            super(itemView);

            fornavn = itemView.findViewById(R.id.fornavnChat);
            profile_billede = itemView.findViewById(R.id.profile_billede);
        }
    }
}