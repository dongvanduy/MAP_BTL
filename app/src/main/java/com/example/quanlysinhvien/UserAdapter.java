package com.example.quanlysinhvien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<User> mListUser;
    private IClickListener mIiClickListener;

    public UserAdapter(List<User> mListUser) {
        this.mListUser = mListUser;
    }

    public interface  IClickListener{
        void onClickUpdateItem(User user);
    }

    public UserAdapter(List<User> mListUser, IClickListener listener) {
        this.mListUser = mListUser;
        this.mIiClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if(user == null)return;
        holder.tvId.setText("ID: "+ user.getId());
        holder.tvName.setText("Name: " + user.getName());
        holder.tvVanghoc.setText("Số buổi vắng: " + user.getVanghoc());
        holder.tvgroup.setText("Nhóm: " + user.getGroup());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIiClickListener.onClickUpdateItem(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvId;
        private TextView tvName;
        private TextView tvVanghoc;
        private Button btnUpdate;
        private TextView tvgroup;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvVanghoc = itemView.findViewById(R.id.tv_vanghoc);
            tvName = itemView.findViewById(R.id.tv_name);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            tvgroup = itemView.findViewById(R.id.tv_group);

        }
    }
}
