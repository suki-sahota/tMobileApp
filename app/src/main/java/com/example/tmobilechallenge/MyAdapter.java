package com.example.tmobilechallenge;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    // Instance variables
    private List<ListItem> listItems;
    private Context context;

    // Constructor
    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        // text
        holder.textViewTitle.setText(listItem.gettValue());
        holder.textViewTitle.setTextColor(Color.parseColor(listItem.gettColor()));
        holder.textViewTitle.setTextSize(listItem.gettSize());

        // title_description
        if (listItem.getSize() > 3) {
            holder.textViewDescription.setText(listItem.getdValue());
            holder.textViewDescription.setTextColor(Color.parseColor(listItem.getdColor()));
            holder.textViewDescription.setTextSize(listItem.getdSize());
        }

        // image_title_description
        if (listItem.getSize() > 6) {
            Picasso.get()
                    .load(listItem.getUrl())
                    .into(holder.imageView);
            holder.imageView.getLayoutParams().width = listItem.getWidth();
            holder.imageView.getLayoutParams().height = listItem.getHeight();
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    // Inner class
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Inner class instance variables
        public TextView textViewTitle;
        public TextView textViewDescription;
        public ImageView imageView;

        // Inner class constructor
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
