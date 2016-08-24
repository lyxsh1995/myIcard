package zhuaizhuai.icard;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tauth.Tencent;
import Tencent.BaseUiListener;
import static com.tencent.tauth.Tencent.*;

public class Splash extends Activity
{
    public static Splash splashthis;
    public BaseUiListener listener = new BaseUiListener();
    public Sqlite sqlite;
    public SQLiteDatabase db;
    public jTDS jtds = new jTDS();
    public Tencent mytencent;
    public static String AppID = "1105607308";
    public String openid,access_token,expires_in;
    public String yonghuming,mima;

    private TextView daojishi_text;
    private int daojishishu;
    private Message msg;
    private Cursor cursor;
    private boolean tiaozhuan = false;


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

        splashthis = this;
        //jTDS连接
        new Thread()
        {
            public void run()
            {
                try
                {
                    jtds.lianjie();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();

        mytencent = createInstance(AppID,this);
        //创建数据库
        sqlite = new Sqlite(getApplication(),"Icardsqlite",null,1);
        db = sqlite.getWritableDatabase();

        daojishi_text = (TextView) findViewById(R.id.daojishi_Text);

        if (!mytencent.isSessionValid())
        {
            String sqlstr = "select * from qq where _id=1";
            cursor = db.rawQuery(sqlstr,null);
            if(cursor.getCount() == 1)
            {
                cursor.move(1);
                yonghuming = cursor.getString(4);
                mima = cursor.getString(5);
                if (yonghuming != null && mima != null)
                {
                    if (!yonghuming.equals("null") && !mima.equals("null"))
                    {
                        new AsyncTask<String, Void, Boolean>()
                        {
                            @Override
                            protected Boolean doInBackground(String... params)
                            {
                                if(Splash.splashthis.jtds.denglu(params[0],params[1]))
                                {
                                    Splash.splashthis.db.execSQL(Splash.splashthis.jtds.getxinxi(params[0]));
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            protected void onPostExecute(Boolean aBoolean)
                            {
                                if (aBoolean)
                                {
                                    tiaozhuan = true;
                                }
                            }
                        }.execute(yonghuming, mima);
                    }
                }
            }
        }


        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                Intent intent;
                if (tiaozhuan == true)
                {
                    intent = new Intent(Splash.this, MainActivity.class);
                    intent.putExtra("yonghuming",yonghuming);
                }else
                {
                    intent = new Intent(Splash.this, Log.class);
                }
                startActivity(intent);
                Splash.this.finish();
            }
        }, 3000);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mytencent.onActivityResultData(requestCode, resultCode, data,listener);
        listener.done = false;
        super.onActivityResult(requestCode, resultCode, data);
    }
}
