package logicturtle.innovaceraccidentalert.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import logicturtle.innovaceraccidentalert.Activity.HackerCamp;
import logicturtle.innovaceraccidentalert.Utils.FireAlarm;
import logicturtle.innovaceraccidentalert.Utils.MessageUtil;
import logicturtle.innovaceraccidentalert.Utils.ShakeListener;


/**
 * Created by user on 29-10-2017.
 */
public class AppService extends Service implements ShakeListener.OnShakeListener {
    private ShakeListener mShaker;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    public int check;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "emergency";
    private static final String NAME = "name";
    private static final String EMERGENCY1 = "emergency1";
    private static final String EMERGENCY2 = "emergency2";
    double latitude = 28.621616;
    double longitude = 77.356224;
    private FireAlarm fireAlarm;


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
        fireAlarm = new FireAlarm(this);
        Toast.makeText(AppService.this, "Service is created!", Toast.LENGTH_LONG).show();
        Log.d(getPackageName(), "Created the Service!");
        check = 1;
        sharedPreferences = this
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public void onShake() {
        if (check == 1) {
            Toast.makeText(AppService.this, "SHAKEN!", Toast.LENGTH_LONG).show();
            final Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(500);
            final String emergency1 = sharedPreferences.getString(EMERGENCY1, "");
            final String name = sharedPreferences.getString(NAME, "");
            final String emergency2 = sharedPreferences.getString(EMERGENCY2, "");

            MessageUtil.sendMessage(emergency1, name + " is injured in accident at " + latitude + ", " + longitude);
            MessageUtil.sendMessage(emergency2, name + " is injured in accident at " + latitude + ", " + longitude);


            //sms call here.............
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!HackerCamp.isAppOnForeground(this)) {

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
        Log.d("kunwar", "Here");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("hypno2");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue().equals("Alert Off")) {

                    fireAlarm.stopAlarm();

                } else if (dataSnapshot.getValue().equals("Alert On")) {

                    fireAlarm.startAlarm();
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
