package com.example.androidtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChnagePassword extends AppCompatActivity {
    SessionManager sessionManager;
    Button updatePassword;
    EditText passwordEditText;
    DbManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());
        final String  userid = sessionManager.getUsername();
        setContentView(R.layout.change_password);
        updatePassword=(Button)findViewById(R.id.change_pass_btn);
        passwordEditText=(EditText)findViewById(R.id.change_pass_edit_text);
        db=new DbManager(this);

        final ArrayList<String> queryResult = db.getUserData(userid);
        passwordEditText.setText(queryResult.get(2).toString());

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  updatedpassword=passwordEditText.getText().toString();
                String oldPassword=queryResult.get(2).toString();
                Toast.makeText(ChnagePassword.this,"Password :"+oldPassword, Toast.LENGTH_SHORT).show();
                if(updatedpassword.equals(oldPassword)){

                    Toast.makeText(ChnagePassword.this,"Your new password" +
                            " matches with your old one ", Toast.LENGTH_SHORT).show();
                }else{
                    db.updatePassword(userid,updatedpassword);
                    Toast.makeText(ChnagePassword.this,"Password Updated:", Toast.LENGTH_SHORT).show();
                }

        }
        });

    }
}
