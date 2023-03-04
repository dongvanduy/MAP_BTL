package com.example.quanlysinhvien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{
    private List<User> mListUser;
    private IClickListener mIiClickListener;

    public MemberAdapter(List<User> mListUser) {
        this.mListUser = mListUser;
    }

    public interface  IClickListener{
        void onClickUpdateItem(User user);
    }

    public MemberAdapter(List<User> mListUser, IClickListener listener) {
        this.mListUser = mListUser;
        this.mIiClickListener = listener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        User user = mListUser.get(position);
        if(user == null)return;
        holder.tvId.setText("ID: "+ user.getId());
        holder.tvName.setText("Name: " + user.getName());
        holder.tvVanghoc.setText("Số buổi vắng: " + user.getVanghoc());
        holder.tvgroup.setText("Nhóm: " + user.getGroup());
    }

    @Override
    public int getItemCount() {
        if(mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{
        private TextView tvId;
        private TextView tvName;
        private TextView tvVanghoc;
        private TextView tvgroup;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvVanghoc = itemView.findViewById(R.id.tv_vanghoc);
            tvName = itemView.findViewById(R.id.tv_name);
            tvgroup = itemView.findViewById(R.id.tv_group);

        }
    }
}