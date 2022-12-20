package com.example.midalcontact.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class ContactModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String contactName;
    private String contactNumber;
    private String contactLabel;

    @Ignore
    private Drawable imgContact;
    @Ignore
    private Drawable imgOption;


    protected ContactModel(Parcel in) {
        id = in.readInt();
        contactName = in.readString();
        contactNumber = in.readString();
        contactLabel = in.readString();
    }

    public ContactModel(){

    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel in) {
            return new ContactModel(in);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };

    public String getContactName(){
        return contactName;
    }

    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactLabel() {
        return contactLabel;
    }

    public void setContactLabel(String contactLabel) {
        this.contactLabel = contactLabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getImgContact() {
        return imgContact;
    }

    public void setImgContact(Drawable imgContact) {
        this.imgContact = imgContact;
    }

    public Drawable getImgOption() {
        return imgOption;
    }

    public void setImgOption(Drawable imgOption) {
        this.imgOption = imgOption;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(contactName);
        parcel.writeString(contactNumber);
        parcel.writeString(contactLabel);
    }
}
