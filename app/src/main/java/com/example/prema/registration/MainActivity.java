package com.example.prema.registration;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button LogInButton, RegisterButton;
    EditText Email, Password;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DBHelper dbHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND";
    public static final String UserEmail = "";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = (Button) findViewById(R.id.buttonLogin);

        RegisterButton = (Button) findViewById(R.id.buttonRegister);

        Email = (EditText) findViewById(R.id.editEmail);
        Password = (EditText) findViewById(R.id.editPassword);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();

                //Removing MainActivity[Login Screen] from the stack for preventing back button press.
            }

        });
        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    // Login function starts from here.
    public void LoginFunction() {
        String pwd = Password.getText().toString();
        char ch,c;
        for(int i = 0;i<pwd.length();i++){
            ch = pwd.charAt(i);
            char x = (char)(ch - 5);
            pwd.replace(ch,x);
        }

        if (EditTextEmptyHolder) {
            // Opening SQLite database write permission.
            cursor = db.rawQuery("SELECT *FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.Table_Column_2_Email + "=? AND " + dbHelper.Table_Column_3_Password + "=?", new String[]{Email.getText().toString(), pwd});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                    String _fname = cursor.getString(cursor.getColumnIndex(dbHelper.Table_Column_1_Name));
                    String _email = cursor.getString(cursor.getColumnIndex(dbHelper.Table_Column_2_Email));
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("fullname", _fname);
                    intent.putExtra("email", _email);
                    startActivity(intent);

                }
            }
        } else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(MainActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();

        }
    }

    // Checking EditText is empty or not.

    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult() {

        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {

            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, EmailHolder);

            startActivity(intent);


        } else {

            Toast.makeText(MainActivity.this, "UserName or Password is Wrong, Please Try Again.", Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND";

    }
}

