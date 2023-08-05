package my.edu.utar.mad_individual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class activity_result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Retrieve the values from the Intent extras
        double totalAmount = getIntent().getDoubleExtra("total_amount", 0.00);
        int numOfPeople = getIntent().getIntExtra("num_of_people", 0);
        ArrayList<Name> nameItems = getIntent().getParcelableArrayListExtra("name_item");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String ttl = decimalFormat.format(totalAmount);

        double ttlAmount = Double.parseDouble(ttl);

        // Show the result in a pop-up dialog
        showResultDialog(ttlAmount, numOfPeople, nameItems);

    }

    private void showResultDialog(double ttlAmount, int numOfPeople, ArrayList<Name> nameItems) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.activity_result, null);

        TextView tvTitle = dialogView.findViewById(R.id.tv_title);
        TextView tvResult = dialogView.findViewById(R.id.tv_result);
        Button btnOk = dialogView.findViewById(R.id.btn_ok);
        Button btnShare = dialogView.findViewById(R.id.bt_share);

        tvTitle.setText("Split Bill Result");
        String resultMessage = "Date: " + getCurrentDate() +
                "\n\n\nTotal Amount: RM" + ttlAmount + "\n\n"
                + "Number of People: " + numOfPeople;
        tvResult.setText(resultMessage);

        for (Name item : nameItems) {
            tvResult.append("\n\n" + item.getItemName() + ": RM " + item.getItemPrice());
        }

        // Share button click listener
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a sharing intent
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                StringBuilder shareBody = new StringBuilder();
                shareBody.append("Split Bill Result\n\n\n");
                shareBody.append("Date: ").append(getCurrentDate()).append("\n\n");
                shareBody.append("Total Amount: RM").append(ttlAmount).append("\n\n");
                shareBody.append("Number of People: ").append(numOfPeople).append("\n\n");

                for (Name item : nameItems) {
                    shareBody.append("\n\n\n").append(item.getItemName()).append(": RM").append(item.getItemPrice());
                }
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody.toString());
                startActivity(Intent.createChooser(sharingIntent, "Split Bill Result"));
            }
        });
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHistory(ttlAmount, numOfPeople, nameItems);
                finish();
             //   finishAffinity();
            }
        });
    }

    private String getCurrentDate() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(calendar.getTime());
    }

    private void saveHistory(double ttlAmount, int numOfPeople, ArrayList<Name> nameItems) {
        SharedPreferences sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve the previous history data if any
        String previousHistory = sharedPreferences.getString("history_data", "");

        // Convert the new history data to JSON string using Gson
        Gson gson = new Gson();
        HistoryItem newHistoryItem = new HistoryItem(ttlAmount, numOfPeople, nameItems, new ArrayList<>(), new ArrayList<>(), getCurrentDate());

        ArrayList<HistoryItem> historyItems = new ArrayList<>();
        if (!previousHistory.isEmpty()) {
            Type listType = new TypeToken<ArrayList<HistoryItem>>(){}.getType();
            historyItems = gson.fromJson(previousHistory, listType);
        }
        historyItems.add(newHistoryItem);
        String newHistoryData = gson.toJson(historyItems);

        // Save the new history data in SharedPreferences
        editor.putString("history_data", newHistoryData);
        editor.apply();
    }
}