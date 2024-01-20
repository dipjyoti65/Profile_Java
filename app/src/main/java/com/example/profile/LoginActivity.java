package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profile.databinding.ActivityLoginBinding;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    // It contain references  to the views defined in the activity_login.xml layout
    ActivityLoginBinding binding;
    DBhelper databaseHelper;
    TextView firstName, lastName, email;

    public static String UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the layout associated with LoginActivity --> Converting the XML layout file into actual View objects that can be displayed on the screen.
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        // returns the root View of the associated layout --> In this case it refers to the layout of LoginActivity
        setContentView(binding.getRoot());

        databaseHelper = new DBhelper(this);

        //Setting listener on Sign In button
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEmail = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                //checking if all fields are filled up by user not not
                if(UserEmail.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(UserEmail,password);

                    //Checking login credentials in database
                    if(checkCredentials){
                        Toast.makeText(LoginActivity.this, " Login Successfully ", Toast.LENGTH_SHORT).show();

                        UserData userData = databaseHelper.getUserData(UserEmail);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("userData",userData);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //If not User is not registered , redirected to Registration Page
        binding.backToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        clearInputFields();
    }
    private void clearInputFields(){
        EditText loginEmail = findViewById(R.id.loginEmail);
        EditText loginPassword = findViewById(R.id.loginPassword);

        loginEmail.setText("");
        loginPassword.setText("");
    }
}