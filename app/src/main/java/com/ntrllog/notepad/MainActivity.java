package com.ntrllog.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences notesSharedPreferences = getSharedPreferences("notes", MODE_PRIVATE);
        final EditText titleEditText = findViewById(R.id.title);
        final EditText contentEditText = findViewById(R.id.content);

        /* Load note from all notes list */
        final Intent i = getIntent();
        if (i.hasExtra("Note")) {
            Note n = i.getExtras().getParcelable("Note");
            titleEditText.setText(n.getTitle());
            contentEditText.setText(n.getContent());
        }

        FloatingActionButton fab = findViewById(R.id.save);
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_save_black_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();

                SharedPreferences.Editor prefsEditor = notesSharedPreferences.edit();

                final Note n;
                if (i.hasExtra("Note")) {
                    n = i.getExtras().getParcelable("Note");
                    if (!n.getTitle().equals(title)) {
                        prefsEditor.remove(n.getTitle());
                    }
                    n.setTitle(title);
                    n.setContent(content);
                }
                else {
                    n = new Note(title, content);
                }

                Gson gson = new Gson();
                String json = gson.toJson(n);
                prefsEditor.putString(n.getTitle(), json);
                prefsEditor.apply();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.all_notes) {
            Intent allNotesIntent = new Intent(MainActivity.this, AllNotes.class);
            startActivity(allNotesIntent);
        }
        return false;
    }
}
