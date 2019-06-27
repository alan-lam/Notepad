package com.example.notepad;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class AllNotes extends AppCompatActivity {

    final ArrayList<Note> notesArrayList = new ArrayList<>();
    NotesAdapter notesAdapter;
    ListView listView;
    SharedPreferences notesSharedPreferences;
    SharedPreferences passwordSharedPreferences;
    PasswordDialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_notes);

        d = new PasswordDialog(AllNotes.this);

        notesSharedPreferences = getSharedPreferences("notes", MODE_PRIVATE);
        passwordSharedPreferences = getSharedPreferences("password", MODE_PRIVATE);

        notesAdapter = new NotesAdapter(this, notesArrayList);
        listView = findViewById(R.id.list);
        registerForContextMenu(listView);

        readFromGson();
        listView.setAdapter(notesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Note n = notesArrayList.get(position);
                if (n.isLocked()) {
                    d.setDialogResult(new PasswordDialog.DialogResult() {
                        @Override
                        public void getResult(String result) {
                            String key = passwordSharedPreferences.getString("password", "");
                            if (key.equals(result)) {
                                Intent i = new Intent(AllNotes.this, MainActivity.class);
                                i.putExtra("Note", n);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    d.show();
                }
                else {
                    Intent i = new Intent(AllNotes.this, MainActivity.class);
                    i.putExtra("Note", n);
                    startActivity(i);
                }
            }
        });
    }

    private void readFromGson() {
        Gson gson = new Gson();
        Map<String,?> keys = notesSharedPreferences.getAll();

        for (Map.Entry<String,?> entry : keys.entrySet()) {
            String json = entry.getValue().toString();
            Note n = gson.fromJson(json, Note.class);
            notesArrayList.add(n);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 1, "Lock");
        menu.add(0, v.getId(), 2, "Unlock");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Note n = notesArrayList.get(info.position);

        final SharedPreferences.Editor prefsEditor = notesSharedPreferences.edit();

        if (item.getTitle().equals("Delete")) {
            if (passwordSharedPreferences.getAll().size() > 0) {
                d.setDialogResult(new PasswordDialog.DialogResult() {
                    @Override
                    public void getResult(String result) {
                        String key = passwordSharedPreferences.getString("password", "");
                        if (key.equals(result)) {
                            prefsEditor.remove(n.getTitle());
                            prefsEditor.apply();

                            notesArrayList.remove(info.position);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                d.show();
            }
            else {
                prefsEditor.remove(n.getTitle());
                prefsEditor.apply();

                notesArrayList.remove(info.position);
            }
        }
        else if (item.getTitle().equals("Lock")) {
            if (passwordSharedPreferences.getAll().size() > 0) {
                d.setDialogResult(new PasswordDialog.DialogResult() {
                    @Override
                    public void getResult(String result) {
                        String key = passwordSharedPreferences.getString("password", "");
                        if (key.equals(result)) {
                            n.setLocked(true);

                            Gson gson = new Gson();
                            String json = gson.toJson(n);
                            prefsEditor.putString(n.getTitle(), json);
                            prefsEditor.apply();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                d.show();
            }
        }
        else {
            if (passwordSharedPreferences.getAll().size() > 0) {
                d.setDialogResult(new PasswordDialog.DialogResult() {
                    @Override
                    public void getResult(String result) {
                        String key = passwordSharedPreferences.getString("password", "");
                        if (key.equals(result)) {
                            n.setLocked(false);

                            Gson gson = new Gson();
                            String json = gson.toJson(n);
                            prefsEditor.putString(n.getTitle(), json);
                            prefsEditor.apply();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                d.show();
            }
        }
        listView.setAdapter(notesAdapter);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        /* if user has set password, then request password before performing actions */
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        else if (passwordSharedPreferences.getAll().size() > 0) {
            d.setDialogResult(new PasswordDialog.DialogResult() {
                @Override
                public void getResult(String result) {
                    String key = passwordSharedPreferences.getString("password", "");
                    if (key.equals(result)) {
                        if (item.getTitle().equals("Set Password")) {
                            Intent i = new Intent(AllNotes.this, ChangePassword.class);
                            startActivity(i);
                        }
                        /* TODO */
                        else if (item.getTitle().equals("Export to local storage")) {
                            if (isExternalStorageWritable() && isExternalStorageReadable()) {
                                /* ask user for storage write permission */
                                if (ContextCompat.checkSelfPermission(getApplicationContext()   , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(AllNotes.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                }
                                else {
                                    File dir = new File(Environment.getExternalStorageDirectory(), "Notepad");
                                    if (!dir.exists()) {
                                        dir.mkdirs();
                                    }
                                    File file = new File(dir, "notepad.txt");
                                    writeToFile(file);
                                }
                            }
                        }
                        /* TODO */
                        else if (item.getTitle().equals("Import from local storage")){
                            if (isExternalStorageReadable()) {
                                Toast.makeText(getApplicationContext(), "Can Read", Toast.LENGTH_SHORT).show();
                            }
                        }
                        /* TODO */
                        else {
                            // show instructions
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            d.show();
        }
        /* if user has not set password, then perform actions without checking password */
        else {
            if (item.getItemId() == android.R.id.home) {
                finish();
            }
            else if (item.getTitle().equals("Set Password")) {
                Intent i = new Intent(AllNotes.this, ChangePassword.class);
                startActivity(i);
            }
            else if (item.getTitle().equals("Export to local storage")) {
                if (isExternalStorageWritable() && isExternalStorageReadable()) {
                    /* ask user for storage write permission */
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    else {
                        File dir = new File(Environment.getExternalStorageDirectory(), "Notepad");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File file = new File(dir, "notepad.txt");
                        writeToFile(file);
                    }
                }
            }
            else if(item.getTitle().equals("Import from local storage")) {
                if (isExternalStorageReadable()) {
                    /* ask user for storage read permission */
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    }
                    else {
                        File f = new File(Environment.getExternalStorageDirectory(), "Notepad");
                        if (!f.exists()) {
                            Toast.makeText(getApplicationContext(), "File Doesn't Exist. See Instructions", Toast.LENGTH_SHORT).show();
                        }
                        /* TODO: read file */
                        else {
                        }
                    }
                }
            }
            /* TODO: show instructions */
            else {
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        d.dismiss(); // Fix Activity Window Leak
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void writeToFile(File file) {
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);

            Gson gson = new Gson();
            Map<String,?> keys = notesSharedPreferences.getAll();
            for (Map.Entry<String,?> entry : keys.entrySet()) {
                String json = entry.getValue().toString();
                Note n = gson.fromJson(json, Note.class);
                pw.println(n.getTitle());
                pw.println(n.getContent());
                pw.println("$--=--$");
                pw.flush();
            }
            pw.close();
            f.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
