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
import com.example.eksamensprojekt.presentation.presenter.AnsattePresenter;

import java.util.ArrayList;
import java.util.List;

public class AnsatteAdapter extends RecyclerView.Adapter<AnsatteAdapter.AnsatteViewHolder> {


    Context context;
    List<AnsattePresenter> ansattePresenterList;
    List<CardView> ansatteCardViewList;

    public AnsatteAdapter(Context context, List<AnsattePresenter> ansattePresenterList){
        this.context = context;
        this.ansattePresenterList = ansattePresenterList;
        ansatteCardViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AnsatteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_ansatte_layout, parent, false );

        return new AnsatteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnsatteViewHolder ansatteViewHolder, int position) {

        ansatteViewHolder.txtAnsatteNavn.setText(ansattePresenterList.get(position).getNavn());

        if (!ansatteCardViewList.contains(ansatteViewHolder.cardviewAnsatte))
            ansatteCardViewList.add(ansatteViewHolder.cardviewAnsatte);

        ansatteViewHolder.setRecyclerItemSelectedListener(new RecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {

                for (CardView cardView:ansatteCardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                ansatteViewHolder.cardviewAnsatte.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));

            }
        });


    }

    @Override
    public int getItemCount() {

        return ansattePresenterList.size();
    }


    public class AnsatteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardviewAnsatte;
        TextView txtAnsatteNavn;

        RecyclerItemSelectedListener recyclerItemSelectedListener;

        public void setRecyclerItemSelectedListener(RecyclerItemSelectedListener recyclerItemSelectedListener) {
            this.recyclerItemSelectedListener = recyclerItemSelectedListener;
        }


        public AnsatteViewHolder(@NonNull View itemView){
            super(itemView);

            txtAnsatteNavn = (TextView) itemView.findViewById(R.id.ansatte_navn);
            cardviewAnsatte = (CardView) itemView.findViewById(R.id.cardview_ansatte);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            recyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());
        }



    }
}
