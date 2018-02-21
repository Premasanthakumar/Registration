package com.example.prema.registration;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText Email, Password, Name ;
    Button Register,back;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    DBHelper dbHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Register = (Button)findViewById(R.id.buttonRegister);
        back = (Button)findViewById(R.id.back);
        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        Name = (EditText)findViewById(R.id.editName);

        dbHelper= new DBHelper(this);
        db     =  dbHelper.getWritableDatabase();
        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus();
                InsertDataIntoSQLiteDatabase();
                EmptyEditTextAfterDataInsert();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }


    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        String pwd = Password.getText().toString();
        char ch,c;
        for(int i = 0;i<pwd.length();i++){
            ch = pwd.charAt(i);
            c = ch;
            char x = (char)(c + 5);
            pwd.replace(ch,x);
        }
        if(EditTextEmptyHolder) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", Name.getText().toString());
            contentValues.put("email", Email.getText().toString());
            contentValues.put("password", pwd);
            long value = db.insert("UserTable", null, contentValues);
            if (value > 0) {// Printing toast message after done inserting.
                Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(RegisterActivity.this,"insert failed",Toast.LENGTH_LONG).show();

            }
        }
        else {
            Toast.makeText(RegisterActivity.this,"Edit text is empty",Toast.LENGTH_LONG).show();

        }
    }
    // This block will execute if any of the registration EditText is empty.


    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();

        Email.getText().clear();

        Password.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }



}

