package com.example.alan.notepad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alan.notepad.Notes;
import com.example.alan.notepad.R;

import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter<Notes>  {

    public NotesAdapter(Activity context, ArrayList<Notes> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Notes currentNote = getItem(position);

        TextView title = listItemView.findViewById(R.id.title);
        title.setText(currentNote.getTitle());

        TextView content = listItemView.findViewById(R.id.content);
        content.setText(currentNote.getContent());

        return listItemView;
    }
}
