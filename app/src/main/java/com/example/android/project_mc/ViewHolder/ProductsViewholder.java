package com.example.android.project_mc.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.project_mc.Model.Carts;
import com.example.android.project_mc.R;

import java.util.ArrayList;

public class ProductsViewholder extends RecyclerView.Adapter<ProductsViewholder.productviewslist>{
    ArrayList<Carts> productslist=new ArrayList<>();

    public ProductsViewholder(ArrayList<Carts> productslist) {
        this.productslist = productslist;
    }

    @NonNull
    @Override
    public productviewslist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        productviewslist productviewslist =new productviewslist(view);
        return productviewslist;
    }

    @Override
    public void onBindViewHolder(@NonNull productviewslist holder, int position) {
        holder.cname.setText(productslist.get(position).getpName());
        holder.cprice.setText(productslist.get(position).getpSalary()+" $");
        holder.cquan.setText("Quantity ="+productslist.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return productslist.size();
    }

    public class productviewslist extends RecyclerView.ViewHolder{
        public TextView cname ,cquan , cprice;
        public productviewslist(View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.cart_pname);
            cquan=itemView.findViewById(R.id.cart_pquantity);
            cprice=itemView.findViewById(R.id.cart_pSalary);
        }
    }
}
