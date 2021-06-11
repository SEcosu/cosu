package com.example.cosu_pra;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.DTO.User;
import com.example.cosu_pra.Main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/*
SharedPreference에 저장된 내용
        Name : 유저 이름
        Password : 유저 비밀번호
        Email : 유저 이메일
        Nickname : 유저 닉네임
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";


    EditText editTextSignUpEmail;
    EditText editTextSignUpPassword, editTextSignUpPasswordConfirm;
    EditText editTextSignUpNickname;
    EditText editTextSignUpName;


    Button buttonSignup;
    ImageView buttonBack;
    TextView textviewMessage;
    private TextView yourAccount, create;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;

    SharedPreferences sh_Pref;
    SharedPreferences.Editor toEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        fStore = FirebaseFirestore.getInstance();

        /* ********if already logged in,finish this job********* */
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LogoutActivity.class));

        }
        //initializing views
        editTextSignUpName = (EditText) findViewById(R.id.editTextSignUpName);
        ;
        editTextSignUpNickname = (EditText) findViewById(R.id.editTextSignUpNickname);
        editTextSignUpEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
        editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);
        editTextSignUpPasswordConfirm = (EditText) findViewById(R.id.editTextSignUpPasswordConfirm);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        create = (TextView) findViewById(R.id.create);
        yourAccount = (TextView) findViewById(R.id.yourAccount);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        create.setText("Create");
        yourAccount.setText(" your Account");
        buttonSignup.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }


    //Register(Sign up)
    private void registerUser() {
        //Get the email & password that the user enters.
        String email = editTextSignUpEmail.getText().toString().trim();
        String password = editTextSignUpPassword.getText().toString().trim();
        String confirmPassword = editTextSignUpPasswordConfirm.getText().toString().trim();
        String name = editTextSignUpName.getText().toString();
        String nickName = editTextSignUpNickname.getText().toString();

        //Check whether the email and password are empty or not.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please input Email.", Toast.LENGTH_SHORT).show();
            editTextSignUpEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please input Password.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please input Confirm Password.", Toast.LENGTH_SHORT).show();
            editTextSignUpPasswordConfirm.requestFocus();
            return;
        }
        //Check whether email and password format is correct
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Not in email format.", Toast.LENGTH_SHORT).show();
            editTextSignUpEmail.requestFocus();
            return;
        }
        if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
            Toast.makeText(this, "Please keep the password format.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.requestFocus();
            return;
        }
        if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", confirmPassword)) {
            Toast.makeText(this, "Please keep the confirm password format.", Toast.LENGTH_SHORT).show();
            editTextSignUpPasswordConfirm.requestFocus();
            return;
        }

        //Check whether password equals password confirmed
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Please check password again.", Toast.LENGTH_SHORT).show();
            editTextSignUpPassword.setText("");
            editTextSignUpPasswordConfirm.setText("");
            editTextSignUpPassword.requestFocus();
            return;
        }

        //If the email and password are entered correctly, continue.
        progressDialog.setMessage("Registering. Please wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            User user = new User(email, name, nickName);
                            fStore.collection("users").add(user);

                            //Input user data is stored in SharedPreference
                            sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);
                            toEdit = sh_Pref.edit();
                            toEdit.putString("Name", name);
                            toEdit.commit();
                            toEdit.putString("Password", password);
                            toEdit.commit();
                            toEdit.putString("Email", email);
                            toEdit.commit();
                            toEdit.putString("Nickname", nickName);
                            toEdit.commit();


                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            //If error occured
                            textviewMessage.setText("Error type\n - Email already registered\n -Password at least 6 digits \n - Server error");
                            Toast.makeText(SignupActivity.this, "Register error", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    /* *******Register Button ******** */
    @Override
    public void onClick(View view) {
        if (view == buttonSignup) {
            registerUser();

        }

        /* *******go to Login page ******** */
        if (view == buttonBack) {
            finish();
        }

    }
}