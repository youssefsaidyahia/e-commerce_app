package com.example.android.project_mc.ViewHolder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.AdminFunctions.AdminViewProductsActivity;
import com.example.android.project_mc.AdminFunctions.SendingMail;
import com.example.android.project_mc.Model.Orders;
import com.example.android.project_mc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class ordersvIewHolder  extends RecyclerView.Adapter<ordersvIewHolder.OrderView > {
    ArrayList<Orders>Orders_List=new ArrayList<>();
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders");

    public ordersvIewHolder(ArrayList<Orders> orders_List) {
        Orders_List = orders_List;
    }
    String shipped="not state";
    @NonNull
    @Override
    public OrderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem,parent,false);
        OrderView ordersvIewHolder=new OrderView(view);
        return ordersvIewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderView holder, final int position) {
        holder.cname.setText(Orders_List.get(position).getName());
        holder.cphone.setText(Orders_List.get(position).getNumber());
        holder.cAdress.setText(Orders_List.get(position).getAddress());
        holder.o_salary.setText(String.valueOf(Orders_List.get(position).getTotal_Price()));
        holder.o_time.setText(Orders_List.get(position).getDate());
        holder.type.setText(Orders_List.get(position).getType());
        if(Orders_List.get(position).getType().equals("Cash on delivery")){
            holder.c_n.setVisibility(View.GONE);
            holder.c_d.setVisibility(View.GONE);
            holder.cvv.setVisibility(View.GONE);
        }
        else if(Orders_List.get(position).getType().equals("credit card")){
            holder.c_n.setVisibility(View.VISIBLE);
            holder.c_d.setVisibility(View.VISIBLE);
            holder.cvv.setVisibility(View.VISIBLE);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        holder.c_n.setText(dataSnapshot.child("Card number").getValue().toString());
                        holder.c_d.setText(dataSnapshot.child("Card expiry date").getValue().toString());
                        holder.cvv.setText(dataSnapshot.child("Card CVV").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        holder.viewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , AdminViewProductsActivity.class);
                intent.putExtra("username", Orders_List.get(position).getUserName());
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                CharSequence charSequence[] = new CharSequence[]
                        {
                                "Yes",
                                "No"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Are you sure you want to ship this order");
                builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            shipped="state";
                            changestate(Orders_List.get(position).getUserName(),v.getContext());
                            removecarts(Orders_List.get(position).getUserName());

                        }
                        else if(which==1){
                            shipped="not accept";
                            changestate(Orders_List.get(position).getUserName(),v.getContext());
                            removecarts(Orders_List.get(position).getUserName());
                        }

                    }
                });
                builder.show();


                }
            });
        }

    private void removecarts(String userName) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Carts").child("Adminview").child(userName).child("Products");
        databaseReference.removeValue();
    }

    private void changestate(String userName, final Context context) {
        Map map=new HashMap();
        map.put("Shipped",shipped);
        databaseReference.child(userName).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"states get",Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent intent = new Intent(context , SendingMail.class);
        intent.putExtra("username",userName);
        intent.putExtra("shipped",shipped);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return Orders_List.size();
    }


    public class OrderView extends RecyclerView.ViewHolder {
        public TextView cname ,cphone , o_salary , cAdress , o_time , type ,c_n, c_d ,cvv;
        public Button viewProducts;
        public OrderView(View itemView) {
            super(itemView);
            cname=itemView.findViewById(R.id.clientname);
            cphone=itemView.findViewById(R.id.clientPhone);
            o_salary=itemView.findViewById(R.id.orderSalary);
            cAdress=itemView.findViewById(R.id.Address);
            o_time=itemView.findViewById(R.id.Date);
            type=itemView.findViewById(R.id.purc_type);
            c_n=itemView.findViewById(R.id.card_number);
            c_d=itemView.findViewById(R.id.card_date);
            cvv=itemView.findViewById(R.id.cvv);
            viewProducts=itemView.findViewById(R.id.viewProducts);
        }
    }
}
