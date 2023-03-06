package com.example.quanlysinhvien;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFormAdd extends DialogFragment {
    private EditText edtName;
    private EditText edtId;
    private EditText edtVang;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String name;
    private int soBuoiVang, id;
    private Button btnAdd;
    private Button btnCancel;

    public DialogFormAdd(int contentLayoutId, String name, int soBuoiVang, int id) {
        super(contentLayoutId);
        this.name = name;
        this.soBuoiVang = soBuoiVang;
        this.id = id;
    }

    public DialogFormAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_add, container, false);
        edtName = v.findViewById(R.id.edt_add_name);
        edtVang = v.findViewById(R.id.edt_add_vang);
        edtId = v.findViewById(R.id.edt_add_id);
        btnAdd = v.findViewById(R.id.btn_add);
        btnCancel = v.findViewById(R.id.btn_cancel_add);
        edtName.setText(name);
        edtVang.setText(String.valueOf(soBuoiVang));
        edtId.setText(String.valueOf(id));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = edtName.getText().toString();
                int _vang = Integer.parseInt(edtVang.getText().toString().trim());
                int _id = Integer.parseInt(edtId.getText().toString().trim());

                if(TextUtils.isEmpty(_name)){
                    input((EditText)edtName, "Họ Tên" );
                }else if(TextUtils.isEmpty(String.valueOf(_vang))){
                    input((EditText)edtVang, "Số buổi vắng" );
                }else if(TextUtils.isEmpty(String.valueOf(_id))){
                    input((EditText)edtId, "MSSV" );
                }else{
                    database.child("list_user").push().setValue(new User(_id, _name, _vang)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(v.getContext(),"Add Success!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Add Fail!!", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        }
    });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dismiss();
            }
        });
        return v;
    }
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void input(EditText txt, String s){
        txt.setError(s + "Không nên để trống!!");
        txt.requestFocus();
    }
}
