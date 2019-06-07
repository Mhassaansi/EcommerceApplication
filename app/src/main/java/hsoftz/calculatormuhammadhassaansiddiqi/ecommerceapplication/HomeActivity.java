package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model.product;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Prevalet.prevalet;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Viewholder.ProductViewholder;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private long backPressTime;
private Toast backToast;
    private DatabaseReference productRef;
private RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
private  String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
   Intent it=getIntent();
 Bundle bundle=it.getExtras();
 if(bundle!=null){
     type=getIntent().getExtras().get("Admin").toString();
 }
        productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        Paper.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if(!type.equals("Admin")){
                    Intent ip=new Intent(HomeActivity.this,CartActivity.class);
                    startActivity(ip);
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
        View haderview=navigationView.getHeaderView(0);
        TextView username=haderview.findViewById(R.id.textView);
        CircleImageView circleImageView=haderview.findViewById(R.id.profile_image);
       if(!type.equals("Admin")){
           username.setText(prevalet.Online_users.getName());
           Picasso.get().load(prevalet.Online_users.getImage()).placeholder(R.drawable.profileyy).
                   into(circleImageView);
       }
       else if(type.equals("Admin")){
           username.setText("Admin");
       }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
      layoutManager=new LinearLayoutManager(this);
      recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<product>option=new
                FirebaseRecyclerOptions.Builder<product>()
                .setQuery(productRef,product.class).build();
        FirebaseRecyclerAdapter<product, ProductViewholder>adapter=
                new FirebaseRecyclerAdapter<product, ProductViewholder>(option) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewholder holder, int position,
                                                    @NonNull final product model) {
holder.productname.setText(model.getName());
holder.productprice.setText("Price = "+ model.getPrice()+"$");
holder.productdescription.setText(model.getDescription());
Picasso.get().load(model.getImage()).into(holder.imageView);

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(type.equals("Admin")){

            Intent op=new Intent(HomeActivity.this,AdminMaintainProducts.class);
            op.putExtra("pid",model.getPid());
            startActivity(op);
        }
        else{
            Intent op=new Intent(HomeActivity.this,ProductDetailsActivity.class);
            op.putExtra("pid",model.getPid());
            startActivity(op);
        }

    }
});

                    }

                    @NonNull
                    @Override
                    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view= LayoutInflater.from(viewGroup.getContext()).inflate
                                (R.layout.product_items_display,viewGroup,false);
                        ProductViewholder holder= new ProductViewholder(view);

                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!type.equals("Admin")) {
                if (backPressTime + 2000 > System.currentTimeMillis()) {
                    backToast.cancel();
                    //super.onBackPressed();
                    // finish();
                    // return;
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                } else {
                    backToast = Toast.makeText(HomeActivity.this, "Press back again to exit", Toast.LENGTH_SHORT);
                    backToast.show();
                }

                backPressTime = System.currentTimeMillis();
            }
            else if(type.equals("Admin")){
                Intent op=new Intent(HomeActivity.this,Adminaddproduct.class);
                startActivity(op);
            }


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
        //if (id == R.id.action_settings) {
          //  return true;
       // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cart) {
            // Handle the camera action
            if(!type.equals("Admin")){
                Intent ip=new Intent(HomeActivity.this,CartActivity.class);
                startActivity(ip);
            }

        } else if (id == R.id.category) {

                Intent i=new Intent(HomeActivity.this,cetagories.class);
                startActivity(i);

        } else if (id == R.id.Search) {
            Intent i=new Intent(HomeActivity.this,SreachProductActivity.class);
            startActivity(i);


        } else if (id == R.id.setting) {
            if(!type.equals("Admin")){
                Intent i=new Intent(HomeActivity.this,Settings.class);
                startActivity(i);
            }

        } else if (id == R.id.logout) {
            if(!type.equals("Admin")){
                Paper.book().destroy();
                Intent it=new Intent(HomeActivity.this,MainActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);

            }
            }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
