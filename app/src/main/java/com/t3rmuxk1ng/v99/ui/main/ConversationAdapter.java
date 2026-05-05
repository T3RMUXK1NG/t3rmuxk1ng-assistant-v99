package com.t3rmuxk1ng.v99.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.t3rmuxk1ng.v99.R;
import com.t3rmuxk1ng.v99.model.ConversationMessage;
import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {
    private List<ConversationMessage> messages = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConversationMessage msg = messages.get(position);
        holder.messageText.setText(msg.getText());
    }

    @Override
    public int getItemCount() { return messages.size(); }

    public void addMessage(ConversationMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ViewHolder(View v) {
            super(v);
            messageText = v.findViewById(R.id.messageText);
        }
    }
}
