package com.example.android.project_mc.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.Model.Products;
import com.example.android.project_mc.R;
import com.example.android.project_mc.ViewHolder.productViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class byerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  RecyclerView items;
  RecyclerView.LayoutManager itemsLayoutManager;
  RecyclerView.Adapter adapterView;
  CircleImageView UserProfileImage;
  public ArrayList<Products>products=new ArrayList<>();
  DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Products");
  String type="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.byer_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        Paper.init(this);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            type=getIntent().getExtras().getString("User");
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("Admin")) {
                    Intent intent=new Intent(byerHomeActivity.this,CartActivity.class);
                    startActivity(intent);
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view=navigationView.getHeaderView(0);
        TextView UserNameProfile=view.findViewById(R.id.userName);

        if(!type.equals("Admin")) {
            UserNameProfile.setText(Prevelent.userData.getUser_Name());

            UserProfileImage = view.findViewById(R.id.profile_image);
        }

        intializeview();
        getData();


    }
    Products product1 ;
    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        product1=dataSnapshot1.getValue(Products.class);
                        if(product1.getProductstate().equals("Approved")) {
                            products.add(product1);
                        }

                    }
                }
                if(Prevelent.userData.getImage()!=null) {
                    Picasso.get().load(Prevelent.userData.getImage()).into(UserProfileImage);
                }
                    adapterView.notifyDataSetChanged();

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void intializeview() {
        items=findViewById(R.id.Menu);
        items.setHasFixedSize(true);
        itemsLayoutManager=new LinearLayoutManager(this);
        items.setLayoutManager(itemsLayoutManager);
        adapterView=new productViewHolder(products,type);
        items.setAdapter(adapterView);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*
        if (id == R.id.action_settings) {
            return true;
        }
*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            if(!type.equals("Admin")) {
                Intent intent=new Intent(byerHomeActivity.this,CartActivity.class);
                startActivity(intent);
            }

        }
        else if (id == R.id.nav_search)
        {
            if(!type.equals("Admin")) {
                Intent intent=new Intent(byerHomeActivity.this,SearchActivity.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.nav_categories)
        {

        }
        else if (id == R.id.nav_profile)
        {
            if(!type.equals("Admin")) {
                Intent intent=new Intent(byerHomeActivity.this,SettingsActivity.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.nav_logout)
        {
            if(!type.equals("Admin")) {
                Paper.book().destroy();
                Intent intent =new Intent(byerHomeActivity.this,Login_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
