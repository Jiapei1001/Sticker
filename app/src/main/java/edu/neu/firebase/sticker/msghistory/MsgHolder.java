package edu.neu.firebase.sticker.msghistory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.firebase.sticker.R;

public class MsgHolder extends RecyclerView.ViewHolder {

    public ImageView senderSticker;
    public ImageView receiverSticker;
    public TextView senderStickerTime;
    public TextView receiverStickerTime;

    public MsgHolder(@NonNull View itemView) {
        super(itemView);
        senderSticker = itemView.findViewById(R.id.msgImageViewSender);
        receiverSticker = itemView.findViewById(R.id.msgImageViewReceiver);
        senderStickerTime = itemView.findViewById(R.id.msgSendTime);
        receiverStickerTime = itemView.findViewById(R.id.msgReceivTime);
    }
}
