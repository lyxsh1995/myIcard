package zhuaizhuai.icard;


import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.*;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by lyxsh on 2016/11/18.
 */
public class FloatWindowService extends Service
{
    public static FloatWindowService floatWindowServicethis;

    View view;
    public WindowManager windowManager;
    public WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    FloatView floatView;
    @Override
    public void onCreate()
    {
        super.onCreate();
        view = LayoutInflater.from(this).inflate(R.layout.float_window, null);

        floatWindowServicethis = this;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatView = new FloatView(getApplicationContext());
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = 150;
        params.height = 150;
        params.x = 100;
        params.y = 100;
        windowManager.addView(floatView, params);

        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = getPackageManager().getLaunchIntentForPackage("zhuaizhuai.icard");
                startActivity(mainIntent);
            }
        });

        floatView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                android.util.Log.e("service","stop");
                stopSelf();
                return true;
            }
        });
    }

    @Override
    public void onDestroy()
    {
        windowManager.removeView(floatView);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}