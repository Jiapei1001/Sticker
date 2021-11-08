package edu.neu.firebase.sticker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MsgAdapter extends RecyclerView.Adapter<MsgHolder> {

    private static Map<String, Integer> stickerMap = new HashMap<String, Integer>() {{
        put("sticker1", R.drawable.sticker1);
        put("sticker2", R.drawable.sticker2);
        put("sticker3", R.drawable.sticker3);
        put("sticker4", R.drawable.sticker4);
        put("sticker5", R.drawable.sticker5);
        put("sticker6", R.drawable.sticker6);
        put("sticker7", R.drawable.sticker7);
        put("sticker8", R.drawable.sticker8);
        put("sticker9", R.drawable.sticker9);
        put("sticker10", R.drawable.sticker10);
        put("sticker11", R.drawable.sticker11);
        put("sticker12", R.drawable.sticker12);
        put("sticker13", R.drawable.sticker13);
        put("sticker14", R.drawable.sticker14);
        put("sticker15", R.drawable.sticker15);
    }};
    private final ArrayList<MsgCard> msgCardList;
    private String userName;
    private String friendName;

    public MsgAdapter(ArrayList<MsgCard> msgCardList, String userName, String friendName) {
        this.msgCardList = msgCardList;
        this.userName = userName;
        this.friendName = friendName;
    }

    @NonNull
    @Override
    public MsgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_card, parent, false);
        return new MsgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgHolder holder, int position) {
        MsgCard msgCard = msgCardList.get(position);

        String sticker = msgCard.getSticker();
        String sender = msgCard.getSender();
        String receiver = msgCard.getReceiver();
        String formattedDate = Utils.formatTime(msgCard.getSendTime());

        if (sender.equals(userName) && receiver.equals(friendName)) {
            holder.senderSticker.setImageResource(stickerMap.get(sticker));
            holder.senderStickerTime.setText(formattedDate);

            holder.receiverSticker.setImageResource(0);
            holder.receiverStickerTime.setText("");
        } else if (sender.equals(friendName) && receiver.equals(userName)) {
            holder.senderSticker.setImageResource(0);
            holder.senderStickerTime.setText("");

            holder.receiverSticker.setImageResource(stickerMap.get(sticker));
            holder.receiverStickerTime.setText(formattedDate);
        }
    }

    @Override
    public int getItemCount() {
        return this.msgCardList.size();
    }
}
