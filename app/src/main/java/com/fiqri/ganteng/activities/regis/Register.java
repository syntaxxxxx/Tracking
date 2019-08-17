package com.fiqri.ganteng.activities.regis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fiqri.ganteng.R;
import com.fiqri.ganteng.activities.login.Login;
import com.fiqri.ganteng.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register extends AppCompatActivity {

    @BindView(R.id.edt_username_register)
    EditText edtUsernameRegister;
    @BindView(R.id.edt_email_register)
    EditText edtEmailRegister;
    @BindView(R.id.edt_password_register)
    EditText edtPasswordRegister;
    @BindView(R.id.edt_con_password_register)
    EditText edtConPasswordRegister;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;

    FirebaseAuth mAuth;
    DatabaseReference myRef;

    String username, email, password, confir;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("user");
    }

    @OnClick({R.id.btn_sign_up, R.id.tv_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                authSignUp();
                break;
            case R.id.tv_sign_in:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void authSignUp() {

        username = edtUsernameRegister.getText().toString().trim();
        email = edtEmailRegister.getText().toString().trim();
        password = edtPasswordRegister.getText().toString().trim();
        confir = edtConPasswordRegister.getText().toString().trim();

        if (TextUtils.isEmpty(username) || (TextUtils.isEmpty(email)
                || (TextUtils.isEmpty(password)) || (TextUtils.isEmpty(confir)))) {
            Toast.makeText(this, getString(R.string.isEmptyField), Toast.LENGTH_SHORT).show();

        } else {
            fetchAuth();
        }
    }

    private void fetchAuth() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveData(user);
                            startActivity(new Intent(Register.this, Login.class));
                            Toast.makeText(Register.this,
                                    "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this,
                                    "Authentication failed.", Toast.LENGTH_SHORT).show();
                            saveData(null);
                        }
                    }
                });
    }

    private void saveData(FirebaseUser user) {
        // instance class User
        User user1 = new User();
        user1.setName(username);
        user1.setEmail(email);
        user1.setPassword(password);
        user1.setUuid(mAuth.getCurrentUser().getUid());

        myRef.child(user.getUid()).setValue(user1);
    }
}

