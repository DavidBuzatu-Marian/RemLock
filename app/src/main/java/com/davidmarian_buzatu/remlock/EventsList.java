package com.davidmarian_buzatu.remlock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EventsList extends RecyclerView.Adapter<EventsList.EventsViewHolder> {

    private String[] data;
    private Context context;
    public EventsList(String[] data, Context context)
    {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_view, parent, false);
        return new EventsViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        String title = data[position];
        holder.txtTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgIcon;
        private TextView txtTitle;
        private Button viewDetails;
        private Context eventContext;
        public EventsViewHolder(View itemView, Context context) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.itemTitle);
            viewDetails = itemView.findViewById(R.id.itemButton);
            eventContext = context;
            setOnClickButton();
        }

        private void setOnClickButton() {
            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgIcon.setBackground(ContextCompat.getDrawable(eventContext, R.drawable.rectangle_active));
                }
            });
        }
    }
}
