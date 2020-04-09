package com.example.androidtest;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY=999;
    EditText name,email,password;
    ImageView imageView;
    Button addimg;
    private static int RESULT_LOAD_IMAGE = 1;
    byte[] tempImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText)findViewById(R.id.t1);
        email=(EditText)findViewById(R.id.t2);
        password=(EditText)findViewById(R.id.t3);
        imageView=(ImageView)findViewById(R.id.registerimage);
        addimg= (Button)findViewById(R.id.image);


        TextView mTextViewRegister = (TextView) findViewById(R.id.textview_register);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(registerIntent);
            }
        });

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.registerimage);
            Bitmap mBitmap = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(mBitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            tempImage= imageInByte;

        }


    }



    public void  addUsers(View view ){
        DbManager db=new DbManager(this);

        if(tempImage == null){
            Toast.makeText(RegisterActivity.this,"" +
                    " Please Select an image",Toast.LENGTH_LONG).show();
        }else {
            if (db.checkDuplicateEmail(email.getText().toString())) {
                Toast.makeText(RegisterActivity.this,
                        "The email already exists please enter a new one", Toast.LENGTH_LONG).show();
            } else {
                String result = db.addUsers(name.getText().toString(), email.getText().toString(), password.getText().toString(), tempImage);
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                name.setText("");
                email.setText("");
                password.setText("");
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        }

    }


}
