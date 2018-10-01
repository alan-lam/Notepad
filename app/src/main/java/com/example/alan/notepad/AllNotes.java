package com.example.alan.notepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class AllNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        final ArrayList<Notes> notes = new ArrayList<>();
        NotesAdapter adapter = new NotesAdapter(this, notes);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
