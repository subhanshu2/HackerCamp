package logicturtle.innovaceraccidentalert.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import logicturtle.innovaceraccidentalert.R;

/**
 * Created by user on 29-Oct-17.
 */

public class AlarmService extends Service {

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        mp = MediaPlayer.create(this, R.raw.alarm);
        mp.setLooping(true);
    }
    public void onDestroy()
    {
        mp.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }



    public void stopAlarm(){

        mp.stop();

    }

    public void startAlarm(){
        mp.start();
    }
}
