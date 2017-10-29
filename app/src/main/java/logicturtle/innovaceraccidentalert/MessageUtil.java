package logicturtle.innovaceraccidentalert;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by kunwar on 29/10/17.
 */

public class MessageUtil {

    private static final String SMS = "sms";



    public MessageUtil() {
    }


    public static void sendIndividualMessage(String number, Context context) {
        Uri uri = Uri.fromParts(SMS, number, null);
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(smsIntent);
    }
}
