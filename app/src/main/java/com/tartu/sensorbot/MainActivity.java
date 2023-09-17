package com.tartu.sensorbot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tartu.sensorbot.chat.ChatbotCondition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button referenceButton = findViewById(R.id.referenceButton);
        Button pervasiveButton = findViewById(R.id.pervasiveButton);
        Intent intent = new Intent(this, ChatActivity.class);

        pervasiveButton.setOnClickListener(v -> intent.putExtra("condition", ChatbotCondition.pervasive));
        referenceButton.setOnClickListener(v -> intent.putExtra("condition", ChatbotCondition.reference));

        startActivity(intent);
    }
}
