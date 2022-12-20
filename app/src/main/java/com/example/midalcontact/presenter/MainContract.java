package com.example.midalcontact.presenter;

import com.example.midalcontact.model.ContactModel;

import java.util.List;

public interface MainContract {

    interface MainPresenter {

        void onAttach(MainView view);
        void onDetach();
        void onAddContact(ContactModel contact);
        void onDeleteContact(ContactModel contact, int pos);
        void onUpdateContact(ContactModel contact);
        void onSearchContact(String filter);

    }

    interface MainView{

        void showContact(List<ContactModel> data);
        void refreshData(List<ContactModel> list);
        void addNewContact(ContactModel contact);
        void deleteContact(ContactModel contact, int pos);
        void updateContact(ContactModel contact, int pos);
    }


}
