//package com.fitness.android.smsgeneratormvvm.UI;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.fitness.android.smsgeneratormvvm.R;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class PassActivity extends AppCompatActivity {
//
//    private EditText n, nn, l;
//    private String f, s, p;
//    private SharedPreferences sharedPreferences;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pass);
//
//        l= findViewById(R.id.prev);
//        n = findViewById(R.id.new_first);
//        nn = findViewById(R.id.new_sec);
//
//        sharedPreferences = getPreferences(MODE_PRIVATE);
//
//    }
//
//    public void confirm(View view) {
//        p = l.getText().toString().trim();
//        f = n.getText().toString().trim();
//        s = nn.getText().toString().trim();
//
//        if (!p.equals(sharedPreferences.getString("pass", "admin1")))
//        {
//            Toast.makeText(this, "Wrong previous password!!", Toast.LENGTH_SHORT).show();
//        }
//        else if (!f.equals(s))
//        {
//            Toast.makeText(this, "Password is not identical", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("pass", f);
//            editor.putBoolean("done", false);
//            editor.apply();
//            Intent intent = new Intent(PassActivity.this, PasswordActivity.class);
//
//            intent.putExtra("done", false);
//
//            startActivity(intent);
//            finish();
//        }
//
//
//    }
//}
