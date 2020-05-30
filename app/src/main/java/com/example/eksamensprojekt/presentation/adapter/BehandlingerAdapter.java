package com.example.eksamensprojekt.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.presentation.presenter.BehandlingPresenter;

import java.util.List;

public class BehandlingerAdapter extends RecyclerView.Adapter<BehandlingerAdapter.MyViewHolder> {

    Context context;
    List<BehandlingPresenter> behandlingPresenterList;

    public BehandlingerAdapter(Context context, List<BehandlingPresenter> behandlingPresenterList) {
        this.context = context;
        this.behandlingPresenterList = behandlingPresenterList;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.behandling_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.txtBehandlingsTid.setText(behandlingPresenterList.get(position).getTid());
        myViewHolder.txtBehandlingsPris.setText(behandlingPresenterList.get(position).getPris());


    }

    @Override
    public int getItemCount() {

        return behandlingPresenterList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtBehandlingsTid, txtBehandlingsPris;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBehandlingsTid = (TextView) itemView.findViewById(R.id.tid_text);
            txtBehandlingsPris = (TextView) itemView.findViewById(R.id.pris_text);
        }
    }
}
