package com.danazone.autosharesms;


import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
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
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            dbManager = new DBManager(context);
            Bundle pudsBundle = intent.getExtras();
            Object[] pdus = (Object[]) pudsBundle.get("pdus");
            SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
            SmsManager sms = SmsManager.getDefault();

            if (messages.getOriginatingAddress().equalsIgnoreCase(SessionManager.getInstance().getKeySaveName())) {

                phone = dbManager.getAllPhone();
                for (int i = 0; i < phone.size(); i++) {
                    sms.sendTextMessage(phone.get(i).getPhone(), null, messages.getMessageBody(), null, null);
                }
            }
        }
    }
}
