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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.everything.providers.android.calendar.Event;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class EventsList extends RecyclerView.Adapter<EventsList.EventsViewHolder> {

    private List<Event> data;
    private Context context;

    public EventsList(List<Event> data, Context context) {
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
        Event event = data.get(position);
        holder.txtTitle.setText(event.title);

        holder.txtDate.setText(getDate(event.dTStart));
        holder.event = data.get(position);
    }

    public static String getDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM YYYY");
        Date startDate = new Date(time);
        return sdf.format(startDate);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        private Event event;
        private ImageView imgIcon;
        private TextView txtTitle, txtDate;
        private Button viewDetails;
        private Context eventContext;

        public EventsViewHolder(View itemView, Context context) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.itemTitle);
            txtDate = itemView.findViewById(R.id.itemDate);
            viewDetails = itemView.findViewById(R.id.itemButton);
            eventContext = context;
            setOnClickButton();
        }

        private void setOnClickButton() {
            viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgIcon.setBackgroundResource(R.drawable.rectangle_active);
                    PopupWindow popupWindow = getPopUpView();
                }
            });
        }

        private void setPopupInfo(View popupView) {
            TextView tvDescription = popupView.findViewById(R.id.popup_description);
            TextView tvTitle = popupView.findViewById(R.id.popup_event_title);
            TextView tvDate = popupView.findViewById(R.id.popup_date);
            tvTitle.setText(event.title);
            tvDescription.setText(event.description.length() > 0 ? event.description : "No description provided for this event.");
            tvDate.setText(getDate(event.dTStart));
        }

        private PopupWindow getPopUpView() {
            LayoutInflater inflater = (LayoutInflater)
                    eventContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);
            setPopupInfo(popupView);
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
