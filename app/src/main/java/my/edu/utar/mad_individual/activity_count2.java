package my.edu.utar.mad_individual;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class activity_count2 extends AppCompatActivity {

    EditText total;
    AutoCompleteTextView member;
    EditText num;
    CheckBox checkBox;
    EditText price;
    Button add;
    Button back;
    Button cal;

    ArrayList<bill_item> billItems;

    int ppl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count2);

        total = findViewById(R.id.ttl_2);
        member = findViewById(R.id.members_2);
        num = findViewById(R.id.number_2);
        price = findViewById(R.id.amount);
        add = findViewById(R.id.add);
        back = findViewById(R.id.bt_back);
        cal = findViewById(R.id.bt_calculate);
        checkBox = findViewById(R.id.checkBox);

        billItems = new ArrayList<>();

        // Retrieve the stored student names from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("student_data", MODE_PRIVATE);
        Set<String> studentNamesSet = sharedPreferences.getStringSet("student_names", new HashSet<>());
        List<String> studentNamesList = new ArrayList<>(studentNamesSet);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, studentNamesList);
        member.setAdapter(adapter);
        member.setThreshold(1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the name and item price from the EditText fields
                String name = member.getText().toString();
                String itemPrice = price.getText().toString();
                boolean shouldStoreName = checkBox.isChecked();

                // Check if both name and item price are not empty
                if (!name.isEmpty() && !itemPrice.isEmpty()) {
                    double itemAmount = Double.parseDouble(itemPrice);
                    billItems.add(new bill_item(name, itemAmount));

                    if (shouldStoreName) {
                        // Store the name in SharedPreferences (same as previous implementation)
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        studentNamesSet.add(name);
                        editor.putStringSet("student_names", studentNamesSet);
                        editor.apply();
                    }

                    // Clear the EditText fields for the next item
                    member.setText("");
                    price.setText("");

                    // Increment ppl when a valid entry is made
                    ppl++;
                    num.setText(String.valueOf(ppl));

                    String message = name + ", RM" + itemAmount;
                    showCustomToast(message);

                } else {
                    // Show an error message if either name or item price is empty
                    showCustomToast("Please enter a valid name and amount");
                }
            }
        });

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String totalAmount = total.getText().toString();
                String numOfPeople = num.getText().toString();
                int numOfPpl = Integer.parseInt(numOfPeople);

                // Check if the total amount matches the sum of individual item prices
                double totalItemPrice = 0.00;
                for (bill_item item : billItems) {
                    totalItemPrice += item.getItemPrice();
                }

                double itemTotal = totalItemPrice;

                double tolerance = 0.0001;

                double totalAmountDouble = Double.parseDouble(totalAmount);

                if ((Math.abs(itemTotal - totalAmountDouble)) > tolerance)
                {
                    // Display AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity_count2.this);
                    builder.setTitle("Warning");
                    builder.setMessage("The total price of items does not match the total amount. Please check again.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Clear the EditText fields for the next item
                            member.setText("");
                            price.setText("");
                            num.setText("");

                            ppl = 0;
                            num.setText(String.valueOf(ppl));

                            // Clear the previous items in the ArrayList
                            billItems.clear();

                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return; // Exit the onClick method
                }

                // The total price matches, proceed to start the ResultActivity
                Intent intent = new Intent(activity_count2.this, activity_result2.class);
                intent.putExtra("total_amount", totalAmountDouble);
                intent.putExtra("num_of_people", numOfPpl);
                intent.putExtra("bill_items", billItems);
                startActivity(intent);

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_count2.this, activity_way.class);
                startActivity(intent);
            }
        });

    }

    private void showCustomToast(String message) {
        // Inflate the custom Toast layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_edit, findViewById(R.id.toast_text));


        // Set the text in the TextView of the custom Toast
        TextView text = layout.findViewById(R.id.tv_toast);
        text.setText(message);

        // Create and display the custom Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
}