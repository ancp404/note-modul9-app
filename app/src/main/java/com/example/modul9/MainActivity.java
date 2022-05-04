package com.example.modul9;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.modul9.adapter.NoteAdapter;
import com.example.modul9.dao.NoteDao;
import com.example.modul9.models.Note;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvEmail, tvUid;
    private FirebaseAuth mAuth;
    private FloatingActionButton btnAdd;
    private Button logout;
    private ImageButton editNote, deleteNote;
    private GoogleSignInClient googleSignInClient;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteDao noteDao = new NoteDao();
    private ArrayList<Note> noteArrayList = new ArrayList<>();
    private Note note;
    DatabaseReference database = FirebaseDatabase.getInstance("https://pamfirebase-b3309-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEmail = findViewById(R.id.tv_email);
        tvUid = findViewById(R.id.tv_uid);
        btnAdd =findViewById(R.id.btn_add);
        logout = findViewById(R.id.btn_keluar);
        editNote = findViewById(R.id.edit_note);
        deleteNote = findViewById(R.id.delete_note);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        logout.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
//        editNote.setOnClickListener(this); ini kalo dua2nya dinyalain ntar stopped working
//        deleteNote.setOnClickListener(this);
        noteData();
        RecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            tvEmail.setText(currentUser.getEmail());
            tvUid.setText(currentUser.getUid());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_keluar:
                logOut();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_note:
                break;
        }
    }

    private void noteData(){
        noteDao.get().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    note = dataSnapshot.getValue(Note.class);
                    note.setKey(dataSnapshot.getKey());
                    noteArrayList.add(note);
                }
                adapter.notifyDataSetChanged();
                RecycleView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void RecycleView(){
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        adapter = new NoteAdapter( noteArrayList, MainActivity.this, notesClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onLongClick(Note note, CardView cardView) {
            Note selected = new Note();
            selected = note;
//            showPopUp(selected.getKey(),cardView);
        }
    };

    public void logOut(){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

//    public void updateData(Note note) {
//        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//        intent.putExtra("KEY", note.getKey());
//        startActivity(intent);
//    }

//    private void deleteData(String key) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_Modul9));
//        builder.setTitle("Delete");
//        builder.setMessage("Are you sure want to delete this note?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                database.child("Note").child(key).removeValue().addOnSuccessListener(succes ->{
//                    Toast.makeText(getApplicationContext(), "Delete succesfully!", Toast.LENGTH_SHORT).show();
//                    noteArrayList.clear();
//                    noteData();
//                }).addOnFailureListener(error ->{
//                    Toast.makeText(getApplicationContext(), "Can't delete this note", Toast.LENGTH_SHORT).show();
//                });
//            }
//        });
//
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), "Delete canceled", Toast.LENGTH_SHORT).show();
//                dialogInterface.dismiss();
//            }
//        });builder.show();

    }

//    private void showPopUp(String key,CardView cardView) {
//        PopupMenu popupMenu = new PopupMenu(this, cardView);
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.edit_note:
//                        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//                        intent.putExtra("key", key);
//                        startActivity(intent);
//                        return true;
//                    case R.id.delete_note:
//                        deleteData(key);
//                        return true;
//                }
//                return false;
//            }
//        });
//        popupMenu.inflate(R.menu.menu);
//        popupMenu.show();
//    }

