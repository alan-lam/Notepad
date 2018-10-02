package com.example.alan.notepad;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class AllNotes extends AppCompatActivity {

    String path = Environment.getExternalStorageDirectory().toString()+"/Notes";
    ArrayList<String> titles  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            titles.add(files[i].getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, titles);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
