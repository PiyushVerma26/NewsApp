package hello.tech.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText regEmail,regName,regPassword,regRetype;
    Button register,google,phone;
    private DatabaseReference refer;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regEmail=findViewById(R.id.regEmail);
        regName=findViewById(R.id.regName);
        register=findViewById(R.id.Register);
        regPassword=findViewById(R.id.regPassword);
        regRetype=findViewById(R.id.regRetype);
        google=findViewById(R.id.regGoogle);
        phone=findViewById(R.id.regPhone);
        refer= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();


        register.setOnClickListener(v -> {
            if (regName.getText().toString().trim().isEmpty()||regEmail.getText().toString().trim().isEmpty()
                    ||regPassword.getText().toString().trim().isEmpty()||regRetype.getText().toString().trim().isEmpty())
            {
                Toast.makeText(Register.this,"Fields Are Empty",Toast.LENGTH_SHORT).show();
            }
            else if (regPassword.length()<6)
            {
                Toast.makeText(Register.this,"Password Must Be Of 6 Characters",Toast.LENGTH_SHORT).show();
            }
            else {
                if (regPassword.getText().toString().trim().equals(regRetype.getText().toString().trim()))
                {
                    String etName=regName.getText().toString().trim();
                    String etEmail=regEmail.getText().toString().trim();
                    String etPassword=regPassword.getText().toString().trim();
                    registerUser(etName,etEmail,etPassword);
                }
                else
                {
                    Toast.makeText(Register.this,"Both Password And Re-type Password Are Not Matched", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void registerUser(String name, String email, String password)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
            HashMap<String,Object> map= new HashMap<>();
            map.put("name",name);
            map.put("email",email);
            map.put("id", Objects.requireNonNull(auth.getCurrentUser()).getUid());
            map.put("password",password);

            refer.child("Users").child((auth.getCurrentUser()).getUid()).setValue(map).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    Toast.makeText(Register.this,"Login And Have A Good Day",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this,LogIn.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            });
        }).addOnFailureListener(e ->
                Toast.makeText(Register.this,e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}