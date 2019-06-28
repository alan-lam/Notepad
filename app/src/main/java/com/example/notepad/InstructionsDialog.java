package com.example.notepad;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

public class InstructionsDialog extends Dialog {

    InstructionsDialog(Activity a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
    }
}
