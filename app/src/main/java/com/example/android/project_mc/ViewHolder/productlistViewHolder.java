package com.example.android.project_mc.ViewHolder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.seller.sellerMaintainProductsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productlistViewHolder extends RecyclerView.Adapter<productlistViewHolder.productsellerview> {
    ArrayList<Products>productlists=new ArrayList<>();

    public productlistViewHolder(ArrayList<Products> productlists) {
        this.productlists = productlists;
    }
    @NonNull
    @Override
    public productsellerview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View l= LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_products_item,null,false);
        productsellerview productlist=new productsellerview(l);
        return productlist;
    }

    @Override
    public void onBindViewHolder(@NonNull productlistViewHolder.productsellerview holder,final int position) {
        holder.productName.setText(productlists.get(position).getProducName());
        holder.productPrice.setText(productlists.get(position).getProductSalary());
        holder.ProductDescription.setText(productlists.get(position).getProductDescription());
        holder.productState.setText(productlists.get(position).getProductstate());
        Picasso.get().load(productlists.get(position).getProducImage()).into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),sellerMaintainProductsActivity.class);
                intent.putExtra("pid", productlists.get(position).getProductRandomKey());
                v.getContext().startActivity(intent);
                }
        });
    }

    @Override
    public int getItemCount() {
        return productlists.size();
    }
    public class productsellerview extends RecyclerView.ViewHolder {

        public TextView ProductDescription ,productName ,productPrice ,productState;
        public ImageView productImage;
        public productsellerview(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.s_itemName);
            ProductDescription=itemView.findViewById(R.id.s_itemDescription);
            productPrice=itemView.findViewById(R.id.s_itemprice);
            productImage=itemView.findViewById(R.id.s_itemImage);
            productState=itemView.findViewById(R.id.s_itemState);
        }
    }
}
