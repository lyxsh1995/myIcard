package zhuaizhuai.icard;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity
{
    TextView daojishi_text;
    int daojishishu;
    Message msg;

    //更新UI
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    daojishi_text.setText(Integer.toString(daojishishu));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        daojishi_text = (TextView) findViewById(R.id.daojishi_Text);

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                Intent intent = new Intent(SplashActivity.this, Log.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1000);
    }

    Runnable daojishi = new Runnable()
    {
        @Override
        public void run()
        {
            for (int i = 3; i != 0; i--)
            {
                try
                {
                    daojishishu = i;
                    //不放下面一句会出错,同一个MSG不能重复加入消息队列
                    msg = Message.obtain();
                    msg.what = 0;
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onStart()
    {
        super.onStart();

        Thread th2 = new Thread(daojishi);
        th2.start();
    }
}
