package my.edu.utar.mad_individual;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class activity_count extends AppCompatActivity {

    EditText total;
    AutoCompleteTextView member;
    EditText num;
    CheckBox checkBox;
    Button add;
    Button back;
    Button cal;

    ArrayList<Name> nameItem;
    List<String> people;

    int ppl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        total = findViewById(R.id.ttl_1);
        member = findViewById(R.id.members_1);
        num = findViewById(R.id.number_1);
        add = findViewById(R.id.add);
        back = findViewById(R.id.bt_back);
        cal = findViewById(R.id.bt_calculate);
        checkBox = findViewById(R.id.checkBox);

        nameItem = new ArrayList<>();
        people = new ArrayList<>();

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
                // Get the name from the member EditText
                String name = member.getText().toString();
                boolean shouldStoreName = checkBox.isChecked();

                // Show a Toast to indicate the name is added
                if (!name.isEmpty()) {
                    people.add(name);

                    if (shouldStoreName) {
                        // Store the name in SharedPreferences (same as previous implementation)
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        studentNamesSet.add(name);
                        editor.putStringSet("student_names", studentNamesSet);
                        editor.apply();
                    }

                    member.setText("");

                    ppl++;
                    num.setText(String.valueOf(ppl));

                    String message = name + " is added";
                    showCustomToast(message);
                }
                else
                {
                    showCustomToast("Please add at least one name.");
                }
            }
        });

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalAmount = total.getText().toString();
                String numOfPeople = num.getText().toString();
                String name = "";


                if (!totalAmount.isEmpty() && !numOfPeople.isEmpty()) {
                    double ttlAmount = Double.parseDouble(totalAmount);
                    int numOfPpl = Integer.parseInt(numOfPeople);

                    double amountPerPerson = ttlAmount / numOfPpl;

                    // Format the amount to have 2 decimal places
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String formattedAmount = decimalFormat.format(amountPerPerson);
                    double amountDp = Double.parseDouble(formattedAmount);

                    totalAmount = decimalFormat.format(ttlAmount);
                    double ttl = Double.parseDouble(totalAmount);

                    for (String person : people) {
                        nameItem.add(new Name(person, amountDp));
                    }

                    // Create an intent to start the ResultActivity
                    Intent intent = new Intent(activity_count.this, activity_result.class);
                    intent.putExtra("total_amount", ttl);
                    intent.putExtra("num_of_people", numOfPpl);
                    intent.putExtra("name_item", nameItem);

                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_count.this, activity_way.class);
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