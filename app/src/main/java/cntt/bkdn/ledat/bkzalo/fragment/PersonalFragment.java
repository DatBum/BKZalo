package cntt.bkdn.ledat.bkzalo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import cntt.bkdn.ledat.bkzalo.model.User;

public class PersonalFragment extends Fragment {
    private TextView tvName,tvPass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.personal_fragment, container, false);
        tvName=(TextView) view.findViewById(R.id.name);
        tvPass=(TextView) view.findViewById(R.id.pass);
        tvName.setText(getActivity().getIntent().getStringExtra("name"));
        tvPass.setText(getActivity().getIntent().getStringExtra("pass"));
        return view;
    }




}
