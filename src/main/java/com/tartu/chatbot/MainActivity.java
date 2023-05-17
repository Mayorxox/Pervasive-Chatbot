package com.tartu.chatbot;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the MainActivity
        setContentView(R.layout.activity_main);

        // If you want to perform any operations on the UI elements,
        // get the references to those elements here, e.g.,:
        // EditText inputField = findViewById(R.id.input_field);
    }
}
