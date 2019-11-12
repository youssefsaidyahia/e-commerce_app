package com.example.android.project_mc.buyer;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class changepassword extends AppCompatActivity {
    private EditText new_pass , conf_pass  ;
    private Button confirm ;
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        new_pass=findViewById(R.id.newPassword);
        conf_pass=findViewById(R.id.again);
        confirm=findViewById(R.id.verfiy_pass);
        username=getIntent().getExtras().getString("name");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedate();
            }
        });
    }

    private void validatedate() {
        String pass=new_pass.getText().toString();
        String c_pass=conf_pass.getText().toString();
        if(new_pass.equals("")){
            Toast.makeText(getApplicationContext(),"enter your  new password",Toast.LENGTH_SHORT).show();
        }
        else if(conf_pass.equals("")){
            Toast.makeText(getApplicationContext(),"you should enter this field ",Toast.LENGTH_SHORT).show();
        }
        else if(!(pass.equals(c_pass))){
            Toast.makeText(getApplicationContext(),"password should be the same",Toast.LENGTH_SHORT).show();
        }
        else{
            changemypassword();
        }
    }

    private void changemypassword() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(username);
        Map map=new HashMap();
        map.put("password",new_pass.getText().toString());
        databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"password changed successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(changepassword.this,Login_activity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
