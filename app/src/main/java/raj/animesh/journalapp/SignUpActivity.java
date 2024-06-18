package raj.animesh.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpActivity extends AppCompatActivity {

    private EditText username_create, password_create,email_Create;
    private Button acc_signup_btn;

    //firebase auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currerntUser;

    //firebase connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username_create = findViewById(R.id.username_create);
        password_create = findViewById(R.id.password_create);
        email_Create = findViewById(R.id.email_Create);
        acc_signup_btn = findViewById(R.id.acc_signup_btn);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //firebase auth
        authStateListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currerntUser = firebaseAuth.getCurrentUser();

                // check if the use is logged or not
                if (currerntUser != null){
                    //user already logged in

                }else{
                    // use not logged in yet/ or user signed out

                }
            }
        };

        acc_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(email_Create.getText().toString())
                && !TextUtils.isEmpty(password_create.getText().toString())
                &&!TextUtils.isEmpty(username_create.getText().toString())
                )
                {
                    String email = email_Create.getText().toString().trim();
                    String pass = password_create.getText().toString().trim();
                    String username = username_create.getText().toString().trim();

                    CreateUserEmailAccount(email,pass,username);

                    email_Create.setText("");
                    password_create.setText("");
                    username_create.setText("");
                    Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(i);
                    
                }else{
                    Toast.makeText(SignUpActivity.this, "No fields are allowed to be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void CreateUserEmailAccount( String email,String pass,String username){
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(pass)
                && !TextUtils.isEmpty(username))
        {
            firebaseAuth.createUserWithEmailAndPassword(
                    email,pass
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         // the user is created successfully
                         Toast.makeText(SignUpActivity.this, "The user is created successfully", Toast.LENGTH_SHORT).show();
                     }
                }
            });
        }
    }
}