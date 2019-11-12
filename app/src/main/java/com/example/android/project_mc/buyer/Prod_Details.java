package com.example.android.project_mc.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Prod_Details extends AppCompatActivity {
   private TextView name , description , price;
   private Button addToCart;
   private ElegantNumberButton elegantNumberButton;
   private ImageView imageView;
   String prod="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod__details);
        prod=getIntent().getExtras().getString("pid").toString();

        name=findViewById(R.id.productName);
        description=findViewById(R.id.productDesc);
        addToCart=findViewById(R.id.cart);
        elegantNumberButton=findViewById(R.id.incremental_btn);
        imageView=findViewById(R.id.product_image);
        price=findViewById(R.id.productsalary);
        
        
        getProductInfo(prod);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addproductToUserCart();
                Intent intent=new Intent(Prod_Details.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addproductToUserCart()
    {
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Carts");
        final Map map=new HashMap();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM ,yyyy");
        String currentDate=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HH:mm:ss a");
        String currentTime=simpleTimeFormat.format(calendar.getTime());

        map.put("pid",prod);
        map.put("pName",name.getText().toString());
        map.put("pDescription",description.getText().toString());
        map.put("pSalary",price.getText().toString());
        map.put("pDate",currentDate);
        map.put("pTime",currentTime);
        map.put("quantity",elegantNumberButton.getNumber());
        map.put("discount","");

        databaseReference.child("Userview").child(Prevelent.userData.getUser_Name()).child("Products").child(prod).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    databaseReference.child("Adminview").child(Prevelent.userData.getUser_Name()).child("Products").child(prod).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                }

        });
    }

    private void getProductInfo(final String prod) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReference.child(prod).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String Name=dataSnapshot.child("ProducName").getValue().toString();
                    String Price=dataSnapshot.child("ProductSalary").getValue().toString();
                    String desc=dataSnapshot.child("ProductDescription").getValue().toString();
                    String Image=dataSnapshot.child("ProducImage").getValue().toString();
                    Products products=new Products(Name,desc,Price,Image,null,null,null,null,null);
                    name.setText(products.getProducName());
                    description.setText(products.getProductDescription());
                    price.setText(products.getProductSalary());
                    Picasso.get().load(products.getProducImage()).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
