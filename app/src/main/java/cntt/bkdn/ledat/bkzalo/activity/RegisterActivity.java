package cntt.bkdn.ledat.bkzalo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.model.User;

import static android.widget.Toast.LENGTH_LONG;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btRegister;
    private String username, password;
    private Firebase root;
    public  List<User> users;
    private  Date time;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        Firebase.setAndroidContext(this);
      init();
       getData();
       handle();
        }
    private void init() {
        edtUsername = (EditText) findViewById(R.id.edt_register_username);
        edtPassword = (EditText) findViewById(R.id.edt_register_password);
        btRegister = (Button) findViewById(R.id.bt_register);
        root = new Firebase("https://bkzalo-c0c49.firebaseio.com/");
        users= new ArrayList<>();

    }
    private  void getData(){
    root.child("Users").addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            User user= dataSnapshot.getValue(User.class);
            users.add(user);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
        }
    });
}
    private void handle() {
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();
            if(!username.isEmpty()&&!password.isEmpty()){
                time=new Date();
                User user = new User(time.toString(),username,password);
                root.child("Users").child(time.toString()).setValue(user);
                showDialog();
            } else if(username.isEmpty()&&!password.isEmpty()){
                Toast.makeText(getApplicationContext(),"Username không được để trống!",Toast.LENGTH_LONG).show();
                } else if(!username.isEmpty()&&password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Password không được để trống!",Toast.LENGTH_LONG).show();
                    } else if(!username.isEmpty()&&!password.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Tài khoản đã tồn tại",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"UserName và password không được để trống!",Toast.LENGTH_LONG).show();
                        }
            }

        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Bạn đã đăng ký thành công!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        builder.create().show();
    }

    public boolean isExist(String name) {
            for (int i = 0; i < users.size(); i++) {
                if (name.equals(users.get(i).getName())) {
                   return  true;
                }
            }
        return false;
    }

}
