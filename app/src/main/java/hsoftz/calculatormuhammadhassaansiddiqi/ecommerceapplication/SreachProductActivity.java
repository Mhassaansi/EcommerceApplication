package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model.product;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Viewholder.ProductViewholder;

public class SreachProductActivity extends AppCompatActivity {
private Button sreachbtn;
private EditText sreachproduct;
private RecyclerView sreachlist;
private String inputSreach="";
private DatabaseReference proref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreach_product);
        proref= FirebaseDatabase.getInstance().getReference().child("Products");
        sreachbtn=(Button)findViewById(R.id.search_btn);
        sreachproduct=(EditText)findViewById(R.id.search_product_name);
        sreachlist=(RecyclerView)findViewById(R.id.search_list);
        sreachlist.setLayoutManager(new LinearLayoutManager(SreachProductActivity.this));
sreachbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        inputSreach=sreachproduct.getText().toString().trim();
        onStart();
    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<product>options=new FirebaseRecyclerOptions.Builder<product>()
                .setQuery(proref.orderByChild("name").startAt(inputSreach),product.class).build();
        FirebaseRecyclerAdapter<product, ProductViewholder>adapter=new FirebaseRecyclerAdapter<product, ProductViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewholder holder, int i, @NonNull final product model) {
                holder.productname.setText(model.getName());
                holder.productprice.setText("Price = "+ model.getPrice()+"$");
                holder.productdescription.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent op=new Intent(SreachProductActivity.this,ProductDetailsActivity.class);
                        op.putExtra("pid",model.getPid());
                        startActivity(op);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.product_items_display,parent,false);
                ProductViewholder holder= new ProductViewholder(view);

                return holder;
            }
        };
sreachlist.setAdapter(adapter);
adapter.startListening();
    }
}
