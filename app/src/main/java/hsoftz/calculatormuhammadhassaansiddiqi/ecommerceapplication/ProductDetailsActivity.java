package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model.product;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Prevalet.prevalet;

public class ProductDetailsActivity extends AppCompatActivity {
private TextView proname,prodetails,prodprice;
private FloatingActionButton cart;
private ElegantNumberButton counter;
private ImageView im;
private String ProductID="",state="Normal";
private Button addcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ProductID=getIntent().getStringExtra("pid");
        addcart=(Button)findViewById(R.id.add_to_cart_button);
        im=(ImageView)findViewById(R.id.product_image);
        proname=(TextView)findViewById(R.id.product_name);
        prodetails=(TextView)findViewById(R.id.product_description);
        prodprice=(TextView)findViewById(R.id.product_price);
     counter=(ElegantNumberButton)findViewById(R.id.count);
       // cart=(FloatingActionButton)findViewById(R.id.add_to_cart);
        getProductDetails(ProductID);
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(ProductDetailsActivity.this, "you can add purchase" +
                            " more products, once your order is shipped or confirmed.",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    add_to_cart();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }

    private void add_to_cart() {
        String savecurrentdate,savecurrenttime;
        Calendar calender=Calendar.getInstance();
        SimpleDateFormat simpledate=new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=simpledate.format(calender.getTime());
        SimpleDateFormat simpletime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=simpletime.format(calender.getTime());
      final  DatabaseReference cartref=FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartlist=new HashMap<>();
        cartlist.put("pid",ProductID);
        cartlist.put("price",prodprice.getText().toString());
        cartlist.put("pname",proname.getText().toString());
        cartlist.put("quantity",counter.getNumber());
        cartlist.put("date",savecurrentdate);
        cartlist.put("time",savecurrenttime);
        cartlist.put("discount","");
cartref.child("User View").child(prevalet.Online_users.getPhone())
        .child("Products").child(ProductID).updateChildren(cartlist)
.addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful()){
            cartref.child("Admin View").child(prevalet.Online_users.getPhone())
                    .child("Products").child(ProductID).updateChildren(cartlist)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ProductDetailsActivity.this, "Added To Cart Succesfully:",
                                    Toast.LENGTH_SHORT).show();
                            Intent it=new Intent(
                                    ProductDetailsActivity.this,HomeActivity.class);
                            startActivity(it);

                        }
                    });
        }
    }
});

    }

    private void getProductDetails(final String ProductID) {
        DatabaseReference dbREF= FirebaseDatabase.getInstance().getReference().child("Products");
        dbREF.child(ProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    product Product = dataSnapshot.getValue(product.class);
                 //   Toast.makeText(ProductDetailsActivity.this,ProductID , Toast.LENGTH_SHORT).show();
                    proname.setText(Product.getName());
                  prodprice.setText(Product.getPrice());
                    prodetails.setText(Product.getDescription());
                    Picasso.get().load(Product.getImage()).into(im);

                }
                else{
                    Toast.makeText(ProductDetailsActivity.this, "ERROR:", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(prevalet.Online_users.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        state = "Order Shipped";
                    }
                    else if(shippingState.equals("Not Shipped"))
                    {
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
