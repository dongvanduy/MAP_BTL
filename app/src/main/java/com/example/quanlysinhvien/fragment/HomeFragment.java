package com.example.quanlysinhvien.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysinhvien.DialogFormAdd;
import com.example.quanlysinhvien.LoadingProgress;
import com.example.quanlysinhvien.MainActivity;
import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.User;
import com.example.quanlysinhvien.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{
    private RecyclerView rcvUser;
    private UserAdapter mUserAdapter;
    private List<User> mListUser;
    private FloatingActionButton fabAdd;
    private LoadingProgress loadingProgress;
    @Nullable
    @Override

    //thực hiện tạo giao diện(view), trả về view là giao diện file xml tương ứng fragment
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvUser = (RecyclerView)view.findViewById(R.id.rcv_user);
        fabAdd = (FloatingActionButton)view.findViewById(R.id.fab_add);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvUser.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(dividerItemDecoration);
        //
        //===========
        mListUser = new ArrayList<>();
        mUserAdapter = new UserAdapter(mListUser, new UserAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(User user) {
                openDialogUpdateItem(user);
            }
        });
        rcvUser.setAdapter(mUserAdapter);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFormAdd dialogFrom = new DialogFormAdd();
                dialogFrom.show(getActivity().getSupportFragmentManager(), "from");
            }
        });


        getListUserFromRealtimeDatabase();
        return view;
    }

    private void openDialogUpdateItem(User user) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);//khi click ra bên ngoài dialog sẽ k tắt đc


        EditText edtUpdateName = dialog.findViewById(R.id.edt_update_name);
        EditText edtUpdateVangHoc = dialog.findViewById(R.id.edt_update_vanghoc);
        Button btnUpdate = dialog.findViewById(R.id.btn_update);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        edtUpdateName.setText(user.getName());//hiển thị name ban đầu
        edtUpdateVangHoc.setText(String.valueOf(user.getVanghoc()));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("list_user");
                String newName = edtUpdateName.getText().toString().trim();//Giá tị người dùng muốn update
                int newVangHoc = Integer.parseInt(edtUpdateVangHoc.getText().toString());
                user.setName(newName);
                user.setVanghoc(newVangHoc);
                myRef.child(String.valueOf(user.getId()))
                        .updateChildren(user.toMap(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getContext(), "Update data success", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();//đóng dialog
                            }
                        });
            }
        });
        dialog.show();//Hiển thị lên màn hình
    }
    private void getListUserFromRealtimeDatabase() {
        loadingProgress = new LoadingProgress();
        loadingProgress.show(getActivity().getSupportFragmentManager(), "wait");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user");
        Query query = myRef.orderByValue();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    mListUser.add(user);
                    mUserAdapter.notifyDataSetChanged();
                    loadingProgress.dismiss();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user == null || mListUser == null || mListUser.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListUser.size(); i++) {
                    if (user.getId() == mListUser.get(i).getId()) {
                        mListUser.set(i, user);
                    }
                }
                mUserAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
