package com.example.bt_content_provider;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<SmsMessage> messageList;

    public MessageAdapter(List<SmsMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        SmsMessage message = messageList.get(position);
        holder.textViewAddress.setText(message.getAddress());
        holder.textViewBody.setText(message.getBody());
        holder.textViewDate.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAddress;
        TextView textViewBody;
        TextView textViewDate;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewBody = itemView.findViewById(R.id.textViewBody);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}

