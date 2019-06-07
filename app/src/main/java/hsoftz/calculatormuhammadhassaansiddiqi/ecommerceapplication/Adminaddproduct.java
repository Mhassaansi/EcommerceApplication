package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Adminaddproduct extends AppCompatActivity implements View.OnClickListener {
private ImageView mobile,laptop,tab,watch,tshirt,pant,boots,cap;
private Button Adminlogout,checknew,Manageprod;
    private long backPressTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddproduct);
        mobile=(ImageView)findViewById(R.id.mobile);
        laptop=(ImageView)findViewById(R.id.laptop);
        tab=(ImageView)findViewById(R.id.tab);
        watch=(ImageView)findViewById(R.id.watch);
        tshirt=(ImageView)findViewById(R.id.tshirt);
        pant=(ImageView)findViewById(R.id.pant);
        boots=(ImageView)findViewById(R.id.boot);
        cap=(ImageView)findViewById(R.id.cap);
        Adminlogout=(Button)findViewById(R.id.Adminlogout);
        checknew=(Button)findViewById(R.id.check_orders);
        Manageprod=(Button)findViewById(R.id.check_orders2);
        Adminlogout.setOnClickListener(this);
        Manageprod.setOnClickListener(this);
        checknew.setOnClickListener(this);
        mobile.setOnClickListener(this);
        laptop.setOnClickListener(this);
        tab.setOnClickListener(this);
        watch.setOnClickListener(this);
        tshirt.setOnClickListener(this);
        pant.setOnClickListener(this);
        boots.setOnClickListener(this);
        cap.setOnClickListener(this);

    }
    public void onBackPressed() {
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
            backToast = Toast.makeText(Adminaddproduct.this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
         case   R.id.mobile:
             Intent it=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
             it.putExtra("cetagory","Mobile");
             startActivity(it);
            break;
            case   R.id.cap:
                Intent in=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                in.putExtra("cetagory","Caps");
                startActivity(in);
                break;
            case   R.id.tshirt:
                Intent ii=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                ii.putExtra("cetagory","TShrits");
                startActivity(ii);
                break;
            case   R.id.tab:
                Intent ie=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                ie.putExtra("cetagory","Tablets");
                startActivity(ie);
                break;
            case   R.id.boot:
                Intent id=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                id.putExtra("cetagory","Boots");
                startActivity(id);
                break;
            case   R.id.watch:
                Intent ic=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                ic.putExtra("cetagory","Watches");
                startActivity(ic);
                break;
            case   R.id.laptop:
                Intent ib=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                ib.putExtra("cetagory","Laptops");
                startActivity(ib);
                break;
            case   R.id.pant:
                Intent ia=new Intent(Adminaddproduct.this,AdminAddNewProductActivity.class);
                ia.putExtra("cetagory","Pants");
                startActivity(ia);
                break;

            case R.id.Adminlogout:
                Intent a=new Intent(Adminaddproduct.this,MainActivity.class);
             a.addFlags(a.FLAG_ACTIVITY_CLEAR_TASK|a.FLAG_ACTIVITY_CLEAR_TASK);
                a.putExtra("Admin","Admin");
                startActivity(a);
                //finish();
                break;
            case R.id.check_orders:
                Intent i=new Intent(Adminaddproduct.this,AdminNeworder.class);
                startActivity(i);
                break;
            case R.id.check_orders2:
                Intent ip=new Intent(Adminaddproduct.this,HomeActivity.class);
                ip.putExtra("Admin","Admin");
                startActivity(ip);
                break;
        }

    }
}
