package com.example.modul9.dao;

import com.example.modul9.models.Note;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NoteDao {
    private DatabaseReference databaseReference;

    public NoteDao(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://pamfirebase-b3309-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = db.getReference(Note.class.getSimpleName());
    }

    public Task<Void> insert(Note note){
        return databaseReference.push().setValue(note);
    }

    public Task<Void> update(String key, Note note){
        return databaseReference.child(key).setValue(note);
    }
    public Task<Void> delete(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get(){
        return databaseReference.orderByKey();
    }

    public Task<DataSnapshot> getall(String key){
        return databaseReference.child(key).get();
    }
}
