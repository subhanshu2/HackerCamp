package logicturtle.innovaceraccidentalert.Utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

import logicturtle.innovaceraccidentalert.R;

/**
 * Created by user on 09-Apr-17.
 */

public class ProgressLoader extends KProgressHUD {


    public ProgressLoader(Context context) {
        super(context);
        this.setStyle(Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setLabel("Loading")
                .setWindowColor(R.color.colorAccent)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public KProgressHUD show() {
        return super.show();
    }


}
