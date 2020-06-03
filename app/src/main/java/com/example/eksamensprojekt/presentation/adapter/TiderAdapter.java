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
import com.example.eksamensprojekt.presentation.presenter.TiderPresenter;

import java.util.ArrayList;
import java.util.List;


public class TiderAdapter extends RecyclerView.Adapter<com.example.eksamensprojekt.presentation.adapter.TiderAdapter.TiderViewHolder> {

    Context context;
    List<TiderPresenter> tiderPresenterList;
    List<CardView> tiderCardViewList;

    public TiderAdapter(Context context, List<TiderPresenter> tiderPresenterList) {
        this.context = context;
        this.tiderPresenterList = tiderPresenterList;
        tiderCardViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_tider_layout, parent, false);

        return new TiderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TiderViewHolder tiderViewHolder, int position) {

        tiderViewHolder.txtTider.setText(tiderPresenterList.get(position).getTid());

        if (!tiderCardViewList.contains(tiderViewHolder.cardviewTider))
            tiderCardViewList.add(tiderViewHolder.cardviewTider);

        tiderViewHolder.setRecyclerItemSelectedListener(new RecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {

                for (CardView cardView : tiderCardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                tiderViewHolder.cardviewTider.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));

            }
        });

    }

    @Override
    public int getItemCount() {

        return tiderPresenterList.size();
    }

    public class TiderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        CardView cardviewTider;
        TextView txtTider;

        RecyclerItemSelectedListener recyclerItemSelectedListener;

        public void setRecyclerItemSelectedListener(RecyclerItemSelectedListener recyclerItemSelectedListener) {
            this.recyclerItemSelectedListener = recyclerItemSelectedListener;
        }

        public TiderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTider = (TextView) itemView.findViewById(R.id.tider_tv);
            cardviewTider = (CardView) itemView.findViewById(R.id.cardview_tider);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            recyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());
        }
    }
}