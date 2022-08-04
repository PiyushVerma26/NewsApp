package hello.tech.news;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    EditText logEmail,logPassword;
    TextView forgot;
    Button logIn,Register,logGoogle,logPhone;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        logEmail=findViewById(R.id.logEmail);
        logPassword=findViewById(R.id.logPassword);
        forgot=findViewById(R.id.tvForgot);
        logIn=findViewById(R.id.logIn);
        Register=findViewById(R.id.Register);
        logGoogle=findViewById(R.id.logGoogle);
        logPhone=findViewById(R.id.logPhone);
        auth=FirebaseAuth.getInstance();

       Register.setOnClickListener(v -> startActivity(new Intent(LogIn.this, hello.tech.news.Register.class)));

        logIn.setOnClickListener(v -> {
            String etMail,etPassword;
            etMail=logEmail.getText().toString().trim();
            etPassword=logPassword.getText().toString().trim();


            if (TextUtils.isEmpty(etMail)|| TextUtils.isEmpty(etPassword))
            {
                Toast.makeText(LogIn.this,"Fields Are Empty",Toast.LENGTH_SHORT).show();
            }


            else
            {
                loginUser(etMail,etPassword);
            }
        });
    }

    private void loginUser(String mail, String password) {
        auth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Toast.makeText(LogIn.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogIn.this,MainActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }).addOnFailureListener(e -> Toast.makeText(LogIn.this,e.getMessage(),Toast.LENGTH_SHORT).show());
    }

}
