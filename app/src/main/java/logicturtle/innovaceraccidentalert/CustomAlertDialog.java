package logicturtle.innovaceraccidentalert;

import android.content.Context;
import android.support.v4.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by kunwar on 3/8/17.
 */

public class CustomAlertDialog {


    public static WarningListener warningListener;


    public static void showErrorDialog(Context context, String message) {

        //Context context = DigitalSecurityApp.getAppContext();
        if (context == null)
            return;
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(message)
                .show();


    }

    public static void showSuccessDialog(Context context, String message) {

        if (context == null)
            return;

        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .show();


    }


    public static void showWarningDialog(String message, Fragment fragment) {

        warningListener = (WarningListener) fragment;

        Context context = HackerCamp.getAppContext();
        if (context == null)
            return;
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(message)
                .setCancelText("Yes do it")
                .setConfirmText("No")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.hide();
                    }
                }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.hide();
                warningListener.onPositiveClick();

            }
        });


    }


    public static interface WarningListener {

        public void onPositiveClick();


    }


}


