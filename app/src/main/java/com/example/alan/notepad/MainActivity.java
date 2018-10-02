package com.example.alan.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText content;
    EditText title;
    Intent allNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = findViewById(R.id.title);
                content = findViewById(R.id.content);
                String t = title.getText().toString() + ".txt";
                if (t.equals(".txt")) {
                    Toast.makeText(getApplicationContext(), "No Blank Title Please!", Toast
                            .LENGTH_SHORT)
                            .show();
                    return;
                }
                File extStore = Environment.getExternalStorageDirectory();
                String path = extStore.getAbsolutePath() + "/" + t;
                String data = content.getText().toString();
                try {
                    File myFile = new File(path);
                    myFile.createNewFile();
                    FileOutputStream out = new FileOutputStream(myFile);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
                    outputStreamWriter.append(data);
                    outputStreamWriter.close();
                    out.close();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {}
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.documents, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        allNotes = new Intent(MainActivity.this, AllNotes.class);
        startActivity(allNotes);
        return false;
    }
}
