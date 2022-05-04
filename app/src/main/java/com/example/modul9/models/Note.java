package com.example.modul9.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Note {
    private String key;
    private String title;
    private String description;

    public Note() {
    }
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.id);
//        dest.writeString(this.title);
//        dest.writeString(this.description);
//    }

    public void readFromParcel(Parcel source) {
        this.key = source.readString();
        this.title = source.readString();
        this.description = source.readString();
    }

    protected Note(Parcel in) {
        this.key = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
