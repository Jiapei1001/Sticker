package edu.neu.firebase.sticker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactHistoryAdapter extends RecyclerView.Adapter<ContactHistoryViewHolder> {

    private final ArrayList<User> contactList;
    private ContactCardClickListener contactCardClickListener;

    public ContactHistoryAdapter(ArrayList<User> contactList, ContactCardClickListener contactCardClickListener) {
        this.contactList = contactList;
        this.contactCardClickListener = contactCardClickListener;
    }

    @NonNull
    @Override
    public ContactHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout
        View view = layoutInflater.inflate(R.layout.activity_contact_card, parent, false);
        return new ContactHistoryViewHolder(view, contactCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHistoryViewHolder holder, int position) {
        User curContact = contactList.get(position);

        holder.userName.setText(curContact.getUsername());
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }
}
