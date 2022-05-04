package com.example.modul9.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modul9.NotesClickListener;
import com.example.modul9.R;
import com.example.modul9.dao.NoteDao;
import com.example.modul9.models.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<Note> notes = new ArrayList<>();
    private NoteDao noteDao;
    private Activity context;
    private NotesClickListener listener;
    public NoteAdapter(ArrayList<Note> notes, Activity context, NotesClickListener listener) {
        this.notes = notes;
        this.context = context;
        this.listener = listener;

    }

    public void setListNotes(ArrayList<Note> notes){
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(notes.get(holder.getAdapterPosition()), holder.cardView);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title, description;
        private Activity context;
        private NoteDao noteDao;
        private ImageView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.judul);
            description = itemView.findViewById(R.id.deskripsi);
            cardView = itemView.findViewById(R.id.card);

        }
    }
}
