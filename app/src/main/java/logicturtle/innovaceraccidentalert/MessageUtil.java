package logicturtle.innovaceraccidentalert;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by kunwar on 29/10/17.
 */

public class MessageUtil {

    private static final String SMS = "sms";



    public MessageUtil() {
    }


    public static void sendMessage(String number, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
        } catch (Exception e) {
            Log.d("kunwar", e.getMessage());
        }
    }
}
