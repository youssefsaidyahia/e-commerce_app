package com.example.android.project_mc.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.productViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchVoice extends AppCompatActivity {
    EditText s_text;
    RecyclerView s_products;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapterView;
    int voicecodel=1;
    public ArrayList<Products> pr_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_voice);
        s_text=findViewById(R.id.editText2);
        s_products=findViewById(R.id.search_result);
        ImageButton btn=(ImageButton)findViewById(R.id.imageButton);
        layoutManager=new LinearLayoutManager(this);
        s_products.setLayoutManager(layoutManager);
        adapterView=new productViewHolder(pr_list,null);
        s_products.setAdapter(adapterView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(i,voicecodel);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==voicecodel && resultCode==this.RESULT_OK) {
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String x=text.get(0);
            s_text.setText(x);
            getData();
        }
    }
    private void getData() {
        Query databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("ProducName").startAt(s_text.getText().toString()).endAt(s_text.getText().toString());
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
