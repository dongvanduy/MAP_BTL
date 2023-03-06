package com.example.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListMember extends AppCompatActivity {

    private RecyclerView rcvMember;
    private MemberAdapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_member);
        List<User> listUser = (List<User>) getIntent().getExtras().get("members");
        listUser.size();
        rcvMember = findViewById(R.id.rcv_member);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       rcvMember.setLayoutManager(linearLayoutManager);
       memberAdapter = new MemberAdapter(listUser);
       rcvMember.setAdapter(memberAdapter);
    }

}