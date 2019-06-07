package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProducts extends AppCompatActivity {
private EditText name,price,description;
private Button applychanges,removebtn;
private ImageView imageView;
    private String ProductID="";
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        ProductID=getIntent().getStringExtra("pid");
        ref= FirebaseDatabase.getInstance().getReference().child("Products").child(ProductID);
        applychanges=(Button)findViewById(R.id.apply_changes);
        removebtn=(Button)findViewById(R.id.Remove);
        name=(EditText)findViewById(R.id.pname_maintain);
        price=(EditText)findViewById(R.id.pprice_maintain);
        description=(EditText)findViewById(R.id.pdecs_maintain);
        imageView=(ImageView) findViewById(R.id.cardimage_maintain);
        dispalyspecificProductInfo();
        applychanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applychanges();

            }
        });
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove_products();
            }
        });

    }

    private void Remove_products() {
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent it=new Intent(AdminMaintainProducts.this,Adminaddproduct.class);
                    startActivity(it);
                    finish();
                    Toast.makeText(AdminMaintainProducts.this,
                            "Product Rmove Succesfull:", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void applychanges() {
        String pnmae=name.getText().toString();
        String pprice=price.getText().toString();
        String pdesc=description.getText().toString();
        if(pnmae.equals("")){
            Toast.makeText(this, "Please Write Product name:", Toast.LENGTH_SHORT).show();
        }
       else if(pprice.equals("")){
            Toast.makeText(this, "Please Write Product price:", Toast.LENGTH_SHORT).show();
        }
       else if(pdesc.equals("")){
            Toast.makeText(this, "Please Write Product Description:", Toast.LENGTH_SHORT).show();
        }
       else{
            HashMap<String,Object> productmap=new HashMap<>();
            productmap.put("pid",ProductID);
            productmap.put("description",pdesc);
            productmap.put("price",pprice);
            productmap.put("name",pnmae);
            ref.updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AdminMaintainProducts.this,
                                "Changes Applied succesfully:", Toast.LENGTH_SHORT).show();
                        Intent it=new Intent(AdminMaintainProducts.this,Adminaddproduct.class);
                        startActivity(it);
                        finish();
                    }
                }
            });
        }
    }

    private void dispalyspecificProductInfo() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nme=dataSnapshot.child("name").getValue().toString();
                    String desc=dataSnapshot.child("description").getValue().toString();
                    String pric=dataSnapshot.child("price").getValue().toString();
                    String pimage=dataSnapshot.child("image").getValue().toString();
                    name.setText(nme);
                    description.setText(desc);
                    price.setText(pric);
                    Picasso.get().load(pimage).into(imageView);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
