package com.example.securevideostreamer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class phonenumberInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phonenumber_interface);

        // Find the button and set an OnClickListener
        Button navigateButton = findViewById(R.id.nextpagebutton);
        EditText checkingNumber = findViewById(R.id.phoneno);

        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = checkingNumber.getText().toString();
                // Create an Intent to start NextActivity
                if (temp.length() == 10 && (temp.startsWith("7") || temp.startsWith("8") || temp.startsWith("9"))) {
                    startActivity(new Intent(getApplicationContext(), checking.class).putExtra("phoneno", "+91"+temp)); // temp contain india phone number = 9232423423 it don't contain +91 before number ok
                } else {
                    checkingNumber.setError("Invalid Number");
                    Toast.makeText(phonenumberInterface.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}