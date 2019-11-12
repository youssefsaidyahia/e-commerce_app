package com.example.android.project_mc.seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.project_mc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addproduct extends AppCompatActivity {
    Button add_product;
    ImageView mSelectProduct;
    EditText mProducName, mProductDescription, mProductSalary;
    String ProductCategory, ProducName, ProductDescription, ProductSalary , currentTime ,currentDate ,productRandomKey,ProductImageUri;
    int pick_image = 1;
    private Uri mediauri;
    DatabaseReference ProductInfo , sellerInfo;
    StorageReference storageReference;
    private ProgressDialog dialog;
    private String sname ,sid ,sphone ,smail, saddress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);



        add_product = findViewById(R.id.add_product);
        mSelectProduct = findViewById(R.id.select_product);
        mProducName = findViewById(R.id.product_name);
        mProductDescription = findViewById(R.id.description);
        mProductSalary = findViewById(R.id.salary);
        ProductCategory = getIntent().getExtras().getString("categoryName");
        dialog = new ProgressDialog(this);
        ProductInfo=FirebaseDatabase.getInstance().getReference().child("Products");
        sellerInfo=FirebaseDatabase.getInstance().getReference().child("Sellers");
        storageReference=FirebaseStorage.getInstance().getReference().child("images");
        mSelectProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                validateData();
            }
        });

        sellerInfo.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    sname=dataSnapshot.child("name").getValue().toString();
                    sphone=dataSnapshot.child("phone").getValue().toString();
                    smail=dataSnapshot.child("Email").getValue().toString();
                    sid=dataSnapshot.child("sid").getValue().toString();
                    saddress=dataSnapshot.child("ShopAddress").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void validateData() {
        ProducName=mProducName.getText().toString();
        ProductDescription=mProductDescription.getText().toString();
        ProductSalary=mProductSalary.getText().toString();
        if(mediauri==null)
        {
            Toast.makeText(getApplicationContext(), "Image not found", Toast.LENGTH_LONG).show();
        }
        else if(ProducName.equals("")){
            Toast.makeText(getApplicationContext(),"product Name is missed",Toast.LENGTH_LONG).show();
        }
        else if(ProductDescription.equals("")){
            Toast.makeText(getApplicationContext(),"product Description is missed",Toast.LENGTH_LONG).show();
        }
        else if(ProductSalary.equals("")){
            Toast.makeText(getApplicationContext(),"product Price is missed",Toast.LENGTH_LONG).show();
        }
        else{
            getImageInfo();
        }

    }

    private void getImageInfo() {
        dialog.setTitle("Adding New Product");
        dialog.setMessage("Dear seller , Please wait, while we loading data ");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM ,yyyy");
        currentDate=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("HH:mm:ss a");
        currentTime=simpleTimeFormat.format(calendar.getTime());

        productRandomKey=currentDate+" "+currentTime;

        final StorageReference filepath=storageReference.child(mediauri.getLastPathSegment()+productRandomKey+".jpg");
        final UploadTask uploadTask=filepath.putFile(mediauri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error=e.toString();
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),error+" found",Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Image uploaded successfully",Toast.LENGTH_LONG).show();
                Task<Uri>uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            String message= task.getException().toString();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error : " + message,Toast.LENGTH_LONG).show();

                        }
                   ProductImageUri=filepath.getDownloadUrl().toString();
                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            ProductImageUri=task.getResult().toString();
                            Toast.makeText(getApplicationContext(), "Image captured successfully", Toast.LENGTH_LONG).show();
                            saveProductinformationintoDatabasee();
                        }
                    }
                });
            }
        });
    }

    private void saveProductinformationintoDatabasee() {

        HashMap<String,Object> map=new HashMap<>();
        map.put("productRandomKey",productRandomKey);
        map.put("ProductcurrentData",currentDate);
        map.put("ProductcurrentTime",currentTime);
        map.put("ProducImage",ProductImageUri);
        map.put("ProducName",ProducName);
        map.put("ProductCategory",ProductCategory);
        map.put("ProductDescription",ProductDescription);
        map.put("ProductSalary",ProductSalary);
        map.put("sellerName",sname);
        map.put("sellerPhone",sphone);
        map.put("sellerEmail",smail);
        map.put("sellerAddress",saddress);
        map.put("sellerId",sid);
        map.put("productstate","not Approved");
        ProductInfo.child(productRandomKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(addproduct.this,SellerHomeActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Product information loaded into database successfully",Toast.LENGTH_LONG).show();

                }
                else{
                    String message= task.getException().toString();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Error : " + message,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void opengallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select product picture"), pick_image);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == pick_image) {
                if (data != null) {
                    mediauri = data.getData();
                    mSelectProduct.setImageURI(mediauri);
                }


            }
        }
    }
}