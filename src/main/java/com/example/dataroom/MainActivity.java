package com.example.dataroom;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText titleInput, contentInput;
    Button saveButton, loadButton;
    TextView resultText;
    1 String TAG = "data";

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleInput = findViewById(R.id.titleInput);
        contentInput = findViewById(R.id.contentInput);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        resultText = findViewById(R.id.resultText);

        db = AppDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String content = contentInput.getText().toString();

            Note note = new Note(title, content);
            db.noteDao().insert(note);
            Log.i(TAG, "onCreate: ");
        });

        loadButton.setOnClickListener(v -> {
            List<Note> notes = db.noteDao().getAllNotes();
            StringBuilder sb = new StringBuilder();
            for (Note n : notes) {
                sb.append(n.id).append(": ").append(n.title).append(" - ").append(n.content).append("\n");
            }
            resultText.setText(sb.toString());
            Log.i(TAG, "load ");
        });
    }
}
