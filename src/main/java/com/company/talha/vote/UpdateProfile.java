package com.company.talha.vote;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile extends AppCompatActivity {
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private DatabaseReference dbUser2=FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
    EditText Updatename,alreadypassword,
    Updatesurname;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView userimage;
    private DatabaseReference menuL5= FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mAuth.getCurrentUser().getUid());
    Button save,changephoto;
    TextView Updateemail;
    private Uri filePath;
    String imageurl;
    private final int PICK_IMAGE_REQUEST=71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        final EditText newpassword =(EditText) findViewById(R.id.newpassword);
        final EditText againpassword =(EditText) findViewById(R.id.againpassword);
        userimage=(ImageView)findViewById(R.id.speaker);
        TextView changepassword =(TextView)findViewById(R.id.changepassword);
        alreadypassword=(EditText)findViewById(R.id.alreadypassword);
        changephoto=(Button)findViewById(R.id.changephoto);
        Updatename =(EditText)findViewById(R.id.name);
        setText(Updatename);
        Updatesurname =(EditText)findViewById(R.id.surname);
        setText1(Updatesurname);
        Updateemail=(TextView)findViewById(R.id.emailUpdate);
        setText2(Updateemail);
        save=(Button)findViewById(R.id.save);
        storage=FirebaseStorage .getInstance();
        storageReference=storage.getReference();
        if (getIntent().getExtras() != null) {
            imageurl = (getIntent().getExtras().getString("imageurl"));
            Picasso.get().load(imageurl).into(userimage);
        }
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpassword.getVisibility()==newpassword.VISIBLE){
                    newpassword.setVisibility(View.INVISIBLE);
                    againpassword.setVisibility(View.INVISIBLE);
                    alreadypassword.setVisibility(View.INVISIBLE);
                }else{
                    newpassword.setVisibility(View.VISIBLE);
                    againpassword.setVisibility(View.VISIBLE);
                    alreadypassword.setVisibility(View.VISIBLE);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Updatename.getText().length()<=0){
                    Toast.makeText(getApplicationContext(), "Please enter a name",Toast.LENGTH_SHORT).show();
                }else{
                    if(Updatesurname.getText().length()<=0){
                        Toast.makeText(getApplicationContext(), "Please enter a surname",Toast.LENGTH_SHORT).show();
                    }else{
                        if(newpassword.getText().length()!=0 || againpassword.getText().length()!=0){
                            if(newpassword.getText().toString().equals(againpassword.getText().toString())){
                                if(isValidPassword(newpassword.getText().toString())) {
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    AuthCredential credential = EmailAuthProvider
                                            .getCredential(Updateemail.getText().toString(), alreadypassword.getText().toString());
                                    if(user!=null)
                                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            user.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    menuL5.child("email").setValue(Updateemail.getText().toString());
                                                    menuL5.child("name").setValue(Updatename.getText().toString());
                                                    menuL5.child("surname").setValue(Updatesurname.getText().toString());
                                                    AlertDialog.Builder builder1=new AlertDialog.Builder(UpdateProfile.this);
                                                    builder1.setTitle("Profile Updated");
                                                    builder1.setMessage("Your profile has been changed!");
                                                    builder1.setCancelable(false);
                                                    builder1.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            Intent authIntent=new Intent(UpdateProfile.this,MainPage.class);
                                                            startActivity(authIntent);
                                                        }
                                                    });
                                                    builder1.show();
                                                }
                                            });
                                        }
                                    });

                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Your passwords is not matching!",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            menuL5.child("email").setValue(Updateemail.getText().toString());
                            menuL5.child("name").setValue(Updatename.getText().toString());
                            menuL5.child("surname").setValue(Updatesurname.getText().toString());
                        }

                    }
                }
                uploadImage();



            }
        });

        changephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }
public void chooseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath=data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                userimage.setImageBitmap(bitmap);
            }
            catch (IOException e){
                    e.printStackTrace();
            }
        }
    }
    public void uploadImage(){
         if(filePath!=null){
             final ProgressDialog progressDialog=new ProgressDialog(this);
            //final String url=UUID.randomUUID().toString();
             progressDialog.setTitle("Uploading...");
             progressDialog.show();
             StorageReference ref=storageReference.child("userImages/"+mAuth.getCurrentUser().getUid());
             ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     progressDialog.dismiss();
                     Toast.makeText(getApplicationContext(), "Uploaded!", Toast.LENGTH_SHORT).show();
                     Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                     task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             String photoLink = uri.toString();
                             menuL5.child("image").setValue(photoLink);//kullanıcının altına resmin linkini burda yazıyoruz.
                         }
                     });
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                 }
             }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                     double progress=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded"+(int)progress+"%");
                 }
             });
         }
    }
    public void setText(final EditText view){
        dbUser2.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                view.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    public void setText1(final EditText view){
        dbUser2.child("surname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                view.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setText2(final TextView view){
        dbUser2.child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                view.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
