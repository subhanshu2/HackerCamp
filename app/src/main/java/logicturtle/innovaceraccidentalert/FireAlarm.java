package logicturtle.innovaceraccidentalert;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by user on 29-Oct-17.
 */

public class FireAlarm {

    Context context;
    MediaPlayer mp;


    public FireAlarm(Context context) {
        this.context = context;
    }

    public void startAlarm() {

        mp = MediaPlayer.create(context, R.raw.alarm);
        mp.setLooping(true);
        mp.start();
    }

    public void stopAlarm() {
        mp.stop();
        mp.pause();
    }






}
