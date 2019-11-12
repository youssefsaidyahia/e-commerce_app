package com.example.android.project_mc.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.project_mc.Interface.ItemClickListener;
import com.example.android.project_mc.R;

public class cartviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView cname ,cquan , cprice;
    private ItemClickListener itemClickListener ;
    public cartviewHolder(View itemView) {
        super(itemView);
        cname=itemView.findViewById(R.id.cart_pname);
        cquan=itemView.findViewById(R.id.cart_pquantity);
        cprice=itemView.findViewById(R.id.cart_pSalary);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
   public  void  setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
   }

}
