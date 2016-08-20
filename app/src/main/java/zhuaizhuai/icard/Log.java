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

import Tencent.BaseUiListener;

/**
 * Created by lyxsh on 2016/8/11.
 */
public class Log extends Activity
{
    public static Log logthis;
    public Sqlite sqlite;
    public SQLiteDatabase db;

    public BaseUiListener listener = new BaseUiListener();
    private Cursor cursor;
    public Tencent mytencent;
    public static String AppID = "1105607308";
    private String openid,access_token,expires_in;

    private Button button_log;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        logthis = this;
        mytencent = Tencent.createInstance(AppID,this);

        //创建数据库
        sqlite = new Sqlite(getApplication(),"Icardsqlite",null,1);
        db = sqlite.getWritableDatabase();




        button_log = (Button) findViewById(R.id.log_log);
        logo = (ImageView) findViewById(R.id.log_logo);

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!mytencent.isSessionValid())
                {
                    String sqlstr = "select * from qq where _id=1";
                    cursor = db.rawQuery(sqlstr,null);
                    if(cursor.getCount() == 1)
                    {
                        cursor.move(1);
                        openid = cursor.getString(1);
                        if ((openid = cursor.getString(1)) != null)
                        {
                            access_token = cursor.getString(2);
                            expires_in = cursor.getString(3);
                            mytencent.setOpenId(openid);
                            mytencent.setAccessToken(access_token,expires_in);

                            Intent intent = new Intent(Log.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            mytencent.login(Log.this, "get_user_info,add_topic", listener);
                        }
                    }
                }
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Log.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mytencent.onActivityResultData(requestCode, resultCode, data,listener);
        db.execSQL(listener.getopenid());
        Intent intent = new Intent(Log.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
