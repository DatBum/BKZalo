package cntt.bkdn.ledat.bkzalo.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.model.Message;
import cntt.bkdn.ledat.bkzalo.model.User;
import de.greenrobot.event.EventBus;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btLogin;
    private TextView tvRegister;
    private String username, password;
    public static   List<User> users;
    private int check = 0;
    public static int id;
    private Firebase firebase;
    public static String currentUser;
    public static ArrayList<Message> smss;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        getData();
        handle();}


    private void init() {

        edtUsername = (EditText) findViewById(R.id.edt_user);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        users = new ArrayList<>();
        smss = new ArrayList<>();
    }

    private  void getData(){
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://bkzalo-c0c49.firebaseio.com/");
        firebase.child("Users").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user= dataSnapshot.getValue(User.class);
                users.add(user);
                handle();
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
      firebase.child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message messages = dataSnapshot.getValue(Message.class);
                smss.add(messages);
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
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();
            if (posCurrent(username,password)!=-1) {
                Intent gotoMainActivity = new Intent(LoginActivity.this,MainActivity.class);
                gotoMainActivity.putExtra("currentId",users.get(posCurrent(username,password)).getId());
                currentUser=users.get(posCurrent(username,password)).getId();
                gotoMainActivity.putExtra("name",users.get(posCurrent(username,password)).getName());
                gotoMainActivity.putExtra("pass",users.get(posCurrent(username,password)).getPass());
                startActivity(gotoMainActivity);
            } else if(checkInternet()==false){
                Toast.makeText(getApplicationContext(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Tài khoản không chính xác",Toast.LENGTH_SHORT).show();
            }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xxxxxxxxxxxx%","roi");
                if(checkInternet()==true){
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public int posCurrent(String name, String pass) {
        if(users!=null){
            for (int i = 0; i < users.size(); i++) {
                if (name.equals(users.get(i).getName()) && pass.equals(users.get(i).getPass())) {
                    return i;
                }
            }
        }
        return -1;
    }
    public boolean checkInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    public  static void getUser_Sms(){

    }
}
