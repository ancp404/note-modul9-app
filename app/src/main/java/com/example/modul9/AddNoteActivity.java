package com.example.modul9;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modul9.dao.NoteDao;
import com.example.modul9.models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class AddNoteActivity extends AppCompatActivity {
    private EditText etId, etJudul, etDeskripsi;
    private Button btnSimpan;
    private final NoteDao noteDao = new NoteDao();
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        etDeskripsi = findViewById(R.id.add_deskripsi);
        etJudul = findViewById(R.id.add_judul);
        btnSimpan = findViewById(R.id.btn_simpan);
        Intent i =getIntent();
        String key = i.getStringExtra("key");

        if (key != null){
            btnSimpan.setVisibility(View.GONE);
//            updatebtn.setVisibility(View.VISIBLE);
            getData(key);
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);

        }else{
            btnSimpan.setVisibility(View.VISIBLE);
//            updatebtn.setVisibility(View.GONE);
            btnSimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertData();
                }
            });
        }
    }

    private void getData(String key){
        noteDao.getall(key).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    note = task.getResult().getValue(Note.class);
                    etJudul.setText(note.getTitle());
                    etDeskripsi.setText(note.getDescription());
                }
            }
        });
    }

    private void insertData() {
        String title = etJudul.getText().toString();
        String description = etDeskripsi.getText().toString();
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Make sure you fill in the note", Toast.LENGTH_SHORT).show();
        }
        note = new Note(title, description);
        noteDao.insert(note).addOnSuccessListener(success -> {
            Toast.makeText(getApplicationContext(), "Note saved!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Note error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData(String key){
//        String id = etId.getText().toString();
        String title = etJudul.getText().toString();
        String description = etDeskripsi.getText().toString();
        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(getApplicationContext(), "Make sure you fill in the note", Toast.LENGTH_SHORT).show();
        }
//        note.setId(id);
        note.setTitle(title);
        note.setDescription(description);
        noteDao.update(key, note).addOnSuccessListener(success -> {
            Toast.makeText(getApplicationContext(), "Update succesfully!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(error -> {
            Toast.makeText(getApplicationContext(), "Can't update this note", Toast.LENGTH_SHORT).show();
        });
    }
}
