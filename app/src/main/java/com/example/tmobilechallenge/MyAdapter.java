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
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ((ViewHolder) holder).bindData(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    // Separate our concerns with an inner class
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Inner class instance variables
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView imageView;

        // Inner class constructor
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        // Bind data here to allow for 'dumb' adapter
        public void bindData(final ListItem listItem) {
            // text
            textViewTitle.setText(listItem.gettValue());
            textViewTitle.setTextColor(Color.parseColor(listItem.gettColor()));
            textViewTitle.setTextSize(listItem.gettSize());

            // title_description
            if (listItem.getSize() > 3) {
                textViewDescription.setText(listItem.getdValue());
                textViewDescription.setTextSize(listItem.getdSize());
                textViewDescription.setTextColor(Color.parseColor(listItem.getdColor()));
            }

            // image_title_description
            if (listItem.getSize() > 6) {
                Picasso.get().load(listItem.getUrl()).into(imageView);
                imageView.getLayoutParams().width = listItem.getWidth();
                imageView.getLayoutParams().height = listItem.getHeight();
            }
        }
    }
}
