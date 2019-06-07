package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {
private String ceatogry,desc,price,pname,savecurrentdate,savecurrenttime;
Button add;
EditText InputName,InputDetails,Inputprice;
ImageView imageView;
private static final int  gallerypick=1;
private String productrandomekey,downloadImageurl;
    ProgressDialog loadingBar;
private Uri imageuri;
private StorageReference productRef;
private DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        ceatogry=getIntent().getExtras().get("cetagory").toString();
        productRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        dbRef=FirebaseDatabase.getInstance().getReference().child("Products");
        imageView=(ImageView)findViewById(R.id.imageView2);
        add=(Button)findViewById(R.id.addproduct);
        InputName=(EditText)findViewById(R.id.productname);
        InputDetails=(EditText)findViewById(R.id.productdetails);
        loadingBar=new ProgressDialog(AdminAddNewProductActivity.this);
        Inputprice=(EditText)findViewById(R.id.productprice);
        Toast.makeText(this, ceatogry, Toast.LENGTH_SHORT).show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryActivity();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductdata();
            }
        });
    }

    private void validateProductdata() {
        desc=InputDetails.getText().toString().trim();
        price=Inputprice.getText().toString().trim();
        pname=InputName.getText().toString().trim();
        if(imageuri==null){
            Toast.makeText(this, "Product Image is mendatory", Toast.
                    LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(desc)){
            Toast.makeText(this, "Please Enter the product description",
                    Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please Enter the product price", Toast.
                    LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pname)){
            Toast.makeText(this, "Please Enter the product name", Toast.
                    LENGTH_SHORT).show();
        }
        else{
            storeproductInformation();
        }
    }

    private void storeproductInformation() {
        loadingBar.setTitle("Adding Product");
        loadingBar.setMessage("Please wait, while we adding product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM,dd, yyyy");
        savecurrentdate =currentdate.format(calendar.getTime());
        SimpleDateFormat current=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime =current.format(calendar.getTime());
        productrandomekey=savecurrentdate+savecurrenttime;
        final StorageReference filepath=productRef.child(imageuri.
                getLastPathSegment()+productrandomekey
                +".jpg");
        final UploadTask uploadTask=filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
String message=e.toString();
                Toast.makeText(AdminAddNewProductActivity.this,"Error"+ message,
                        Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Image Upload" +
                        " Succesfully", Toast.LENGTH_SHORT).show();
                Task<Uri>uploadtask=uploadTask.continueWithTask(new Continuation<UploadTask
                        .TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws
                            Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }
downloadImageurl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
downloadImageurl=task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity
                                    .this, "Product Imahe save to database Succesfully"
                                    , Toast
                                    .LENGTH_SHORT)
                                    .show();
                            saveProductinfotodatabase();
                        }

                    }
                });
            }
        });
    }

    private void saveProductinfotodatabase() {
        HashMap<String,Object> productmap=new HashMap<>();
        productmap.put("pid",productrandomekey);
        productmap.put("date",savecurrentdate);
        productmap.put("time",savecurrenttime);
        productmap.put("description",desc);
        productmap.put("image",downloadImageurl);
        productmap.put("cetagory",ceatogry);
        productmap.put("price",price);
        productmap.put("name",pname);
        dbRef.child(productrandomekey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful()){
    Intent i=new Intent (AdminAddNewProductActivity.this,Adminaddproduct.class);
  startActivity(i);
    loadingBar.dismiss();
    Toast.makeText(AdminAddNewProductActivity.this, "Product add succesfully", Toast
            .LENGTH_SHORT).show();
}
else {
    loadingBar.dismiss();
    String message=task.getException().toString();
    Toast.makeText(AdminAddNewProductActivity.this,
            "Error"+ message, Toast.LENGTH_SHORT).show();
}
            }
        });
    }

    private void openGalleryActivity() {
        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
       gallery.setType("image/*");
       startActivityForResult(gallery,gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == gallerypick && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();
        imageView.setImageURI(imageuri);
        }
    }
}
