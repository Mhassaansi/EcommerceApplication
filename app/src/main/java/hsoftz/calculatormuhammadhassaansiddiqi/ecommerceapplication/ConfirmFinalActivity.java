package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Prevalet.prevalet;

public class ConfirmFinalActivity extends AppCompatActivity {
private Button confirm;
private EditText city,name,address,phone;
String toatalamount="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final);
        city=(EditText)findViewById(R.id.finalcity);
        name=(EditText)findViewById(R.id.finalname);
        phone=(EditText)findViewById(R.id.finalphone);
        address=(EditText)findViewById(R.id.fianladdress);
        confirm=(Button)findViewById(R.id.confirm);
        toatalamount=getIntent().getStringExtra("Total Price");
        //Toast.makeText(this,"Total Price $"+toatalamount,Toast.LENGTH_SHORT.show());
confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        check();
    }
});


    }

    private void check() {
        if(TextUtils.isEmpty(city.getText().toString())){
            Toast.makeText(this, "Kindly Enter City:", Toast.LENGTH_SHORT).show();
        }
      else  if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "Kindly Enter Your Name:", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(this, "Kindly Enter your adress:", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this, "Kindly Enter your Phone Number:", Toast.LENGTH_SHORT).show();
        }
        else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String savecurrentdate,savecurrenttime;
        Calendar calender=Calendar.getInstance();
        SimpleDateFormat simpledate=new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=simpledate.format(calender.getTime());
        SimpleDateFormat simpletime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=simpletime.format(calender.getTime());
        final DatabaseReference orderRef= FirebaseDatabase.getInstance().
                getReference().child("Orders").child(prevalet.Online_users.getPhone());
       final String namo=name.getText().toString();
      final   String cit=city.getText().toString();
      final   String adros=address.getText().toString();
       final String phono=phone.getText().toString();

        HashMap<String,Object> order=new HashMap<>();
        order.put("totalAmount",toatalamount);
        order.put("adress",adros);
        order.put("name",namo);
        order.put("city",cit);
        order.put("date",savecurrentdate);
        order.put("time",savecurrenttime);
        order.put("phone",phono);
        order.put("state","Not Shipped");
        orderRef.updateChildren(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()){
                   FirebaseDatabase.getInstance().getReference().child("Cart List")
                           .child("User View").child(prevalet.Online_users.getPhone())
                           . removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful()){
    Toast.makeText(ConfirmFinalActivity.this,
            "Your Final Order Has been Palced Succesfully:", Toast.LENGTH_SHORT).show();

    Intent it=new Intent(
            ConfirmFinalActivity.this,HomeActivity.class);
    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(it);
    finish();
}
                       }
                   });
               }
            }
        });


    }
}
