package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profile.databinding.ActivityLoginBinding;
import com.example.profile.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    // It contain references  to the views defined in the activity_register.xml layout
    ActivityRegisterBinding binding;
    DBhelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the layout associated with RegisterActivity --> Converting the XML layout file into actual View objects that can be displayed on the screen.
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        // returns the root View of the associated layout --> In this case it refers to the layout of RegisterActivity
        setContentView(binding.getRoot());

        databaseHelper = new DBhelper(this);

        // Setting listener on Register Button
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.registerEmail.getText().toString();
                String password = binding.registerPassword.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();
                String firstName = binding.firstName.getText().toString();
                String lastName = binding.lastName.getText().toString();

                if(email.equals("")||password.equals("")||confirmPassword.equals("")||firstName.equals("")||lastName.equals(""))
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if(!checkUserEmail){
                            Boolean insert = databaseHelper.insertData(email,password,firstName,lastName);

                            if(insert){
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "User email already exists , Please login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Setting Listener to go back to login page from Register page
        binding.backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}