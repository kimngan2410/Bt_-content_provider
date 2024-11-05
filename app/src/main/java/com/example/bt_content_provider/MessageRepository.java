package com.example.bt_content_provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {

    private Context context;

    public MessageRepository(Context context) {
        this.context = context;
    }

    public List<SmsMessage> fetchMessages() {
        List<SmsMessage> messages = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Telephony.Sms.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE));
                messages.add(new SmsMessage(address, body, date)); // Sử dụng SmsMessage thay vì Message
            }
            cursor.close();
        }
        return messages;
    }
}
