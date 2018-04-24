package Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Receiver pour l'event qui regarde le changement de la batterie
        if (intent.getAction() == "android.intent.action.BATTERY_CHANGED") {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = (level / (float) scale) * 100;

            if (batteryPct == 20) {
                String log = String.valueOf(batteryPct * 100);

                Toast.makeText(context, log, Toast.LENGTH_LONG).show();
            }
        }

        // Receiver pour l'event qui reçoit les sms
        if (intent.getAction() == "android.provider.Telephony.SMS_RECEIVED") {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] objects = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[objects.length];

                for (int i = 0; i < objects.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
                }

                String log = messages[0].getMessageBody();

                Toast.makeText(context, log, Toast.LENGTH_LONG).show();
            }
        }
    }
}
