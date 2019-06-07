package hsoftz.calculatormuhammadhassaansiddiqi.ecommerceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {
    private EditText Inputphone,Inputpassword;
    private Button login;
    ProgressDialog loadingBar;
    String parentDB="Users";
    private CheckBox checkBox;
    TextView admin,notadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkBox=(CheckBox)findViewById(R.id.Remberme);
        Inputphone=(EditText)findViewById(R.id.phoneno);
        Inputpassword=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        admin=(TextView)findViewById(R.id.admin);
        login.setVisibility(View.INVISIBLE);
        notadmin=(TextView)findViewById(R.id.textView4);

        loadingBar=new ProgressDialog(Login.this);
        Paper.init(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginusers();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
login.setText("Login Admin");
admin.setVisibility(View.INVISIBLE);
notadmin.setVisibility(View.VISIBLE);
parentDB="Admin";
checkBox.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
            }
        });
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setText("Login User");
                admin.setVisibility(View.VISIBLE);
                notadmin.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                parentDB="Users";
                login.setVisibility(View.VISIBLE);
            }
        });

    }

    private void loginusers() {
        String phone = Inputphone.getText().toString().trim();
        String password = Inputpassword.getText().toString().trim();
         if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else{
             loadingBar.setTitle("Login Account");
             loadingBar.setMessage("Please wait, while we are checking the credentials.");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();
             AllowAccessToAccount(phone,password);
         }
        Inputpassword.setText("");
        Inputphone.setText("");
    }

    private void AllowAccessToAccount(final String phone, final String password) {
        if(checkBox.isChecked()){

            Paper.book().write(prevalet.UserPhoneKey,phone);
            Paper.book().write(prevalet.UserPassword,password);
        }
        final DatabaseReference Rootref;
        Rootref=FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDB).child(phone).exists()){
                    Users userdata=dataSnapshot.child(parentDB).child(phone).getValue(Users.class);
                    if(userdata.getPhone().equals(phone)){
                        if(userdata.getPassword().equals(password)){
                           if(parentDB.equals("Admin")){
                               Toast.makeText(Login.this, "Logged In Succesfully As Admin", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();
                               Intent it=new Intent(Login.this,Adminaddproduct.class);
                               startActivity(it);
                           }
                           else if(parentDB.equals("Users")){
                               Toast.makeText(Login.this, "Logged In Succesfully As A Users", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();
                               Intent it=new Intent(Login.this,HomeActivity.class);
                               prevalet.Online_users=userdata;
                               startActivity(it);
                           }
                        }
                    }
                }
              else{
                  loadingBar.dismiss();
                    Toast.makeText(Login.this, "your Credentials is incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
