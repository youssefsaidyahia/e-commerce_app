package com.example.android.project_mc.buyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.AdminFunctions.AdminHomeActivity;
import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.Model.UserData;
import com.example.android.project_mc.R;
import com.example.android.project_mc.seller.SellerHomeActivity;
import com.example.android.project_mc.seller.Seller_register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login_activity extends AppCompatActivity {
    Button log;
    EditText u_name , pass ;
    private ProgressDialog dialog;
    private TextView forget_password , logain_as_seller;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        Button register = (Button) findViewById(R.id.S_u);
        u_name = (EditText) findViewById(R.id.uname);
        pass = (EditText) findViewById(R.id.pass);
        checkBox = findViewById(R.id.remember);
        logain_as_seller = findViewById(R.id.LoginSeller);
        Paper.init(this);
        forget_password=findViewById(R.id.ForgetPassword);
        dialog = new ProgressDialog(this);
        logain_as_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_activity.this, Seller_register.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_activity.this,Sign_upAcivity.class);
                startActivity(i);
            }
        });
        log =(Button)findViewById(R.id.lo);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getData();

            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login_activity.this,resetPassword.class);
                intent.putExtra("check" ,"forget");
                startActivity(intent);
            }
        });
        String User_Name=Paper.book().read(Prevelent.User_Name);
        String User_Password=Paper.book().read(Prevelent.User_Password);
        if (User_Name !="" && User_Password!=""){
            if(!TextUtils.isEmpty(User_Name) && !TextUtils.isEmpty(User_Password)){
                validateData(User_Name ,User_Password);
                dialog.setTitle("Check your name");
                dialog.setMessage("Please wait, while we are checking your data");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(Login_activity.this, SellerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void getData() {
        final String username=u_name.getText().toString();
        final String pa=pass.getText().toString();
        if(username.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please , enter your user name", Toast.LENGTH_LONG).show();
            }
        else if(pa.equals("")){
            Toast.makeText(getApplicationContext(),"Please , enter your password",Toast.LENGTH_LONG).show();
        }
        else{
            dialog.setTitle("Check your name");
            dialog.setMessage("Please wait, while we are checking your data");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            validateData(username,pa);
        }
        }

    private void validateData(final String username, final String pa) {
        if (checkBox.isChecked()){
            Paper.book().write(Prevelent.User_Name,username);
            Paper.book().write(Prevelent.User_Password,pa);
        }
    
        final DatabaseReference users= FirebaseDatabase.getInstance().getReference().child("Users").child(username);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String key = dataSnapshot.getKey();
                    String password = dataSnapshot.child("password").getValue().toString();
                    UserData userData;
                    if (dataSnapshot.child("User Image").exists()){
                        String Image = dataSnapshot.child("User Image").getValue().toString();
                        userData = new UserData(key, password,Image);
                }
                    else{
                         userData = new UserData(key, password,null);
                    }
                    if (!(username.equals(key))) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "your name is not exist,try again", Toast.LENGTH_LONG).show();
                    } else {

                        if (!pa.equals(password)) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "your passsword is not exsist", Toast.LENGTH_LONG).show();

                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "login successfully", Toast.LENGTH_LONG).show();
                            if(username.equals("goe")) {
                                Intent i = new Intent(Login_activity.this, AdminHomeActivity.class);
                                Prevelent.userData=userData;
                                startActivity(i);
                            }
                            else{
                                Intent i = new Intent(Login_activity.this, byerHomeActivity.class);
                                startActivity(i);
                                Prevelent.userData=userData;
                            }
                        }
                    }
                }

                else{
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"your data is not exsist,please SignUp",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
