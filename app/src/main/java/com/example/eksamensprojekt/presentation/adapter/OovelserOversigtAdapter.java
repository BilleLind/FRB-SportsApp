package com.example.eksamensprojekt.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eksamensprojekt.R;
import com.example.eksamensprojekt.data.model.Oovelser;

import java.util.List;


public class OovelserOversigtAdapter extends RecyclerView.Adapter<OovelserOversigtAdapter.ViewHolder> {

    private Context mContext;
    private List<Oovelser> mOovelser;


    public OovelserOversigtAdapter(Context mContext, List<Oovelser> oovelser) {
        this.mContext = mContext;
        this.mOovelser = oovelser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) { //Putter ViewHolders ind i deres positioner
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_oovelserlist, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        //Angiver navnet
        viewHolder.oversigtTextView.setText(mOovelser.get(position).getName());

        //Angiver billedet
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_foreground);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(mOovelser.get(position).getImage())
                .into(viewHolder.oversigtImageView);
    }

    @Override
    public int getItemCount() { //Antal ViewHolders som skal skabes
        return mOovelser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder holder hver widget i memory, og gør klar til at tilføje dem til RecyclerViewet
        TextView oversigtTextView;
        ImageView oversigtImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            oversigtTextView = itemView.findViewById(R.id.oversigt_Text_View);
            oversigtImageView = itemView.findViewById(R.id.oversigt_Image_View);
        }
    }
}
