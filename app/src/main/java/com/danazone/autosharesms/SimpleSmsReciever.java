package com.danazone.autosharesms;

/**
 * Created by ismail on 7/12/17.
 */

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.*;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// Todo : SMS Reciever Class

public class SimpleSmsReciever extends BroadcastReceiver {
    private DBManager dbManager;
    private static final String TAG = "Message recieved";
    private List<Phone> phone = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        dbManager = new DBManager(context);
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
        SmsManager sms = SmsManager.getDefault();

        if (messages.getOriginatingAddress().equals("Facebook")) {

//            Intent smsIntent = new Intent(context, SMS_Receive.class);
//            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                smsIntent.putExtra("MessageNumber", messages.getOriginatingAddress());
//                smsIntent.putExtra("Message", messages.getMessageBody());
//                context.startActivity(smsIntent);

            phone = dbManager.getAllPhone();
            for (int i = 0; i < phone.size(); i++) {
                sms.sendTextMessage(phone.get(i).getPhone(), null, messages.getMessageBody(), null, null);
            }
        }
    }

}
