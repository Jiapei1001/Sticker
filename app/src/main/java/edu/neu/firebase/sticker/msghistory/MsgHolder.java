package edu.neu.firebase.sticker.msghistory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.firebase.sticker.R;

public class MsgHolder extends RecyclerView.ViewHolder {

    private ImageView senderSticker;
    private ImageView receiverSticker;
    private TextView senderStickerTime;
    private TextView receiverStickerTime;

    public MsgHolder(@NonNull View itemView) {
        super(itemView);
        senderSticker = itemView.findViewById(R.id.imageViewSender);
        receiverSticker = itemView.findViewById(R.id.imageViewReceiver);
        senderStickerTime = itemView.findViewById(R.id.textViewSenderTime);
        receiverStickerTime = itemView.findViewById(R.id.textViewReceiverTime);
    }
}
