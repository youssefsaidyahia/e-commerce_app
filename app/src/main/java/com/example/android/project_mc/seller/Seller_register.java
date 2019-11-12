package com.example.android.project_mc.seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Seller_register extends AppCompatActivity {
  private TextView register;
  private EditText name , phone ,email, password ,shop_name;
  private Button have_account ;
  private ProgressDialog dialog;
  private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        register=findViewById(R.id.register_seller_text);
        name=findViewById(R.id.seller_name);
        phone=findViewById(R.id.seller_phone);
        email=findViewById(R.id.seller_mail);
        shop_name=findViewById(R.id.seller_Business);
        have_account=findViewById(R.id.have_account);
        password=findViewById(R.id.seller_password);
        auth=FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Seller_register.this ,seller_login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerregisterprocess();

            }
        });
    }
    private void sellerregisterprocess() {
        final String s_name=name.getText().toString();
        final String s_phone=phone.getText().toString();
        final String s_mail=email.getText().toString();
        final String s_pass=password.getText().toString();
        final String s_ahop=shop_name.getText().toString();
        if(!s_name.equals("") && !s_phone.equals("") && !s_mail.equals("") && !s_pass.equals("") && !s_ahop.equals("")){
            dialog.setTitle("wait ,you will be ready soon");
            dialog.setMessage("Please wait, while we are checking your data");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            auth.createUserWithEmailAndPassword(s_mail,s_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Sellers");
                         String sid=auth.getCurrentUser().getUid();

                         Map map=new HashMap();
                         map.put("sid",sid);
                         map.put("name",s_name);
                         map.put("phone",s_phone);
                         map.put("Email",s_mail);
                         map.put("ShopAddress",s_ahop);
                         map.put("password",s_pass);

                         reference.child(sid).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                             @Override
                             public void onComplete(@NonNull Task task) {
                                 if(task.isSuccessful()){
                                     dialog.dismiss();
                                     Toast.makeText(getApplicationContext(),"You are registered successfully" ,Toast.LENGTH_LONG).show();
                                     Intent intent=new Intent(Seller_register.this, SellerHomeActivity.class);
                                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                     startActivity(intent);
                                     finish();
                                 }
                             }
                         });
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
