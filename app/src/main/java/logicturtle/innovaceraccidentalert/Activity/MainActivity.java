package logicturtle.innovaceraccidentalert.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import logicturtle.innovaceraccidentalert.ProgressLoader;
import logicturtle.innovaceraccidentalert.PushNotification;
import logicturtle.innovaceraccidentalert.R;
import logicturtle.innovaceraccidentalert.UserModel;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.emergency1)
    EditText emergency1;

    @BindView(R.id.emergency2)
    EditText emergency2;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "emergency";
    private static final String NAME = "name";
    private static final String EMERGENCY1 = "emergency1";
    private static final String EMERGENCY2 = "emergency2";
    ProgressLoader progressLoader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PushNotification.sendPushNotification(this,"vjfnb","vcmuvbinj",R.drawable.checked);
        sharedPreferences = this
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        progressLoader = new ProgressLoader(this);

    }




    @OnClick(R.id.submit)
    public void submit(){
        progressLoader.show();
        final String Name = getStringFromEditText(name);
        final String Emergency1 = getStringFromEditText(emergency1);
        final String Emergency2 = getStringFromEditText(emergency2);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference();
        Task<Void> task = reference.child(Name).setValue(new UserModel(Name,Emergency1,Emergency2));
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                    progressLoader.dismiss();

                sharedPreferences
                        .edit()
                        .putString(NAME, Name)
                        .apply();

                sharedPreferences
                        .edit()
                        .putString(EMERGENCY1, Emergency1)
                        .apply();

                sharedPreferences
                        .edit()
                        .putString(EMERGENCY2, Emergency2)
                        .apply();

                Intent intent = new Intent(MainActivity.this, AccelerometerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressLoader.dismiss();
                Toast.makeText(MainActivity.this,"Some Error Occurred",Toast.LENGTH_SHORT);
            }
        });



    }



    public String getStringFromEditText(EditText text){


        return text.getText().toString();

    }
}
