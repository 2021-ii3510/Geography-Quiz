package fr.isep.ii3510.geographyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        listView = findViewById(R.id.listView);
        displayListViewContent();
    }

    private void displayListViewContent() {
        // 1. We create the contents of the ArrayAdapter
        SharedPreferences prefs = getSharedPreferences("leaderboard", MODE_PRIVATE);
        Map<String, ?> entries = prefs.getAll();
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, ?> entry: entries.entrySet()) {
            // France: 1
            String value = entry.getKey() + ": " + entry.getValue();
            values.add(value);
        }

        // 2. We instantiate the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                values);

        // 3. We set the Adapter for the ListView
        listView.setAdapter(adapter);
    }
}