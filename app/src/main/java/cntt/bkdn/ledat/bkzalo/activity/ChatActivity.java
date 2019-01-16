package cntt.bkdn.ledat.bkzalo.activity;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cntt.bkdn.ledat.bkzalo.Manifest;
import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.adapter.ChatAdapter;
import cntt.bkdn.ledat.bkzalo.model.Message;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEnter;
    private ImageView imvIcon, imvImage, imvCamera,imvCall, imvSend;
    private ImageView imvIconShow;
    private int[] icons = {R.id.icon_shy, R.id.icon_sad, R.id.icon_happy, R.id.icon_superise, R.id.icon_angry,
            R.id.icon_love, R.id.icon_cry, R.id.icon_died, R.id.icon_embarass, R.id.icon_sleepy};
    private List<String> iconList;
    private Firebase mFirebaseRef;
    private String sms;
    private ChatAdapter adapter;
    private List<Message> smss;
    private RecyclerView rcv;
    private Toolbar toolbar;
    private TextView nameClient;
    public static String currentId, clientId,curentName,clientName;
    private Date timeSend;
    private ChildEventListener childEventListenerMessage;
    private Message valueCurrent;
    private LinearLayout layoutIcons;
    private LinearLayout linearLayoutChat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initToolbar();
        initRecycleView();
        init();
        connectFirebase();
        handle();
        getData();

    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void initRecycleView() {
        rcv = (RecyclerView) findViewById(R.id.rcv_chat);
        smss = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        adapter = new ChatAdapter(getApplication(), smss);
        rcv.setAdapter(adapter);

    }

    public void init() {
        nameClient = (TextView) findViewById(R.id.name_client);
        edtEnter = (EditText) findViewById(R.id.edt_enter);
        imvSend = (ImageView) findViewById(R.id.imv_send);
        imvIcon = (ImageView) findViewById(R.id.imv_icons);
        imvImage = (ImageView) findViewById(R.id.imv_image);
        imvCamera = (ImageView) findViewById(R.id.imv_camera);
        layoutIcons = (LinearLayout) findViewById(R.id.icons);
        linearLayoutChat = (LinearLayout) findViewById(R.id.line_chat);
        iconList = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            findViewById(icons[i]).setOnClickListener(this);
        }

        Intent intent = getIntent();
        clientId = intent.getStringExtra("clientId");
        currentId = intent.getStringExtra("currentId");
        curentName = intent.getStringExtra("currentName");
        clientName = intent.getStringExtra("nameClient");
        nameClient.setText(intent.getStringExtra("nameClient"));
    }

    public void connectFirebase() {
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase("https://bkzalo-c0c49.firebaseio.com/");
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_angry:
                setIcon("><", R.drawable.angry);
                break;
            case R.id.icon_cry:
                setIcon(":'(", R.drawable.cry);
                break;
            case R.id.icon_died:
                setIcon("-_-", R.drawable.died);
                break;
            case R.id.icon_embarass:
                setIcon("@@", R.drawable.embarrass);
                break;
            case R.id.icon_happy:
                setIcon(":D", R.drawable.happy);
                break;
            case R.id.icon_love:
                setIcon("<3", R.drawable.love);
                break;
            case R.id.icon_sad:
                setIcon(":(", R.drawable.sad);
                break;
            case R.id.icon_shy:
                setIcon(":)", R.drawable.shy);
                break;
            case R.id.icon_sleepy:
                setIcon("-.-", R.drawable.sleep);
                break;
            case R.id.icon_superise:
                setIcon("0.0", R.drawable.superise);
                break;
        }
    }

    public void setIcon(String sign, int id) {
        layoutIcons.setVisibility(View.INVISIBLE);
        linearLayoutChat.setVisibility(View.VISIBLE);
        Drawable d = getResources().getDrawable(id);
        addIconInEditText(d, sign);
    }

    private void addIconInEditText(Drawable drawable, String sign) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        int selectionCursorPos = edtEnter.getSelectionStart();
        edtEnter.getText().insert(selectionCursorPos, sign);
        selectionCursorPos = edtEnter.getSelectionStart();
        SpannableStringBuilder builder = new SpannableStringBuilder(edtEnter.getText());
        int startPos = selectionCursorPos - sign.length();
        builder.setSpan(new ImageSpan(drawable), startPos, selectionCursorPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        edtEnter.setText(builder);
        edtEnter.setSelection(selectionCursorPos);
    }

    public void handle() {
        edtEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollRecycleView();
            }
        });

        imvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollRecycleView();
                if(edtEnter.getText().toString()!=null||edtEnter.getText().toString()!=""){
                    sms = edtEnter.getText().toString();
                    edtEnter.setText("");
                    timeSend = new Date();
                    valueCurrent = new Message(sms, currentId, clientId);
                    valueCurrent.setText(true);
                    mFirebaseRef.child("Messages").child(timeSend.toString()).setValue(valueCurrent);
                }


            }
        });

        imvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutIcons.setVisibility(View.VISIBLE);
                linearLayoutChat.setVisibility(View.INVISIBLE);
            }
        });

        imvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        imvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture,2);
            }
        });
    }
    public void putData() {


    }

    public void getData() {
        childEventListenerMessage = mFirebaseRef.child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message messages = dataSnapshot.getValue(Message.class);
              if((messages.getIdUserSend().equals(currentId)&&messages.getIdUserReceive().equals(clientId))||
                     (messages.getIdUserSend().equals(clientId)&&messages.getIdUserReceive().equals(currentId))  ) {
                  smss.add(messages);
                  adapter.notifyDataSetChanged();
             }
                scrollRecycleView();

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            setPhotoData(bitmap);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap selectedImage = (Bitmap)data.getExtras().get("data");
            setPhotoData(selectedImage);
        }
    }

    public void setPhotoData(Bitmap bm){
        timeSend = new Date();
        valueCurrent = new Message(getBitmapToByte(bm), currentId, clientId);
        valueCurrent.setPhoto(true);
        mFirebaseRef.child("Messages").child(timeSend.toString()).setValue(valueCurrent);
    }

    public static String getBitmapToByte(Bitmap bitmap) {
        String encoded = null;
        try {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }


    public void scrollRecycleView() {
        rcv.postDelayed(new Runnable() {
            @Override
            public void run() {
                rcv.scrollToPosition(rcv.getAdapter().getItemCount() - 1);
            }
        }, 1000);
    }


}
