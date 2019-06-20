package com.example.android.mikva3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mikva3.R;
import com.example.android.mikva3.model.Help;

import java.util.List;

public class HelpStatusListAdapter extends RecyclerView.Adapter<HelpStatusListAdapter.MyViewHolder> {

    private List<Help> moviesList;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Help help = moviesList.get(position);
        holder.txtRoom.setText(help.getRoom_key());
        holder.txtStart.setText(String.valueOf(help.getReady_press_time()));
        holder.txtEnd.setText(String.valueOf(help.getDone_press_time()));
        holder.txtDate.setText(String.valueOf(help.getHelp_cancel_time()));
        holder.txtStatus.setText(help.getStatus());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}