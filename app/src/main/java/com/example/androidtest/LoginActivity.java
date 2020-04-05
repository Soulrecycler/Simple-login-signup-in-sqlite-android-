package com.example.androidtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button  mLoginButton;
    TextView mTextViewRegister;
    DbManager dbManager;
    SessionManager sessionmanager;
    public final String CHANNEL_ID ="001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionmanager = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_login_user);

        mTextUsername =(EditText)findViewById(R.id.username);
        mTextPassword =(EditText)findViewById(R.id.password);
        mLoginButton =(Button) findViewById(R.id.LoginButton);
        mTextViewRegister =(TextView) findViewById(R.id.textview_register);
        dbManager = new DbManager(this);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mTextUsername.getText().toString();
                String password = mTextPassword.getText().toString();
                if(password.equals("")){
                    mTextPassword.setError("please enter password  ");
                }

                if(username.equals("")){
                    mTextPassword.setError("please enter username");
                }


                    if(dbManager.isLoginValid(username,password)){
                        sessionmanager.setLogin(true);
                        sessionmanager.setUsername(username);
                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                        {
                            NotificationChannel notificationChannel = new NotificationChannel(
                                    CHANNEL_ID,"001",NotificationManager.IMPORTANCE_DEFAULT);
                            notificationChannel.setDescription("Notification Desc");
                            NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.createNotificationChannel(notificationChannel);

                            Notification.Builder builder = new Notification.Builder(LoginActivity.this,CHANNEL_ID);
                            builder.setContentText("You hav logged in ")
                                    .setSmallIcon(R.drawable.ic_remove_red_eye_black_24dp)
                                    .setContentTitle("SJTESt")
                                    .setPriority(Notification.PRIORITY_DEFAULT);

                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(LoginActivity.this);
                            notificationManagerCompat.notify(001,builder.build());
                            startActivity(new Intent(getApplicationContext(),Home2Activity.class));
                            Toast.makeText(LoginActivity.this,"Login Sucessful",Toast.LENGTH_SHORT).show();

                        }else {
                                String message = "You Have logged in ";
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                        LoginActivity.this
                                )
                                        .setSmallIcon(R.drawable.ic_remove_red_eye_black_24dp)
                                        .setContentTitle("New Notification")
                                        .setContentText(message)
                                        .setAutoCancel(true);
                                Intent LoginIntent = new Intent(LoginActivity.this, Home2Activity.class);
                                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                LoginIntent.putExtra("message", message);
                                PendingIntent pendingIntent = PendingIntent.getActivity(LoginActivity.this,
                                        0, LoginIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                                NotificationManager notificationManager = (NotificationManager) getSystemService(
                                        Context.NOTIFICATION_SERVICE
                                );
                                notificationManager.notify(0, builder.build());
                                startActivity(LoginIntent);
                            Toast.makeText(LoginActivity.this,"Login Sucessful",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
