package com.example.androidtest.ui.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidtest.ChnagePassword;
import com.example.androidtest.DbManager;
import com.example.androidtest.LoginActivity;
import com.example.androidtest.R;
import com.example.androidtest.SessionManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.NOTIFICATION_SERVICE;

public class DashboardFragment extends Fragment {
    Button logoutButton,updateButton,changePassword;
    ImageView imageView;
    SessionManager sessionManager;
    EditText name,password;
    TextView email;
    DbManager db;
    byte[] tempImage;
    private DashboardViewModel dashboardViewModel;
    public final String CHANNEL_ID ="002";
    final int REQUEST_CODE_GALLERY=999;
    private static int RESULT_LOAD_IMAGE = 1;
    View rootReplica;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rootReplica=root;
        changePassword=root.findViewById(R.id.change_password_btn);
        name=root.findViewById(R.id.NameEditText);
        password=root.findViewById(R.id.PasswordEditText);
        email=root.findViewById(R.id.EmailEditText);
        imageView=root.findViewById(R.id.profile_imageView);
        updateButton=root.findViewById(R.id.update_button);
        logoutButton = root.findViewById(R.id.logout_button);
        sessionManager =new SessionManager(getActivity().getApplicationContext());
        final String  username = sessionManager.getUsername();
        db = new DbManager(getActivity());

//        code to logout and add a notification ****************************************************


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure to Log out?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.setLogin(false);
                        sessionManager.setUsername("");
                        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                       getActivity().finish();

                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                        {
                            NotificationChannel notificationChannel = new NotificationChannel(
                                    CHANNEL_ID,"001", NotificationManager.IMPORTANCE_DEFAULT);
                            notificationChannel.setDescription("Notification Desc");
                            NotificationManager notificationManager =(NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.createNotificationChannel(notificationChannel);

                            Notification.Builder builder = new Notification.Builder(getActivity(),CHANNEL_ID);
                            builder.setContentText("You hav logged out ")
                                    .setSmallIcon(R.drawable.ic_remove_red_eye_black_24dp)
                                    .setContentTitle("SJTESt")
                                    .setPriority(Notification.PRIORITY_DEFAULT);

                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
                            notificationManagerCompat.notify(001,builder.build());
                            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));

                        }else {
                            String message = "You Have logged out ";
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                    getActivity()
                            )
                                    .setSmallIcon(R.drawable.ic_remove_red_eye_black_24dp)
                                    .setContentTitle("New Notification")
                                    .setContentText(message)
                                    .setAutoCancel(true);
                            Intent LoginIntent = new Intent(getActivity(), LoginActivity.class);
                            LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            LoginIntent.putExtra("message", message);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                                    0, LoginIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(
                                    NOTIFICATION_SERVICE
                            );
                            notificationManager.notify(0, builder.build());
                            startActivity(LoginIntent);

                        }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


//        code to update images*****************************************************
        final ArrayList<String> queryResult = db.getUserData(username);
        name.setText(queryResult.get(0).toString());
        email.setText(queryResult.get(1).toString());
        password.setText(queryResult.get(2).toString());
        imageView.setImageBitmap(db.getimage(username));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  updatedName=name.getText().toString();
                String  updatedpassword=password.getText().toString();
                byte[]  updatedImage=tempImage;
                String  emailReplica = email.getText().toString();
                //Toast.makeText(getActivity().getApplicationContext(),"username:"+updatedName,Toast.LENGTH_SHORT).show();
                db.updateUser(updatedName,emailReplica,updatedImage);

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChangePasswordIntent = new Intent(getActivity(), ChnagePassword.class);
                startActivity(ChangePasswordIntent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(),new
                        String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = rootReplica.findViewById(R.id.profile_imageView);
            Bitmap mBitmap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(mBitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            tempImage= imageInByte;

        }


    }

}
