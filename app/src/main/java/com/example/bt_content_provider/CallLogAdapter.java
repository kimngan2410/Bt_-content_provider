package com.example.bt_content_provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {
    private List<CallLogEntry> callLogList;

    public CallLogAdapter(List<CallLogEntry> callLogList) {
        this.callLogList = callLogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallLogEntry callLogEntry = callLogList.get(position);
        holder.numberTextView.setText(callLogEntry.getNumber());
        holder.typeTextView.setText(callLogEntry.getType());
        holder.dateTextView.setText(callLogEntry.getDate());
    }

    @Override
    public int getItemCount() {
        return callLogList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberTextView;
        TextView typeTextView;
        TextView dateTextView;

        ViewHolder(View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.textViewNumber);
            typeTextView = itemView.findViewById(R.id.textViewType);
            dateTextView = itemView.findViewById(R.id.textViewDate);
        }
    }
}