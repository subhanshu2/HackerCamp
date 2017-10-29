package logicturtle.innovaceraccidentalert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.emergency1)
    EditText emergency1;

    @BindView(R.id.emergency2)
    EditText emergency2;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "emergency";
    private static final String EMERGENCY1 = "emergency1";
    private static final String EMERGENCY2 = "emergency2";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreferences = this
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }




    @OnClick(R.id.submit)
    public void submit(){

        String Name = getStringFromEditText(name);
        String Emergency1 = getStringFromEditText(emergency1);
        String Emergency2 = getStringFromEditText(emergency2);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference();
        reference.child(Name).setValue(new UserModel(Name,Emergency1,Emergency2));

        sharedPreferences
                .edit()
                .putString(EMERGENCY1, Emergency1)
                .apply();

        sharedPreferences
                .edit()
                .putString(EMERGENCY2, Emergency2)
                .apply();

    }



    public String getStringFromEditText(EditText text){


        return text.getText().toString();

    }
}
