package com.example.midalcontact.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.midalcontact.model.ContactModel;


@Database(entities = {ContactModel.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static DatabaseManager databaseManager;

    public static DatabaseManager getDatabaseManager(Context context) {
        if (databaseManager == null) {
            databaseManager = Room
                    .databaseBuilder(context.getApplicationContext(), DatabaseManager.class, "contact_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return (DatabaseManager) databaseManager;
    }

}
