package com.example.midalcontact.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.midalcontact.model.ContactModel;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    public void insertContact(ContactModel contact);

    @Query("SELECT * FROM contact")
    public List<ContactModel> getAllContacts();

    @Query("SELECT * FROM contact WHERE contactName LIKE '%'|| :name||'%'")
    public List<ContactModel> searchContact(String name);

    @Update
    public void updateContact(ContactModel contact);

    @Delete
    public void deleteContact(ContactModel contact);
}

