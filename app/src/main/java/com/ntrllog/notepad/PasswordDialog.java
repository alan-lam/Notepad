package com.ntrllog.notepad;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PasswordDialog extends Dialog implements android.view.View.OnClickListener {

    private DialogResult mDialogResult;

    PasswordDialog(Activity a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_dialog);

        final EditText passwordEditText = findViewById(R.id.dialog_password);

        Button b = findViewById(R.id.dialog_enter);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordEditText.getText().toString();
                mDialogResult.getResult(password);
                passwordEditText.setText(""); // Clear text field when wrong password is entered
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
    }

    void setDialogResult(DialogResult dialogResult) {
        mDialogResult = dialogResult;
    }

    public interface DialogResult {
        void getResult(String result);
    }

}
