package zhuaizhuai.icard;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by lyxsh on 2016/8/11.
 */
public class Log extends Activity
{
    public static Log logthis;
    public String openid;
    public jTDS jtds = new jTDS();

    private Button log_qq,log_register,log_log;
    private EditText account,password;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        logthis = this;
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

        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);

        log_qq = (Button) findViewById(R.id.log_qq);
        log_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!Splash.splashthis.mytencent.isSessionValid())
                {
                    Splash.splashthis.mytencent.login(Log.this, "get_user_info,add_topic", Splash.splashthis.listener);
                }
            }
        });

        log_register = (Button) findViewById(R.id.log_regist);
        log_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Log.this,Register.class);
                startActivity(intent);
            }
        });

        log_log = (Button) findViewById(R.id.log_log);
        log_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AsyncTask<String, Void, Boolean>()
                {
                    @Override
                    protected Boolean doInBackground(String... params)
                    {
                        return jtds.denglu(params[0],params[1]);
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean)
                    {
                        if (aBoolean)
                        {
                            Intent intent = new Intent(Log.this,MainActivity.class);
//                            intent.putExtra("yonghuming",account.getText().toString().trim());
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Log.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(account.getText().toString().trim(), password.getText().toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try
        {
            Splash.splashthis.mytencent.onActivityResultData(requestCode, resultCode, data,Splash.splashthis.listener);
            Splash.splashthis.db.execSQL(Splash.splashthis.listener.getopenid());
            openid = Splash.splashthis.listener.res.getString("openid");
            Splash.splashthis.mytencent.setOpenId(openid);
            Splash.splashthis.mytencent.setAccessToken(Splash.splashthis.listener.res.getString("access_token"),
                                                  Splash.splashthis.listener.res.getString("expires_in"));
            Splash.splashthis.listener.done = false;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        new AsyncTask<String, Void, Boolean>()
        {
            @Override
            protected Boolean doInBackground(String... params)
            {
                return jtds.yijingzhuce(params[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean)
            {
                if (aBoolean)
                {
                    Intent intent = new Intent(Log.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    //QQ登录未注册过
                    Intent intent = new Intent(Log.this,Register.class);
                    startActivity(intent);
                }
            }
        }.execute(openid);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
