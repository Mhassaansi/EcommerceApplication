package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model.Cart;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Prevalet.prevalet;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Viewholder.CartViewHolder;

public class CartActivity extends AppCompatActivity {
private TextView tv,tv1;
private Button bnext;
private RecyclerView rcycle;
private  RecyclerView.LayoutManager layoutManager;
    private int overtoatlprice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rcycle=(RecyclerView)findViewById(R.id.cart_list);
        rcycle.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
      rcycle.setLayoutManager(layoutManager);
      bnext=(Button)findViewById(R.id.next_btn);
      tv=(TextView)findViewById(R.id.total_price);
        tv1=(TextView)findViewById(R.id.msg1);


bnext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        tv.setText("Total Price = "+String.valueOf(overtoatlprice)+ "$");
        Intent it=new Intent(CartActivity.this,ConfirmFinalActivity.class);
        it.putExtra("Total Price",String.valueOf(overtoatlprice));
        startActivity(it);
    }
});
    }

    @Override
    public void onStart() {
        super.onStart();
        CheckOrderState();
       // tv.setText("Total Price = "+String.valueOf(overtoatlprice)+ "$");
      final DatabaseReference cartref=FirebaseDatabase.getInstance().getReference()
              .child("Cart List");
        FirebaseRecyclerOptions<Cart>options= new FirebaseRecyclerOptions.Builder<Cart>().setQuery
                (cartref.child("User View").child(prevalet.Online_users.getPhone())
                        .child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull
            final Cart cart) {
              cartViewHolder.name.setText("Name:"+cart.getPname());
              cartViewHolder.quantity.setText("Quantity = "+cart.getQuantity());
              cartViewHolder.price.setText("Price = "+cart.getPrice()+"$");
                int oneTimeproductprice=((Integer.valueOf(cart.getPrice())))*
                        Integer.valueOf(cart.getQuantity());
                overtoatlprice=overtoatlprice+oneTimeproductprice;
cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        CharSequence options[]=new CharSequence[]{
         "Edit",
                "Remove"
        };
        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Cart Options:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent it=new Intent(CartActivity.this,ProductDetailsActivity.class);
                    it.putExtra("pid",cart.getPid());
                    startActivity(it);
                }
                if(which==1){
                   cartref.child("User View").child(prevalet.Online_users.getPhone())
                           .child("Products").child(cart.getPid())
                           .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(CartActivity.this, "Item Remove" +
                                       " Succesfully:", Toast.LENGTH_SHORT).show();
                               Intent it=new Intent(CartActivity.this,HomeActivity.class);
                               startActivity(it);
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
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.cart_items_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(v);

                return holder;
            }
        };
rcycle.setAdapter(adapter);
adapter.startListening();
    }
    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(prevalet.Online_users.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        tv.setText("Dear " + userName + "\n order is shipped successfully.");
                        rcycle.setVisibility(View.GONE);

                        tv1.setVisibility(View.VISIBLE);
                        tv1.setText("Congratulations, your final order has been Shipped" +
                                " successfully. Soon you will received your order at your door " +
                                "step.");
                        bnext.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "you can purchase more" +
                                " products" + ", once you received your first final order.",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                    else if(shippingState.equals("Not Shipped"))
                    {
                        tv.setText("Shipping State = Not Shipped");
                        rcycle.setVisibility(View.GONE);

                        tv1.setVisibility(View.VISIBLE);
                        bnext.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "you can purchase more " +
                                "products, once you received your first final order.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
  /*  private void checkorderstate(){
        DatabaseReference orderref;
        orderref=FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(prevalet.Online_users.getPhone());
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String Shippingstate=dataSnapshot.child("state").getValue().toString();
                    String Username=dataSnapshot.child("name").getValue().toString();
                    if(Shippingstate.equals("Shipped")){
                        tv.setText("Dear " + Username + " \n Your rder has been Shipped:");
                        rcycle.setVisibility(View.GONE);
                        tv1.setVisibility(View.VISIBLE);
                        bnext.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,
                                "You can Purshase more Products until Your frist order Has been recivied:", Toast.LENGTH_SHORT).show();
                    }
                    else if(Shippingstate.equals("Not Shipped")){
                        tv.setText("Shipping State = Not Shipped:");
                        rcycle.setVisibility(View.GONE);
                        tv1.setVisibility(View.VISIBLE);
                        tv1.setText("Your Final order Has Been Palced Succesfully.Soon It will be on Your door step:");
                        bnext.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,
                                "You can Purshase more Products until Your frist order Has been recivied:", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/
}
