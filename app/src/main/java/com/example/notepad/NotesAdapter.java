package com.example.notepad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends ArrayAdapter<Note> {

    public NotesAdapter(Activity context, ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Note currentNote = getItem(position);

        TextView title = listItemView.findViewById(R.id.title);
        title.setText(currentNote.getTitle());

        if (currentNote.isLocked()) {
            ImageView lockImageView = listItemView.findViewById(R.id.lock_icon);
            lockImageView.setImageResource(R.drawable.ic_lock_black_24dp);
        }
        else {
            ImageView lockImageView = listItemView.findViewById(R.id.lock_icon);
            lockImageView.setImageDrawable(null);
        }

        return listItemView;
    }
}
