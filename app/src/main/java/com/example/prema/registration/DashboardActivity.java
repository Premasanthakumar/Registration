package com.example.prema.registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    String EmailHolder;
    TextView Email;
    Button LogOUT ;
    private TextView name;
    private String NameHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        name = (TextView)findViewById(R.id.textView1);
        Email = (TextView)findViewById(R.id.textView2);

        LogOUT = (Button)findViewById(R.id.button1);

        Intent intent = getIntent();


        // Receiving User Email Send By MainActivity.
        NameHolder = intent.getStringExtra("fullname");
        EmailHolder = intent.getStringExtra("email");

        // Setting up received email to TextView.
        Email.setText("Email :"+ EmailHolder);
        name.setText("name :"+NameHolder);


        // Adding click listener to Log Out button.
        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.
                finish();

                Toast.makeText(DashboardActivity.this,"Log Out Successfull", Toast.LENGTH_LONG).show();

            }
        });

    }
}
