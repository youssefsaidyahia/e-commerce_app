package com.example.android.project_mc.AdminFunctions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.project_mc.Model.Carts;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.ProductsViewholder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewProductsActivity extends AppCompatActivity {
    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    String selecteduser;
    RecyclerView.Adapter adapterView;
    public ArrayList<Carts> cartlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_products);
        productsList=findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        selecteduser=getIntent().getExtras().getString("username");
        layoutManager=new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);
        adapterView=new ProductsViewholder(cartlist);
        productsList.setAdapter(adapterView);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Carts").child("Adminview").child(selecteduser).child("Products");
        getdata();
    }
    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Carts carts = dataSnapshot1.getValue(Carts.class);
                    cartlist.add(carts);
                }
                adapterView.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
