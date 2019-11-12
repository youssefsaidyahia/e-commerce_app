package com.example.android.project_mc.seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class seller_login extends AppCompatActivity {
    private EditText email, password ;
    private Button login;
    private ProgressDialog dialog;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        email=findViewById(R.id.sellerEmail);
        password=findViewById(R.id.sellerPassword);
        login=findViewById(R.id.Register);

        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginseller();
            }
        });
    }

    private void loginseller() {
        final String s_mail=email.getText().toString();
        final String s_pass=password.getText().toString();
        if(!s_mail.equals("") && !s_pass.equals("")){
            dialog.setTitle("wait ,you will be ready soon");
            dialog.setMessage("Please wait, while we are checking your data");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            auth.signInWithEmailAndPassword(s_mail,s_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Intent intent=new Intent(seller_login.this, SellerHomeActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                       finish();
                   }
                }
            });
        }
        else{
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"complete the form ,please" ,Toast.LENGTH_LONG).show();
        }
    }
}
