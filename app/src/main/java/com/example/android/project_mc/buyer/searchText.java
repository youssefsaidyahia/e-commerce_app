package com.example.android.project_mc.buyer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.productViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

;

public class searchText extends AppCompatActivity {
    EditText s_text ;
    Button search;
    RecyclerView s_products;
    RecyclerView.LayoutManager layoutManager;
    String input="";
    RecyclerView.Adapter adapterView;
    public ArrayList<Products> pr_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_text);
        s_text=findViewById(R.id.search);
        search=findViewById(R.id.s_text);
        s_products=findViewById(R.id.p_list);
        layoutManager=new LinearLayoutManager(this);
        s_products.setLayoutManager(layoutManager);
        adapterView=new productViewHolder(pr_list,null);
        s_products.setAdapter(adapterView);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=s_text.getText().toString();
                 getData();

            }
        });
    }

    private void getData() {
        Query databaseReference=FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("ProducName").startAt(input).endAt(input);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Products products = dataSnapshot1.getValue(Products.class);
                    if (products.getProductstate().equals("Approved")) {
                        pr_list.add(products);
                    }
                }
                adapterView.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    }
