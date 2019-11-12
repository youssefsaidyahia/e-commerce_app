package com.example.android.project_mc.ViewHolder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.AdminFunctions.AdminApprovedProductsActivity;
import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.buyer.Prod_Details;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productViewHolder extends RecyclerView.Adapter<productViewHolder.productlist > {
    ArrayList<Products>productlists=new ArrayList<>();
    String type;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");

    public productViewHolder(ArrayList<Products> productlists, String type) {
        this.productlists = productlists;
        this.type = type;
    }

    @NonNull
    @Override
    public productlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View l= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,null,false);
        productlist productlist=new productlist(l);
        return productlist;
    }

    @Override
    public void onBindViewHolder(@NonNull productlist holder, final int position) {
        holder.productName.setText(productlists.get(position).getProducName());
        holder.productPrice.setText(productlists.get(position).getProductSalary());
        holder.ProductDescription.setText(productlists.get(position).getProductDescription());
        Picasso.get().load(productlists.get(position).getProducImage()).into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(type.equals("user")){
                    Intent intent = new Intent(v.getContext(), Prod_Details.class);
                    intent.putExtra("pid", productlists.get(position).getProductRandomKey());
                    v.getContext().startActivity(intent);
                }
                else{
                    final String pid=productlists.get(position).getProductRandomKey();
                    CharSequence charSequence[] = new CharSequence[]
                            {
                                    "Yes",
                                    "No"
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Approved this product");
                    builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                databaseReference.child(pid).child("productstate").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(v.getContext(),"the product has benn approved and it is ready to sell",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(v.getContext(), AdminApprovedProductsActivity.class);
                                            v.getContext().startActivity(intent);
                                        }
                                    }
                                });
                            }
                            else if(which==1){
                                 databaseReference.child(pid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if(task.isSuccessful()){
                                             Toast.makeText(v.getContext(),"the product is deleted",Toast.LENGTH_LONG).show();
                                         }
                                     }
                                 });
                            }

                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
       return productlists.size();
    }

    public class productlist extends RecyclerView.ViewHolder {

        public TextView ProductDescription ,productName ,productPrice;
        public ImageView productImage;
        public productlist(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.itemName);
            ProductDescription=itemView.findViewById(R.id.itemDescription);
            productPrice=itemView.findViewById(R.id.itemprice);
            productImage=itemView.findViewById(R.id.itemImage);
            }
    }
}
