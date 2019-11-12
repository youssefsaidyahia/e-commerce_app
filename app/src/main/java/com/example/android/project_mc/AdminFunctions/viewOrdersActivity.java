package com.example.android.project_mc.AdminFunctions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.project_mc.Model.Orders;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.ordersvIewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewOrdersActivity extends AppCompatActivity {
    private RecyclerView orderssList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapterView;
    private DatabaseReference databaseReference ;
    public ArrayList<Orders>view_orders=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);
        orderssList=findViewById(R.id.order_list);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderssList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        orderssList.setLayoutManager(layoutManager);
        adapterView=new ordersvIewHolder(view_orders);
        orderssList.setAdapter(adapterView);
        
        getdata();
    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Orders orders = dataSnapshot1.getValue(Orders.class);
                    view_orders.add(orders);
                }
                    adapterView.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
