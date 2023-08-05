package my.edu.utar.mad_individual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class activity_history extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SliderAdapter adapter;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        back = findViewById(R.id.bt_back);

        List<HistoryItem> historyItems = new ArrayList<>();

        // Retrieve the history from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
        String historyData = sharedPreferences.getString("history_data", "");

        // Convert the JSON string back to the list of HistoryItem using Gson
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<HistoryItem>>(){}.getType();
        ArrayList<HistoryItem> savedHistoryItems = gson.fromJson(historyData, listType);

        historyItems.addAll(savedHistoryItems);

        adapter = new SliderAdapter(historyItems);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the TextView is clicked, start the split activity here
                Intent intent = new Intent(activity_history.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}