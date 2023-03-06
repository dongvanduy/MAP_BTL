package com.example.quanlysinhvien.fragment;

import static com.example.quanlysinhvien.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlysinhvien.LoadingProgress;
import com.example.quanlysinhvien.MainActivity;
import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.User;
import com.example.quanlysinhvien.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class MyProfileFragment extends Fragment{
    private View mView;
    private ImageView imgAvatar;
    private EditText edtFullName, edtEmail;
    private Button btnUpdateProfile;
    private Uri mUri;
    private MainActivity mMainActivity;
    LoadingProgress loadingProgress;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        initUi();
        mMainActivity = (MainActivity) getActivity();
        setUserInformation();
        initListener();
        return mView;

    }

    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });

    }

    public void setUri(Uri mUri){
        this.mUri = mUri;
    }
    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String strFullName = edtFullName.getText().toString().trim();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(strFullName).setPhotoUri(mUri).build();
        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Update profile success!", Toast.LENGTH_SHORT).show();
                    mMainActivity.showUserInformation();
                }
            }
        });
    }

    private void onClickRequestPermission() {
        if(mMainActivity == null){return;}
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            mMainActivity.openGallery();
            return;
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            mMainActivity.openGallery();
        }else{
            String[] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permisstions,MY_REQUEST_CODE);
        }
    }
    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {

            return;
        }
        edtFullName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatardefault).into(imgAvatar);
    }

    private void initUi(){
        imgAvatar = mView.findViewById(R.id.img_avatar_profile);
        edtFullName = mView.findViewById(R.id.edt_name_profile);
        edtEmail = mView.findViewById(R.id.edt_email_profile);
        btnUpdateProfile = mView.findViewById(R.id.btn_update_profile);
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        imgAvatar.setImageBitmap(bitmapImageView);
    }
}
