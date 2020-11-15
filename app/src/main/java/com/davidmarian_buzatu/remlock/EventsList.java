package com.davidmarian_buzatu.remlock;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class EventsList extends RecyclerView.Adapter<EventsList.EventsViewHolder> {

    private String[] data;
    private Context context;

    public EventsList(String[] data, Context context) {
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

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
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
                    Log.d("FIRED", "FIRED");
                    imgIcon.setBackgroundResource(R.drawable.rectangle_active);
                    PopupWindow popupWindow = getPopUpView();
                }
            });
        }

        private PopupWindow getPopUpView() {
            LayoutInflater inflater = (LayoutInflater)
                    eventContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.setAnimationStyle(R.style.popup_window_animation);
            popupWindow.showAtLocation(itemView, Gravity.BOTTOM, 0, 0);
            return popupWindow;
        }
    }


}
