package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Model.Users;
import hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication.Prevalet.prevalet;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button b,b1;
    ProgressDialog loadingBar;
    String parentDB="Users";
     private String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it=getIntent();
        Bundle bundle=it.getExtras();
        if(bundle!=null){
            type=getIntent().getExtras().get("Admin").toString();
        }
        setContentView(R.layout.activity_main);
        b= findViewById(R.id.button);
        b1= findViewById(R.id.button2);
        loadingBar=new ProgressDialog(MainActivity.this);
        Paper.init(this);
        b.setOnClickListener(this);
        b1.setOnClickListener(this);
        String userphonekey=Paper.book().read(prevalet.UserPhoneKey);
        String userpassword=Paper.book().read(prevalet.UserPassword);
        if(userphonekey!= "" && userpassword!=""){
            if(!TextUtils.isEmpty(userphonekey)&& !TextUtils.isEmpty(userpassword)){
                AllowAccess(userphonekey,userpassword);
                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please Wait.........");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }

        }
        //onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (type.equals("Admin")) {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

        }
    }

        private void AllowAccess ( final String phone, final String password){
            final DatabaseReference Rootref;
            Rootref = FirebaseDatabase.getInstance().getReference();
            Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(parentDB).child(phone).exists()) {
                        Users userdata = dataSnapshot.child(parentDB).child(phone).getValue(Users.class);
                        if (userdata.getPhone().equals(phone)) {
                            if (userdata.getPassword().equals(password)) {
                                Toast.makeText(MainActivity.this, "Logged In Succesfully",
                                        Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent it = new Intent(MainActivity.this, HomeActivity.class);
                                prevalet.Online_users = userdata;
                                startActivity(it);
                            }
                        }
                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent it=new Intent(MainActivity.this,Signup.class);
                startActivity(it);

                break;
            case R.id.button2:
                Intent i=new Intent(MainActivity.this, Login.class);
                startActivity(i);
                break;
        }
    }}
