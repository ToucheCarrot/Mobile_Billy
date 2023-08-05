package my.edu.utar.mad_individual;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_way extends AppCompatActivity {

    private TextView way1;
    private TextView way2;
    private TextView way3;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way);

        way1 = findViewById(R.id.tv_way1);
        way2 = findViewById(R.id.tv_way2);
        way3 = findViewById(R.id.tv_way3);
        back = findViewById(R.id.bt_back);

        way1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_way.this, activity_count.class);
                startActivity(intent);
            }
        });

        way2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_way.this, activity_count2.class);
                startActivity(intent);
            }
        });

        way3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_way.this, activity_count3.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the TextView is clicked, start the split activity here
                Intent intent = new Intent(activity_way.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}