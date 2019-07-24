package com.example.android.mikva3.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mikva3.R;
import com.example.android.mikva3.model.Help;
import com.example.android.mikva3.utils.AppUtils;

import java.util.List;

public class HelpStatusListAdapter extends RecyclerView.Adapter<HelpStatusListAdapter.MyViewHolder> {

    private List<Help> moviesList;
    final Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRoom, txtStart, txtEnd, txtDate, txtStatus;

        public MyViewHolder(View view) {
            super(view);
            txtRoom = (TextView) view.findViewById(R.id.txtRoom);
            txtStart = (TextView) view.findViewById(R.id.txtStart);
            txtEnd = (TextView) view.findViewById(R.id.txtEnd);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        }
    }

    public HelpStatusListAdapter(List<Help> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help_status_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Help help = moviesList.get(position);

        long startTime = System.currentTimeMillis() - help.getHelp_press_time();

        help.setTimer(startTime);

        holder.txtRoom.setText(help.getRoom_key());

        holder.txtStart.setText(AppUtils.getDate(help.getHelp_press_time()));

        if (!help.isStartTimer()) {
            timerRunnable = new Runnable() {

                @Override
                public void run() {

                    long millis = (help.getTimer()) + 1000;


                    help.setTimer(millis);

                    long seconds = (millis / 1000) % 60;
                    long minutes = (millis / (1000 * 60)) % 60;
                    long hours = millis / (1000 * 60 * 60);


                    holder.txtEnd.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                    timerHandler.postDelayed(this, 1000);

                }
            };
            help.setStartTimer(true);

            timerHandler.postDelayed(timerRunnable, 0);
        }



    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}