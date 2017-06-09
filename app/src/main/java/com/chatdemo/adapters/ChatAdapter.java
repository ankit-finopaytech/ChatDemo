package com.chatdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatdemo.R;
import com.chatdemo.model.ChatHistoryBean;
import com.chatdemo.utils.AppConstants;
import com.chatdemo.utils.UtilFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit_aggarwal on 08-06-2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context context;
    private List<ChatHistoryBean> chatList;

    public ChatAdapter(Context context, List<ChatHistoryBean> chatList){
        this.context=context;
        this.chatList=chatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.chat_list_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ChatHistoryBean chatHistoryBean=chatList.get(holder.getAdapterPosition());

        if (chatHistoryBean.getMessageSource()!=null && chatHistoryBean.getMessageSource().equalsIgnoreCase(AppConstants.YOU)){
            holder.tvMszSender.setText(chatHistoryBean.getMessage());
            holder.tvTimeSender.setText(UtilFunctions.convertTimestamp(chatHistoryBean.getDate()));
            holder.linearRight.setVisibility(View.VISIBLE);
            holder.linearLeft.setVisibility(View.GONE);

        }else {
            holder.tvMszReceiver.setText(chatHistoryBean.getMessage());
            holder.tvTimeReceiver.setText(UtilFunctions.convertTimestamp(chatHistoryBean.getDate()));

            holder.linearRight.setVisibility(View.GONE);
            holder.linearLeft.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public void updateList(ArrayList<ChatHistoryBean> chatList){
        this.chatList=chatList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMszReceiver;
        private TextView tvTimeReceiver;
        private TextView tvMszSender;
        private TextView tvTimeSender;
        private View itemView;
        private LinearLayout linearRight,linearLeft;

        public MyViewHolder(View view) {
            super(view);
            itemView=view;
            tvMszReceiver = (TextView) view.findViewById(R.id.tv_msz_receiver);
            tvTimeReceiver = (TextView) view.findViewById(R.id.tv_time_receiver);
            tvMszSender = (TextView) view.findViewById(R.id.tv_msz_sender);
            tvTimeSender = (TextView) view.findViewById(R.id.tv_time_sender);
            linearRight= (LinearLayout)view.findViewById(R.id.linear_right);
            linearLeft= (LinearLayout)view.findViewById(R.id.linear_left);
        }
    }
}
