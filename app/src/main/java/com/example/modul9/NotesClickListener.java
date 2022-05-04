package com.example.modul9;

import androidx.cardview.widget.CardView;

import com.example.modul9.models.Note;

public interface NotesClickListener {
    void onLongClick(Note note, CardView cardView);
}
