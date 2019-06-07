package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model.Cart;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Viewholder.CartViewHolder;

public class AdminUserProductActivity extends AppCompatActivity {
private RecyclerView cartrecycle;
private RecyclerView.LayoutManager layoutManager;
private DatabaseReference cartREF;
private String userid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_product);
        cartrecycle=(RecyclerView)findViewById(R.id.allproductrecyle);
        cartrecycle.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        cartrecycle.setLayoutManager(layoutManager);
        userid=getIntent().getStringExtra("uid");
        cartREF= FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("Admin View").child(userid).child("Products");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cart>options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartREF,Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.name.setText("Name:"+cart.getPname());
                cartViewHolder.quantity.setText("Quantity = "+cart.getQuantity());
                cartViewHolder.price.setText("Price = "+cart.getPrice()+"$");
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
        cartrecycle.setAdapter(adapter);
        adapter.startListening();
    }
}
