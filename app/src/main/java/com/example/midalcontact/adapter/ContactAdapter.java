package com.example.midalcontact.adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midalcontact.R;
import com.example.midalcontact.model.ContactModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    List<ContactModel> contactsList = new ArrayList<>();
    CallbackEvent callbackEvent;

    //private int previousExpandedPosition  = -1;
    // private int mExpandedPosition  = -1;


    public ContactAdapter(CallbackEvent callbackEvent) {
        this.callbackEvent = callbackEvent;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindContacts(contactsList.get(position));

        /*final boolean isExpanded = position==mExpandedPosition;
        holder.details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void addDataToList(ArrayList<ContactModel> contact) {
        contactsList.clear();
        contactsList.addAll(contact);
        sortList(contact);
        notifyDataSetChanged();
    }

    public void addItem(ContactModel contact) {
        contactsList.add(contact);
        notifyDataSetChanged();
    }

    public void sortList(ArrayList<ContactModel> data) {
        Collections.sort(data, new Comparator<ContactModel>() {
            @Override
            public int compare(ContactModel contactModel, ContactModel t1) {
                return contactModel.getContactName().compareTo(t1.getContactName());
            }
        });

    }

    public void deleteItem(int position){
        contactsList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateItem(int pos, ContactModel contact){
        contactsList.set(pos, contact);
        notifyItemChanged(pos);

    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView contactName;
        TextView contactNumber;
        TextView contactSubText;
        ImageView imgContact;
        ImageView imgOption;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.txt_name_family);
            contactSubText = itemView.findViewById(R.id.txt_sub_name);
            contactNumber = itemView.findViewById(R.id.txt_phone);
            imgContact = itemView.findViewById(R.id.img_call_phone);
            imgOption = itemView.findViewById(R.id.img_edit_contact);
        }

        private void bindContacts(ContactModel data) {
            contactName.setText(data.getContactName());
            contactSubText.setText(data.getContactName().substring(0, 2));
            contactNumber.setText(data.getContactNumber());
            imgContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callbackEvent.onCall(data.getContactNumber());
                }
            });

            imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(itemView.getContext(), imgOption);
                    popupMenu.inflate(R.menu.menu_popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.edit:
                                    callbackEvent.onEdit(data, getAdapterPosition());
                                    break;

                                case R.id.delete:
                                    callbackEvent.onDelete(data, getAdapterPosition());
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();

                }
            });

        }

    }

    public interface CallbackEvent {
        void onCall(String number);

        void onEdit(ContactModel contactModel, int pos);

        void onDelete(ContactModel contactModel, int id);
    }


}
