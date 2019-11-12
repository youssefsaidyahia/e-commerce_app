package com.example.android.project_mc.buyer;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Sign_upAcivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private EditText  u_name , pass ,ret_pass , gen ;
    Button re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acivity);
        re=(Button)findViewById(R.id.login);
        u_name=(EditText)findViewById(R.id.ne_user);
        pass=(EditText)findViewById(R.id.new_pass);
        ret_pass=(EditText)findViewById(R.id.ret_pass);
        gen=(EditText)findViewById(R.id.gender);
        dialog = new ProgressDialog(this);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_user_data();
            }
        });
        }

    private void get_user_data() {
        final String User_Name=u_name.getText().toString();
        final String password=pass.getText().toString();
        String con_passwors=ret_pass.getText().toString();
        final String ge=gen.getText().toString();
        if(User_Name.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please , enter your user name", Toast.LENGTH_LONG).show();
        }
        else if(password.equals("")){
            Toast.makeText(getApplicationContext(),"Please , enter your password",Toast.LENGTH_LONG).show();
        }
        else if(con_passwors.equals("'")){
            Toast.makeText(getApplicationContext(),"you must enter this",Toast.LENGTH_LONG).show();
        }
        else if(!(con_passwors.equals(password))){
            Toast.makeText(getApplicationContext(),"passwords not matched",Toast.LENGTH_LONG).show();
        }
        else{
            dialog.setTitle("Create new account");
            dialog.setMessage("Please wait, while we are checking the credentials.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            checkUserData(User_Name,password,con_passwors,ge);
        }
    }



    private void checkUserData(final String user_name, final String password, String con_passwors, final String ge) {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(user_name).exists()) {
                    Map names=new HashMap();
                    names.put("User Name",user_name);
                    names.put("password",password);
                    if(!ge.equals("")) {
                        names.put("Gender", ge);
                    }
                    databaseReference.child("Users").child(user_name).updateChildren(names).addOnCompleteListener(new OnCompleteListener() {

                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"your acoount created",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                Intent intent =new Intent(Sign_upAcivity.this,Login_activity.class);
                                startActivity(intent);
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "check your internet connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "this user_name is already exsist....please,try again", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(Sign_upAcivity.this,Login_activity.class);
                    startActivity(intent);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
