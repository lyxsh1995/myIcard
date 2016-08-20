package zhuaizhuai.icard;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.json.JSONException;

import Tencent.BaseUiListener;

/**
 * Created by lyxsh on 2016/8/11.
 */
public class Log extends Activity
{
    public static Log logthis;

    private Button button_log;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        logthis = this;
        button_log = (Button) findViewById(R.id.log_log);

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!Splash.splashthis.mytencent.isSessionValid())
                {
                    Splash.splashthis.mytencent.login(Log.this, "get_user_info,add_topic", Splash.splashthis.listener);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Splash.splashthis.mytencent.onActivityResultData(requestCode, resultCode, data,Splash.splashthis.listener);
        Splash.splashthis.db.execSQL(Splash.splashthis.listener.getopenid());
        try
        {
            Splash.splashthis.mytencent.setOpenId(Splash.splashthis.listener.res.getString("openid"));
            Splash.splashthis.mytencent.setAccessToken(Splash.splashthis.listener.res.getString("access_token"),
                                                  Splash.splashthis.listener.res.getString("expires_in"));
            Splash.splashthis.listener.done = false;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Intent intent = new Intent(Log.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
