package com.example.eksamensprojekt.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.presentation.Interface.RecyclerItemSelectedListener;
import com.example.eksamensprojekt.presentation.presenter.BehandlingPresenter;

import java.util.ArrayList;
import java.util.List;

public class BehandlingerAdapter extends RecyclerView.Adapter<BehandlingerAdapter.BehandlingViewHolder> {

    Context context;
    List<BehandlingPresenter> behandlingPresenterList;
    List<CardView> cardViewList;


    public BehandlingerAdapter(Context context, List<BehandlingPresenter> behandlingPresenterList) {
        this.context = context;
        this.behandlingPresenterList = behandlingPresenterList;
        cardViewList = new ArrayList<>();

    }




    @NonNull
    @Override
    public BehandlingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.behandling_layout, viewGroup, false);

        return new BehandlingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BehandlingViewHolder behandlingViewHolder, int position) {
        behandlingViewHolder.txtBehandlingsTid.setText(behandlingPresenterList.get(position).getVarighed());
        behandlingViewHolder.txtBehandlingsPris.setText(behandlingPresenterList.get(position).getPris());

        if (!cardViewList.contains(behandlingViewHolder.cardviewBehandlinger))
            cardViewList.add(behandlingViewHolder.cardviewBehandlinger);

        behandlingViewHolder.setRecyclerItemSelectedListener(new RecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //set white background for all card not selected
                for (CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //Set selected background for only selected item
                behandlingViewHolder.cardviewBehandlinger.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));



            }
        });



    }

    @Override
    public int getItemCount() {

        return behandlingPresenterList.size();
    }


    public class BehandlingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardviewBehandlinger;
        TextView txtBehandlingsTid, txtBehandlingsPris;

        RecyclerItemSelectedListener recyclerItemSelectedListener;

        public void setRecyclerItemSelectedListener(RecyclerItemSelectedListener recyclerItemSelectedListener) {
            this.recyclerItemSelectedListener = recyclerItemSelectedListener;
        }

        public BehandlingViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBehandlingsTid = (TextView) itemView.findViewById(R.id.tid_text);
            txtBehandlingsPris = (TextView) itemView.findViewById(R.id.pris_text);
            cardviewBehandlinger = (CardView) itemView.findViewById(R.id.cardview_behandlinger);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            recyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());

        }
    }
}
