package com.chatdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chatdemo.adapters.ChatAdapter;
import com.chatdemo.db.DBHandler;
import com.chatdemo.model.ChatHistoryBean;
import com.chatdemo.utils.AppConstants;

import java.util.ArrayList;

public class ChatWindowActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChatWindowActivity";

    private RelativeLayout bottomLayout;
    private ImageView ivSmiley;
    private ImageView ivAttachment;
    private ImageView ivCamera;
    private FloatingActionButton fabSubmit;
    private EditText etMessage;
    private RecyclerView rvChatList;
    private LinearLayoutManager linearLayoutManager;
    private Context context;
    private ArrayList<ChatHistoryBean> chatList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private String source;
    private String username;


    private void initViews() {
        bottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        ivSmiley = (ImageView) findViewById(R.id.iv_smiley);
        ivAttachment = (ImageView) findViewById(R.id.iv_attachment);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
        fabSubmit = (FloatingActionButton) findViewById(R.id.fab_submit);
        etMessage = (EditText) findViewById(R.id.et_message);
        rvChatList = (RecyclerView) findViewById(R.id.rv_chatList);
        linearLayoutManager = new LinearLayoutManager(this);
        rvChatList.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(context, chatList);
        rvChatList.setAdapter(chatAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        context = this;
        source=getIntent().getStringExtra(AppConstants.KEY_SOURCE);
        username=getIntent().getStringExtra(AppConstants.KEY_USERNAME);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(username);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViews();
        setListener();
        setData();
    }

    private void setData() {
        chatList=new DBHandler(context).getChatList();
       chatAdapter.updateList(chatList );
    }

    private void setListener() {
        fabSubmit.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_window_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab_submit: {
                String message = etMessage.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    ChatHistoryBean chatHistoryBean = new ChatHistoryBean();
                    chatHistoryBean.setDate(""+ System.currentTimeMillis());
                    chatHistoryBean.setMessage(message);
                    chatHistoryBean.setMessageSource(source);
                   if( new DBHandler(this).saveMessage(chatHistoryBean)){
                       chatList.add(chatHistoryBean);
                       chatAdapter.updateList(chatList);
                       etMessage.setText("");
                    }
                    linearLayoutManager.scrollToPosition(chatList.size()-1);
                }
            }
            break;
        }
    }
}
