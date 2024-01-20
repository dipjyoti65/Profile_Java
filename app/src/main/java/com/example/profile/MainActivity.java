package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView firstName, lastName, email;
    DBhelper databaseHelper;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve user data from Intent extras
        Intent intent = getIntent();
        UserData userData =(UserData) intent.getSerializableExtra("userData");

        if(userData != null){
            firstName = findViewById(R.id.firstNameTextView);
            lastName = findViewById(R.id.lastNameTextView);
            email = findViewById(R.id.emailTextView);

            firstName.setText(userData.getFirstName());
            lastName.setText(userData.getLastName());
            email.setText(userData.getEmail());
        }else{
            Toast.makeText(this, "User data not Available", Toast.LENGTH_SHORT).show();
        }

        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // databaseHelper.logoutUser();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.removeExtra("email");
                intent.removeExtra("password");
                startActivity(intent);
                finish();
            }
        });

    }
}