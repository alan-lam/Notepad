package com.ntrllog.notepad;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        final EditText passwordEditText = findViewById(R.id.dialog_new_password);
        Button enter = findViewById(R.id.dialog_change_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                if (password.length() > 0) {
                    SharedPreferences passwordSharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = passwordSharedPreferences.edit();
                    prefsEditor.putString("password", password);
                    prefsEditor.apply();
                    Toast.makeText(getApplicationContext(), "Password Set!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot Have Empty Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
