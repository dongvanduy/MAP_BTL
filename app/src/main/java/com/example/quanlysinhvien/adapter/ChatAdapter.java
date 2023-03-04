package com.example.quanlysinhvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien.ChatActivity;
import com.example.quanlysinhvien.R;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<Message> arrayList;

    public ChatAdapter(Context context, List<Message> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setArrayList(List<Message> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Message chatItem = arrayList.get(i);
        String name = chatItem.name != null ? chatItem.name + ": " : "";

        if(chatItem.userId.equals(ChatActivity.mssv)){
            view = inflater.inflate(R.layout.chat_item_send,null);
            name = "";
        }else{
            view = inflater.inflate(R.layout.chat_item_receive,null);
        }


        TextView contentText =  view.findViewById(R.id.content_message);
        contentText.setText(name + chatItem.content);

//        if(sinhVienItem.fullname != null){
//            textName.setText(sinhVienItem.fullname);
//        }else{
//            textName.setText(sinhVienItem.name);
//        }


        return view;
    }
}
