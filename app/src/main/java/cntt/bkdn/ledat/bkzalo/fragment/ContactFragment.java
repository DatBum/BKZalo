package cntt.bkdn.ledat.bkzalo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import cntt.bkdn.ledat.bkzalo.R;
import cntt.bkdn.ledat.bkzalo.activity.ChatActivity;
import cntt.bkdn.ledat.bkzalo.activity.LoginActivity;
import cntt.bkdn.ledat.bkzalo.activity.RegisterActivity;
import cntt.bkdn.ledat.bkzalo.adapter.ContactAdapter;
import cntt.bkdn.ledat.bkzalo.model.Message;
import cntt.bkdn.ledat.bkzalo.model.User;

public class ContactFragment extends Fragment {
    public static   ArrayList<User> users;
    public static    ArrayList<Message> smss;
    private ContactAdapter adapter;
    private ListView lvContact;
    private Firebase firebase;
    public String currentId;
    private ArrayList<String> keys;
    private ChildEventListener childEventListenerMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.contact_fragment, container, false);
        Firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://bkzalo-c0c49.firebaseio.com/");
        init(view);
        handle();
        return view;
    }

    public void init(View view) {
        keys= new ArrayList<>();
        users= new ArrayList<>();
        smss= new ArrayList<>();
        lvContact = (ListView) view.findViewById(R.id.lv_contact);
        Intent intent= getActivity().getIntent();
        if(intent!=null){
            currentId=intent.getStringExtra("currentId");

        }

        firebase.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user= dataSnapshot.getValue(User.class);
                if(!currentId.equals(dataSnapshot.getValue(User.class).getId())) {
                    users.add(user);
                }
                Log.i("userrrrrrrrrrr",users.size()+"");
                adapter = new ContactAdapter(users, getActivity());
                lvContact.setAdapter(adapter);

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
                String key =dataSnapshot.getKey();
                keys.add(key);
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

    public void handle(){
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goTocChatActivity = new Intent(getActivity(),ChatActivity.class);
                goTocChatActivity.putExtra("clientId",users.get(position).getId());
                goTocChatActivity.putExtra("nameClient",users.get(position).getName());
                goTocChatActivity.putExtra("currentId",currentId);
                goTocChatActivity.putExtra("currentName",getActivity().getIntent().getStringExtra("name"));
                startActivity(goTocChatActivity);
            }
        });

        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa");
                builder.setMessage("Bạn có chắc muốn xóa người này không?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idReceiveUser= users.get(position).getId();
                        firebase.child("Users").child(idReceiveUser).removeValue();
                        for(int i=0;i<smss.size();i++){
                            if(smss.get(i).getIdUserReceive().equals(idReceiveUser)&&smss.get(i).getIdUserSend().equals(LoginActivity.currentUser)){
                                firebase.child("Messages").child(keys.get(i)).removeValue();
                            }
                        }
                        users.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }


}
