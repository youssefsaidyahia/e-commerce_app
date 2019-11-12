package com.example.android.project_mc.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.android.project_mc.R;

public class Categorylist extends AppCompatActivity {
    ImageView books , hats , sports , laptops , sweather ,tshirts ,shoess , watches ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);
        books=findViewById(R.id.books);
        hats=findViewById(R.id.hats);
        sports=findViewById(R.id.sports);
        laptops=findViewById(R.id.laptops);
        sweather=findViewById(R.id.sweather);
        tshirts=findViewById(R.id.tshirts);
        shoess=findViewById(R.id.shoess);
        watches=findViewById(R.id.watches);

        getcategory();

    }

    private void getcategory() {
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","books");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","hats");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","sports");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","laptops");
                startActivity(intent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","sweather");
                startActivity(intent);
            }
        });
        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","tshirts");
                startActivity(intent);
            }
        });
        shoess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","shoess");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Categorylist.this,addproduct.class);
                intent.putExtra("categoryName","watches");
                startActivity(intent);
            }
        });


  }
    }


