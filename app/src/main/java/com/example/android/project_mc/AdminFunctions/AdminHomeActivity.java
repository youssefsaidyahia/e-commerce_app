
package com.example.android.project_mc.AdminFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.project_mc.R;
import com.example.android.project_mc.buyer.Login_activity;

public class AdminHomeActivity extends AppCompatActivity {
    Button new_order , logout  , Approved_products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        new_order=findViewById(R.id.new_order);
        logout=findViewById(R.id.logout);
        Approved_products=findViewById(R.id.approved_products);
        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this,viewOrdersActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this,Login_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        Approved_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this,AdminApprovedProductsActivity.class);
                intent.putExtra("User","AdminApprove");
                startActivity(intent);
            }
        });
    }
}
