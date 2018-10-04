package com.example.alan.notepad;

import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter<Note> {

    ArrayList<Note> list;

    public NotesAdapter(Activity context, ArrayList<Note> notes) {
        super(context, 0, notes);
        this.list = notes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Note currentNote = getItem(position);

        TextView title = listItemView.findViewById (R.id.title);
        title.setText (currentNote.getTitle());

        ImageButton deleteButton = listItemView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().toString()+"/Notes/"+list.get(position).getTitle();
                File file = new File(path);
                file.delete();
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }
}
