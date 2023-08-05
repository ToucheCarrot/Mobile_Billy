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

public class activity_count3 extends AppCompatActivity {

    EditText total;
    AutoCompleteTextView name;
    EditText percent;
    EditText num;
    CheckBox checkBox;
    Button add;
    Button back;
    Button cal;

    List<Person> persons;

    int ppl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count3);

        total = findViewById(R.id.ttl_3);
        name = findViewById(R.id.members_3);
        num = findViewById(R.id.number_3);
        percent = findViewById(R.id.percentage);
        add = findViewById(R.id.add);
        back = findViewById(R.id.bt_back);
        cal = findViewById(R.id.bt_calculate);
        checkBox = findViewById(R.id.checkBox);

        persons = new ArrayList<>();

        // Retrieve the stored student names from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("student_data", MODE_PRIVATE);
        Set<String> studentNamesSet = sharedPreferences.getStringSet("student_names", new HashSet<>());
        List<String> studentNamesList = new ArrayList<>(studentNamesSet);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, studentNamesList);
        name.setAdapter(adapter);
        name.setThreshold(1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String totalAmountStr = total.getText().toString();
                double totalAmount = Double.parseDouble(totalAmountStr);
                String nameStr = name.getText().toString();
                String percentageStr = percent.getText().toString();

                if (!nameStr.isEmpty() && !percentageStr.isEmpty())
                {
                    int percentage = Integer.parseInt(percentageStr);
                    boolean shouldStoreName = checkBox.isChecked();

                    // Calculate the item amount for the person based on the percentage
                    double CalItemAmount = (totalAmount * percentage) / 100;

                    persons.add(new Person(nameStr, percentage, CalItemAmount));

                    if (shouldStoreName) {
                        // Store the name in SharedPreferences (same as previous implementation)
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        studentNamesSet.add(nameStr);
                        editor.putStringSet("student_names", studentNamesSet);
                        editor.apply();
                    }

                    // Clear the EditText fields for the next person
                    name.setText("");
                    percent.setText("");

                    // Increment the number of people
                    ppl++;
                    num.setText(String.valueOf(ppl));

                    String message = nameStr + " (" + percentage + "%)" + ": RM" + CalItemAmount;
                    showCustomToast(message);
                }
                else {
                    showCustomToast("Please enter name and percentage");
                }
            }
        });

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String totalAmountStr = total.getText().toString();
                String numOfPpl = num.getText().toString();
                double totalAmount = Double.parseDouble(totalAmountStr);
                int numOfPeople = Integer.parseInt(numOfPpl);
                int totalPercentage = 0;

                for (Person item : persons) {
                    totalPercentage += item.getPercentage();
                }

                if (totalPercentage != 100)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity_count3.this);
                    builder.setTitle("Warning");
                    builder.setMessage("Total percentage must be 100%");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Clear the EditText fields for the next item
                            name.setText("");
                            percent.setText("");
                            num.setText("");

                            ppl = 0;
                            num.setText(String.valueOf(ppl));

                            // Clear the previous items in the ArrayList
                            persons.clear();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return;
                }

                // Calculate and display the result
                Intent intent = new Intent(activity_count3.this, activity_result3.class);
                intent.putExtra("total_amount", totalAmount);
                intent.putExtra("num_of_people", numOfPeople);
                intent.putParcelableArrayListExtra("persons", new ArrayList<>(persons));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_count3.this, activity_way.class);
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