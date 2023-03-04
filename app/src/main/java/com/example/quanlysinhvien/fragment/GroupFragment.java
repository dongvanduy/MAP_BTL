package com.example.quanlysinhvien.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysinhvien.ChatActivity;
import com.example.quanlysinhvien.ListMember;
import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.SignInActivity;
import com.example.quanlysinhvien.SignUpActivity;
import com.example.quanlysinhvien.User;
import com.example.quanlysinhvien.UserAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ChatFragment.initStudentData();
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        List<Integer> listKey = new ArrayList<>();
        listKey.add(R.id.layout_group_1);
        listKey.add(R.id.layout_group_2);
        listKey.add(R.id.layout_group_3);
        listKey.add(R.id.layout_group_4);
        listKey.add(R.id.layout_group_5);
        listKey.add(R.id.layout_group_6);
        listKey.add(R.id.layout_group_7);
        listKey.add(R.id.layout_group_8);
        listKey.add(R.id.layout_group_9);

        for (Integer i =0;i< listKey.size();i++) {
            LinearLayout layout = view.findViewById(listKey.get(i));
            Integer finalI = i+1;

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ListMember.class);
                    List<User> users = new ArrayList<>();
                    if(ChatFragment.snapshotStudents != null){
                        for (DataSnapshot d : ChatFragment.snapshotStudents.getChildren()){
                            if(d.child("group") != null
                                    && d.child("group").getValue() != null
                                    && Integer.valueOf(d.child("group").getValue().toString()).equals(finalI)){
                                users.add(new User(Integer.valueOf(d.child("id").getValue().toString()),
                                        (String)d.child("name").getValue(),
                                        Integer.valueOf(d.child("vanghoc").getValue().toString()),
                                        Integer.valueOf(d.child("group").getValue().toString())));
                            }

                        }

                        intent.putExtra("members",(Serializable) users);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getContext(), "MSSV không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



        return view;
    }
}
