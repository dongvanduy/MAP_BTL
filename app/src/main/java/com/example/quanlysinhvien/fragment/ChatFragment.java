package com.example.quanlysinhvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlysinhvien.ChatActivity;
import com.example.quanlysinhvien.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatFragment extends Fragment {
    public DatabaseReference myRef;
    public static DataSnapshot snapshotStudents;

    public static void initStudentData () {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("list_user");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshotStudents = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        initStudentData();

        EditText input =   view.findViewById(R.id.mssv_input);
        Button button = view.findViewById(R.id.submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(snapshotStudents.hasChild(input.getText().toString())){
                    String[] s = snapshotStudents.child(input.getText().toString())
                            .child("name").getValue().toString().split(" ");
                    ChatActivity.mssv = input.getText().toString();
                    ChatActivity.name = s[s.length-1];
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "MSSV không tồn tại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
