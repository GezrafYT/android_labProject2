package com.example.test_guyshitrit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> fileLines = new ArrayList<>();
    private int currentLineIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load file data
        loadFileData();

        // Setting up the ListView and its items
        ListView listView = findViewById(R.id.listView);
        String[] items = {"Read File (Click)", "Item 2 (Inactive)", "Item 3", "Item 4 (Inactive)", "Item 5", "Item 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        TextView footerTextView = findViewById(R.id.footerTextView);

        // Set click listeners
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 1 || position == 3) {
                Toast.makeText(this, "This item is inactive!", Toast.LENGTH_SHORT).show();
            } else if (position == 0) {
                if (currentLineIndex < fileLines.size()) {
                    footerTextView.setText(fileLines.get(currentLineIndex));
                    currentLineIndex++;
                } else {
                    footerTextView.setText("End of file reached.");
                }
            } else {
                Toast.makeText(this, "Clicked: " + items[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFileData() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.sample_file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file.", Toast.LENGTH_SHORT).show();
        }
    }
}
