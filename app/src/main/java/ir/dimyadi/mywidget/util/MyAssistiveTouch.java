package ir.dimyadi.mywidget.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ir.dimyadi.mywidget.DialogActivity;
import ir.dimyadi.mywidget.R;
import ir.dimyadi.mywidget.SettingsActivity;
import ir.dimyadi.mywidget.views.CustomView;

/**
 * @author MEHDIMYADI
 **/

public class MyAssistiveTouch {

    private View popview;
    private ImageView overlayedButton;
    private RelativeLayout relativeLayout;
    private Context context;
    private boolean enable = false;
    private Initializer wmParams;
    private WindowManager windowManager;
    private CustomView customView;

    public MyAssistiveTouch(View popview, RelativeLayout relativeLayout, ImageView overlayedButton, Initializer wmParams, WindowManager windowManager, Context context) {
        this.popview = popview;
        this.relativeLayout = relativeLayout;
        this.overlayedButton = overlayedButton;
        this.wmParams = wmParams;
        this.windowManager = windowManager;
        this.context = context;

    }

    public MyAssistiveTouch(Context context) {
        this.context = context;
    }

    public void settingButton() {
        /*each button with
         * its
         * functionality*/
        ImageView btnClose = popview.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.removeView(popview);
                customView = new CustomView(overlayedButton, windowManager, relativeLayout, wmParams);
                customView.removeCustomView();
                setEnable(true);
            }
        });

        ImageView btnHome = popview.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get home of launcher
                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.addCategory(Intent.CATEGORY_HOME);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);
                //relativeLayout.removeView(popview);
                //relativeLayout.addView(overlayedButton, wmParams.wmInnitializer(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
                //setEnable(true);
                Intent intent = new Intent(context.getApplicationContext(), DialogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                relativeLayout.removeView(popview);
                relativeLayout.addView(overlayedButton);
                setEnable(true);
            }
        });

        //ImageView btnBluetooth = popview.findViewById(R.id.btnBluetooth);
        //btnBluetooth.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        enableBluetooth();
        //        relativeLayout.removeView(popview);
        //        relativeLayout.addView(overlayedButton);
        //        setEnable(true);
        //    }
        //});

        //     ImageView btnWifi = popview.findViewById(R.id.btnWifi);
        //     btnWifi.setOnClickListener(new View.OnClickListener() {
        //         @Override
        //         public void onClick(View view) {
        //             enableWiFi();
        //             relativeLayout.removeView(popview);
        //             relativeLayout.addView(overlayedButton);
        //             setEnable(true);
        //         }
        //     });

        ImageView btnSetting = popview.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                relativeLayout.removeView(popview);
                relativeLayout.addView(overlayedButton);
                setEnable(true);
            }
        });

    }

    // private void enableWiFi() {
    //     WifiManager wifiManager = (WifiManager) context.getApplicationContext()
    //             .getSystemService(Context.WIFI_SERVICE);

    //     assert wifiManager != null;
    //     boolean wifiEnabled = wifiManager.isWifiEnabled();

    //    if (wifiEnabled) {
    //        Toast.makeText(context, "Wi-Fi disabled", Toast.LENGTH_SHORT).show();
    //        wifiManager.setWifiEnabled(false);
    //    } else {
    //        Toast.makeText(context, "Wi-Fi enabled", Toast.LENGTH_SHORT).show();
    //        wifiManager.setWifiEnabled(true);
    //    }
    //}

    // private void enableBluetooth() {
    //     BluetoothAdapter bluetoothAdapter = getDefaultAdapter();
    //     if (bluetoothAdapter == null) {
    //         Toast.makeText(context.getApplicationContext(), "disabled", Toast.LENGTH_SHORT).show();
    //     } else {
    //         if (bluetoothAdapter.isEnabled()) {
    //             Toast.makeText(context, "Bluetooth disable", Toast.LENGTH_SHORT).show();
    //             bluetoothAdapter.disable();
    //         } else {
    //             Toast.makeText(context, "Bluetooth enaled", Toast.LENGTH_SHORT).show();
    //             bluetoothAdapter.enable();
    //         }
    //     }
    // }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
