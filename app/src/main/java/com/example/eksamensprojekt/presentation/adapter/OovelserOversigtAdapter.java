package com.example.eksamensprojekt.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.eksamensprojekt.R;

import java.util.ArrayList;


public class OovelserOversigtAdapter extends RecyclerView.Adapter<OovelserOversigtAdapter.ViewHolder> {

    private static final String TAG = "OovelserAdapter"; //Til debugging

    private Context mContext;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();


    public OovelserOversigtAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages) {
        this.mContext = mContext;
        this.mImageNames = mImageNames;
        this.mImages = mImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Putter ViewHolders ind i deres positioner
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_oovelserlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called."); //Debugging hvis nogle viewholders ikke kommer ind

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.oversigtImageView);

        holder.oversigtTextView.setText(mImageNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_LONG).show();
            }
        }
        );
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder holder hver widget i memory, og gør klar til at tilføje dem til RecyclerViewet

        TextView oversigtTextView;
        ImageView oversigtImageView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            oversigtTextView = itemView.findViewById(R.id.oversigt_Text_View);
            oversigtImageView = itemView.findViewById(R.id.oversigt_Image_View);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
