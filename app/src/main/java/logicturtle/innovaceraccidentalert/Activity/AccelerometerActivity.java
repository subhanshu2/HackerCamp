package logicturtle.innovaceraccidentalert.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import logicturtle.innovaceraccidentalert.FireAlarm;
import logicturtle.innovaceraccidentalert.MessageUtil;
import logicturtle.innovaceraccidentalert.R;
import logicturtle.innovaceraccidentalert.AppService;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private long tStart;
    private int flag = 0;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "emergency";
    private static final String NAME = "name";
    private static final String EMERGENCY1 = "emergency1";
    private static final String EMERGENCY2 = "emergency2";

    @BindView(R.id.circle_progress_bar)
    ProgressBar progressBar;
    private FireAlarm fireAlarm;

    @BindView(R.id.speed)
    TextView speedTV;

    private static final int MAX_SPEED = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, AppService.class);
        startService(intent);
        fireAlarm = new FireAlarm(this);
        tStart = System.currentTimeMillis();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sharedPreferences = this
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        new Thread() {
            public void run() {

                detect(sensorEvent);
            }
        }
                .start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void detect(SensorEvent sensorEvent) {
        if (flag == 0)
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                double acceleration = Math.sqrt((x * x + y * y));
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                double elapsedSeconds = tDelta / 1000.0;
                int speed = (int) (acceleration * 3);
                speed = speed * 5 / 18;
                Log.d("ayush", "speed" + speed);
                final int finalSpeed = speed;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speedTV.setText(finalSpeed + " " + "km/hr");
                    }
                });
                progressBar.setProgress((MAX_SPEED / 100));
                if (speed > MAX_SPEED) {
                    fireAlarm();
                    progressBar.setProgress(100);
                }
            }
    }

    private void fireAlarm() {
        flag = 1;
        fireAlarm.startAlarm();
        final String emergency1 = sharedPreferences.getString(EMERGENCY1, "");
        final String name = sharedPreferences.getString(NAME, "");
        final String emergency2 = sharedPreferences.getString(EMERGENCY2, "");
        MessageUtil.sendMessage(emergency1, name+" is injured in accident");
        MessageUtil.sendMessage(emergency2, name+" is injured in accident");

    }

    @OnClick(R.id.stop_alarm)
    public void stopAlarm() {
        flag = 0;
        fireAlarm.stopAlarm();
    }
}
