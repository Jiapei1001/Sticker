package edu.neu.firebase.sticker;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * This is a class that stores the reference of the card layout views that have to be dynamically
 * modified during the execution of the program by a list of data
 */
public class ContactHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public Button sendStickers;
    public Button chatHistory;

    public ContactHistoryViewHolder(@NonNull View itemView, final ContactCardClickListener listener) {
        super(itemView);

        this.userName = itemView.findViewById(R.id.userName);
        this.sendStickers = itemView.findViewById(R.id.btnSendStickers);
        this.chatHistory = itemView.findViewById(R.id.btnChatHistory);

        sendStickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    // Returns the position of the ViewHolder in terms of the latest layout pass
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onSendStickersClick(position);
                    }
                }
            }
        });

        chatHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    // Returns the position of the ViewHolder in terms of the latest layout pass
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onChatHistoryClick(position);
                    }
                }
            }
        });
    }


}
