package com.example.midalcontact.presenter;

import com.example.midalcontact.db.ContactDao;
import com.example.midalcontact.model.ContactModel;

import java.util.List;

public class ContactPresenter implements MainContract.MainPresenter {

    private MainContract.MainView view = null;
    private ContactDao contactDao;

    public ContactPresenter(MainContract.MainView view, ContactDao contactDao) {
        this.view = view;
        this.contactDao = contactDao;
    }


    @Override
    public void onAttach(MainContract.MainView view) {
        this.view = view;
        List<ContactModel> dataList = contactDao.getAllContacts();
        view.showContact(dataList);
    }

    @Override
    public void onDetach() {
        view = null;

    }

    @Override
    public void onAddContact(ContactModel contact) {
        try{
            contactDao.insertContact(contact);
            view.addNewContact(contact);

        }catch (Exception e){
            e.getMessage();
        }

    }

    @Override
    public void onDeleteContact(ContactModel contact, int pos) {
        try{
            contactDao.deleteContact(contact);
            view.deleteContact(contact, pos);

        }catch (Exception e){
            e.getMessage();
        }
    }


    @Override
    public void onUpdateContact(ContactModel contact) {
        try{
            contactDao.updateContact(contact);
            view.refreshData(contactDao.getAllContacts());

        }catch (Exception e){
            e.getMessage();
        }

    }

    @Override
    public void onSearchContact(String filter) {
        if (filter.isEmpty()) {
            List<ContactModel> data = contactDao.getAllContacts();
            view.refreshData(data);
        } else {
            List<ContactModel> data = contactDao.searchContact(filter);
            view.refreshData(data);

        }
    }
}
