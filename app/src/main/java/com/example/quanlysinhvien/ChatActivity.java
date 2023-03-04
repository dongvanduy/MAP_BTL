package com.example.quanlysinhvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.quanlysinhvien.adapter.ChatAdapter;
import com.example.quanlysinhvien.adapter.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {
    public static String mssv;
    public static String name;
    private ChatAdapter chatAdapter;
    private GridView gridView;
    private Timer timerRefresh;
    public DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gridView = findViewById(R.id.area_chating);// fragmentChatBinding.areaChating;

        chatAdapter = new ChatAdapter(getBaseContext(), new ArrayList<>());
        gridView.setAdapter(chatAdapter);

        Button sendBtn = findViewById(R.id.send_btn);//fragmentChatBinding.sendBtn;
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.send_input);
                String content = input.getText().toString().trim();
                myRef.child(System.currentTimeMillis()+"")
                        .setValue(new HashMap<String,String>(){{
                            put("mssv",mssv);
                            put("name",name);
                            put("content",content);
                        }})
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Update data success", Toast.LENGTH_SHORT).show();
                                input.setText("");
                            }
                        });
            }
        });

        database.getReference("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messages = new ArrayList<>();
                for(DataSnapshot d : snapshot.getChildren()){
                    messages.add(new Message(d.child("content").getValue().toString(),
                                    d.child("mssv").getValue().toString(),
                                    d.child("name").getValue().toString()));
                }
                chatAdapter.setArrayList(messages);
                chatAdapter.notifyDataSetChanged();
                gridView.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



//    @Override
//    public void afterGetData(List<Message> list, boolean newData) {
//        if(newData){
//            chatAdapter = new ChatAdapter(getBaseContext(), list);
//            gridView.setAdapter(chatAdapter);
//        }
//    }
//
//    @Override
//    public void afterSend() {
//        fragmentChatBinding.sendInput.setText("");
//    }

}