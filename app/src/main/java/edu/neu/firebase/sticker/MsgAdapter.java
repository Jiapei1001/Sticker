package edu.neu.firebase.sticker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;

public class MsgAdapter extends RecyclerView.Adapter<MsgHolder> {


    private Map<String, Integer> stickerMap;

    private final ArrayList<MsgCard> msgCardList;
    private String userName;
    private String friendName;

    public MsgAdapter(ArrayList<MsgCard> msgCardList, String userName, String friendName) {
        this.msgCardList = msgCardList;
        this.userName = userName;
        this.friendName = friendName;

        // init sticker map
        this.stickerMap = Utils.loadStickerMap();

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
            holder.senderStickerTime.setText(MessageFormat.format("{0}\t\t\t\t{1}", userName, formattedDate));

            holder.receiverSticker.setImageResource(0);
            holder.receiverStickerTime.setText("");
        } else if (sender.equals(friendName) && receiver.equals(userName)) {
            holder.senderSticker.setImageResource(0);
            holder.senderStickerTime.setText("");

            holder.receiverSticker.setImageResource(stickerMap.get(sticker));
            holder.receiverStickerTime.setText(MessageFormat.format("{0}\t\t\t\t{1}", formattedDate, friendName));
        }
    }

    @Override
    public int getItemCount() {
        return this.msgCardList.size();
    }
}