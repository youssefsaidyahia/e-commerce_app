package com.example.android.project_mc.buyer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.Model.Carts;
import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.cartviewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity {
    private TextView totalPrice , note;
    private Button next;
    private RecyclerView cartsList;
    RecyclerView.LayoutManager layoutManager;
    int salary;
    String ship="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartsList=findViewById(R.id.cart_list);
        cartsList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        cartsList.setLayoutManager(layoutManager);
        totalPrice=findViewById(R.id.total_price);
        next=findViewById(R.id.next_btn);
        note=findViewById(R.id.note);
        checkstate();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, Confirmed_order.class);
                i.putExtra("totalPrice",salary);
                startActivity(i);
            }
        });
    }

    private void checkstate() {
        final  DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevelent.userData.getUser_Name());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ship = dataSnapshot.child("State").getValue().toString();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ship.equals("")) {
            cartsList.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            totalPrice.setVisibility(View.VISIBLE);
            note.setVisibility(View.GONE);

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Carts");

            FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Carts>().setQuery(databaseReference.child("Userview").child(Prevelent.userData.getUser_Name()).child("Products"), Carts.class).build();
            FirebaseRecyclerAdapter<Carts, cartviewHolder> adapter = new FirebaseRecyclerAdapter<Carts, cartviewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull cartviewHolder holder, int position, @NonNull final Carts model) {
                    holder.cname.setText(model.getpName());
                    holder.cprice.setText(model.getpSalary() + "$");
                    holder.cquan.setText("Quantity" + model.getQuantity());
                    salary = salary + (Integer.parseInt(model.getpSalary())) * (Integer.parseInt(model.getQuantity()));
                    totalPrice.setText(String.valueOf(salary));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CharSequence charSequence[] = new CharSequence[]
                                    {
                                            "Edit",
                                            "Delete"
                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("options");
                            builder.setItems(charSequence, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        Intent intent = new Intent(CartActivity.this, CartActivity.class);
                                        intent.putExtra("pid", model.getPid());
                                        startActivity(intent);
                                    } else if (which == 1) {
                                        databaseReference.child("Userview").child(Prevelent.userData.getUser_Name()).child("Products").child(model.getPid()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "product Removed", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(CartActivity.this, CartActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                            builder.show();
                        }
                    });


                }

                @NonNull
                @Override
                public cartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                    cartviewHolder cartviewHolder = new cartviewHolder(view);
                    return cartviewHolder;
                }
            };

            cartsList.setAdapter(adapter);
            adapter.startListening();
        }
        else{
            cartsList.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            totalPrice.setVisibility(View.GONE);
            note.setVisibility(View.VISIBLE);
        }
    }
}
