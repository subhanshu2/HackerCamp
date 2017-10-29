package logicturtle.innovaceraccidentalert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import logicturtle.innovaceraccidentalert.Activity.AccelerometerActivity;
import logicturtle.innovaceraccidentalert.Activity.MainActivity;

/**
 * Created by as on 28-Oct-17.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "emergency";
    private static final String EMERGENCY1 = "emergency1";
    private static final String EMERGENCY2 = "emergency2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        sharedPreferences = this
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        final String emergency1 = sharedPreferences.getString(EMERGENCY1, "");
        final String emergency2 = sharedPreferences.getString(EMERGENCY2, "");


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if (!(emergency1==null&&emergency2==null)) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();
                }   else {

                    Intent i = new Intent(SplashScreenActivity.this, AccelerometerActivity.class);
                    startActivity(i);

                }
            }
        }, 2*1000);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
