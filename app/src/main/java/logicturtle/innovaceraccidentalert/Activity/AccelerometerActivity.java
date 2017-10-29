package logicturtle.innovaceraccidentalert.Activity;

import android.content.Intent;
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
import logicturtle.innovaceraccidentalert.R;
import logicturtle.innovaceraccidentalert.Services.AppService;
import logicturtle.innovaceraccidentalert.Utils.FireAlarm;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private long tStart;
    private int flag = 0;


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
                int speed = (int) (acceleration * 4);
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
    }

    @OnClick(R.id.stop_alarm)
    public void stopAlarm() {
        flag = 0;
        fireAlarm.stopAlarm();
    }
}
