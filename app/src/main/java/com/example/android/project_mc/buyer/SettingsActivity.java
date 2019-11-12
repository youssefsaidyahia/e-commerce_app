package com.example.android.project_mc.buyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.project_mc.Model.Prevelent;
import com.example.android.project_mc.Model.UserData;
import com.example.android.project_mc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private TextView close , update, change , chooseMap;
    private EditText UserName , UserPhone , UserAdress;
    private CircleImageView userImage;
    private String checked="" , imagedownloaded;
    private StorageReference storageReference;
    private StorageTask uploadTask ;
    Uri imageUri;
    UserData userData;
    String checke="" , myadress;
    private Button security;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        close=findViewById(R.id.Close);
        change=findViewById(R.id.change_profile);
        update=findViewById(R.id.Update);
        UserName=findViewById(R.id.Name);
        UserPhone=findViewById(R.id.Number);
        UserAdress=findViewById(R.id.Adress);
        userImage=findViewById(R.id.Userprofile);
        chooseMap=findViewById(R.id.choosemap);
        security=findViewById(R.id.security_questions);
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        getUserInfo( UserName,UserPhone,UserAdress,userImage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked.equals("changed")){
                    updateUserInfo();

                }
                else{
                    saveUserInfo();
                }
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked="changed";
                CropImage.activity(imageUri).setAspectRatio(1,1)
                        .start(SettingsActivity.this);

            }
        });
        chooseMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checke="true";
                Intent intent=new Intent(SettingsActivity.this,MapsActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });
        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent =new Intent(SettingsActivity.this,resetPassword.class);
            intent.putExtra("check" ,"security");
            startActivity(intent);
            }
        });
    }

    private void getUserInfo(final EditText userName,final  EditText userPhone,final EditText userAdress,final CircleImageView userImage) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevelent.userData.getUser_Name());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if (dataSnapshot.child("User Image").exists())
                    {
                        String Name = dataSnapshot.child("User Name").getValue().toString();
                        String phone = dataSnapshot.child("Phone Number").getValue().toString();
                        String Image = dataSnapshot.child("User Image").getValue().toString();
                        String Adress = dataSnapshot.child("User Address").getValue().toString();
                        userData=new UserData(Prevelent.userData.getUser_Name(),Prevelent.userData.getPassword(),Image);
                        Prevelent.userData=userData;
                        userName.setText(Name);
                        userPhone.setText(phone);
                        Picasso.get().load(Image).into(userImage);
                        userAdress.setText(Adress);

                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) && (resultCode== RESULT_OK) && (data!=null))
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();

            userImage.setImageURI(imageUri);
        }

        else{
            Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void updateUserInfo() {
        if(imageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Image not found", Toast.LENGTH_LONG).show();
        }
        else if(UserName.equals("")){
            Toast.makeText(getApplicationContext(),"product Name is missed",Toast.LENGTH_LONG).show();
        }
        else if(UserPhone.equals("")){
            Toast.makeText(getApplicationContext(),"phone is missed",Toast.LENGTH_LONG).show();
        }
        else if(UserAdress.equals("")){
            Toast.makeText(getApplicationContext(),"Adress is missed",Toast.LENGTH_LONG).show();
        }
        else if(checked.equals("changed")){
          Loadimage();
         }

    }

    private void Loadimage() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("loading image");
        dialog.setMessage("Please wait, while we loading loading your image ");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final StorageReference filepath = storageReference.child(Prevelent.userData.getImage() + ".jpg");
        uploadTask = filepath.putFile(imageUri);

        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri uri = task.getResult();
                    imagedownloaded = uri.toString();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("User Name", UserName.getText().toString());
                    hashMap.put("Phone Number", UserPhone.getText().toString());

              if(checke.equals("true")){
                        myadress=getIntent().getExtras().getString("address");
                    }
                    else
                    {
                        myadress=UserAdress.getText().toString();
                    }
                    hashMap.put("User Address",UserAdress.getText().toString());
                    hashMap.put("User Image", imagedownloaded);
                    UserData userData=new UserData(Prevelent.userData.getUser_Name(),Prevelent.userData.getPassword(),imagedownloaded);
                    Prevelent.userData=userData;
                    databaseReference.child(Prevelent.userData.getUser_Name()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                startActivity(new Intent(SettingsActivity.this, Login_activity.class));
                                Toast.makeText(getApplicationContext(), "information updated successfully", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveUserInfo() {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap <String,Object>hashMap=new HashMap<>();
        hashMap.put("User Name",UserName.getText().toString());
        hashMap.put("Phone Number",UserPhone.getText().toString());
      if(checke.equals("true")){
            myadress=getIntent().getExtras().getString("address");
        }
        else
        {
            myadress=UserAdress.getText().toString();
        }
        hashMap.put("User Address",myadress);
        Prevelent.userData=userData;
        databaseReference.child(Prevelent.userData.getUser_Name()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){

                    startActivity(new Intent(SettingsActivity.this,byerHomeActivity.class));
                    Toast.makeText(getApplicationContext(),"informatio updated successfully",Toast.LENGTH_LONG).show();
                }
                else{

                    Toast.makeText(getApplicationContext(),"try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
