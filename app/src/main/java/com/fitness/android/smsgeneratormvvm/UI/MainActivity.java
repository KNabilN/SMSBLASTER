package com.fitness.android.smsgeneratormvvm.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fitness.android.smsgeneratormvvm.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText pre, num, inc;
    public static List<String> numbers;
    private SharedPreferences sharedPreferences;
    private int trials = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initial the UI components
        pre = findViewById(R.id.pre);
        num = findViewById(R.id.num);
        inc = findViewById(R.id.inc);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        trials = sharedPreferences.getInt("Trial", 0);

        if (trials >= 500)
        {
            finish();
        }
        else
        {
            Toast.makeText(this, "You still have " + (2 - trials) + " Trials", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Trial", trials + 1);
        editor.apply();

    }

    public void Next(View view)
    {
        // onClick method to generate numbers
        increaseNum();
    }

    private void increaseNum() {
        // get Data and check
        String preStr = pre.getText().toString();
        String number = num.getText().toString();
        String increase = inc.getText().toString();
        if (preStr.isEmpty() || number.isEmpty() || increase.isEmpty())
        {
            Toast.makeText(this, "Complete required data", Toast.LENGTH_SHORT).show();
        }
        else if (number.startsWith("0") ||number.startsWith("+"))
        {
            Toast.makeText(this, "Please don't put prefixes in Numbers field", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // generate the numbers and store them
            numbers = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(increase); i++)
            {
                Long numN = i + Long.parseLong(number);
                numbers.add(preStr + numN);
            }
            Intent intent = new Intent(MainActivity.this, SMSender.class);
            startActivity(intent);


        }

    }

//    public void changePass(View view) {
//        Intent intent = new Intent(MainActivity.this, PassActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
