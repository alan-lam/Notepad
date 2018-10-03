package com.example.alan.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class AllNotes extends AppCompatActivity {

    String path = Environment.getExternalStorageDirectory().toString()+"/Notes";
    ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        addToNotesArray();

        NotesAdapter adapter = new NotesAdapter(this, notes);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Note note = notes.get(position);
                Intent i = new Intent(AllNotes.this, MainActivity.class);
                i.putExtra("Note", note);
                startActivity(i);
            }
        });
    }

    public void addToNotesArray() {
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(files[i]));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
                Note note = new Note(files[i].getName(), text.toString());
                notes.add(note);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
