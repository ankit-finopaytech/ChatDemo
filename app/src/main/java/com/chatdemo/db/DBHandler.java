package com.chatdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chatdemo.model.ChatHistoryBean;

import java.util.ArrayList;

/**
 * Created by ankit_aggarwal on 08-06-2017.
 */

public class DBHandler extends SQLiteOpenHelper {


    private static final String TAG = "DBHandler";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hod_life";

    // Table Names
    private static final String TABLE_CHAT_HISTORY = "chat_history";


    private static final String CREATE_TABLE_CHAT_HISTORY = "CREATE TABLE " + TABLE_CHAT_HISTORY
            + "(  ID  INTEGER PRIMARY KEY, MESSAGE  TEXT, MESSAGE_TIME  DATETIME, MESSAGE_SOURCE TEXT" + ")";

    private Context context;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CHAT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean saveMessage(ChatHistoryBean chatHistoryBean) {
        boolean isInserted = false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MESSAGE", chatHistoryBean.getMessage());
        values.put("MESSAGE_SOURCE", chatHistoryBean.getMessageSource());
        values.put("MESSAGE_TIME", chatHistoryBean.getDate());
        long insertNumber = db.insert(TABLE_CHAT_HISTORY, null, values);
        if (insertNumber > 0) {
            isInserted = true;
        }
        return isInserted;
    }


    public ArrayList<ChatHistoryBean> getChatList() {
        ArrayList<ChatHistoryBean> chatList = new ArrayList<ChatHistoryBean>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHAT_HISTORY;

        Log.i(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                ChatHistoryBean chatHistoryBean = new ChatHistoryBean();
                chatHistoryBean.setId(c.getInt((c.getColumnIndex("ID"))));
                chatHistoryBean.setMessage((c.getString(c.getColumnIndex("MESSAGE"))));
                chatHistoryBean.setMessageSource(c.getString(c.getColumnIndex("MESSAGE_SOURCE")));
                chatHistoryBean.setDate((c.getString(c.getColumnIndex("MESSAGE_TIME"))));
                chatList.add(chatHistoryBean);
            } while (c.moveToNext());
        }

        return chatList;
    }

}
