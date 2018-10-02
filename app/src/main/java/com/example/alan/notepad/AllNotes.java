package com.example.alan.notepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AllNotes extends AppCompatActivity {

    /*String path = Environment.getExternalStorageDirectory().toString()+"/Notes";
    ArrayList<String> titles  = new ArrayList<>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        /*File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            titles.add(files[i].getName());
        }*/

        /*final ArrayList<Note> notes = new ArrayList<>();
        NotesAdapter adapter = new NotesAdapter(this, notes);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);*/
    }
}
