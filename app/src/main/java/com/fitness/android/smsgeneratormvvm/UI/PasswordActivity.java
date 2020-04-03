package com.fitness.android.smsgeneratormvvm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitness.android.smsgeneratormvvm.R;

import java.util.prefs.Preferences;

public class PasswordActivity extends AppCompatActivity {

    private EditText pass;
    private Button next;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //initial
        pass = findViewById(R.id.pass);
        next = findViewById(R.id.next_btn);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        if (sharedPreferences.getBoolean("done", false))
        {
            Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = pass.getText().toString();
                if (password.isEmpty())
                {
                    Toast.makeText(PasswordActivity.this, "Please, Enter Your Password", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals("admin1"))
                {
                    Toast.makeText(PasswordActivity.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Intent intent = new Intent(PasswordActivity.this, MainActivity.class);

                    sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("done", true);
                    editor.apply();
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
