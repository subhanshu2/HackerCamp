package logicturtle.innovaceraccidentalert;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 29-10-2017.
 */
public class AppService extends Service implements ShakeListener.OnShakeListener {
    private ShakeListener mShaker;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    public int check;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.d("ayush", "service created ");
        drowsinessDetection();
        mSensorManager = ((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        mAccelerometer = this.mSensorManager.getDefaultSensor(1);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(this);
        Toast.makeText(AppService.this, "Service is created!", Toast.LENGTH_LONG).show();
        Log.d(getPackageName(), "Created the Service!");
        check = 1;
    }

    @Override
    public void onShake() {
        if (check == 1) {
            Toast.makeText(AppService.this, "SHAKEN!", Toast.LENGTH_LONG).show();
            final Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(500);
            //sms call here.............
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!HackerCamp.isAppOnForeground(this)){

        }

        return super.onStartCommand(intent, flags, startId);

    }

    public void onDestroy() {
        super.onDestroy();
        check = 0;
        Log.d(getPackageName(), "Service Destroyed.");
    }

    private void drowsinessDetection() {
        //firebase call + alarm raise...............
    }
}
