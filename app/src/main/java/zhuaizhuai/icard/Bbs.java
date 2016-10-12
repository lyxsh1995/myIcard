package zhuaizhuai.icard;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static zhuaizhuai.icard.R.id.iv_nav_back;
import static zhuaizhuai.icard.R.id.iv_nav_right;

/**
 * Created by lyxsh on 2016/9/6.
 */
public class Bbs extends Activity
{
    TextView bbs_1, bbs_2, bbs_3, bbs_4;
    ListView bbs_listview;
    String zhutiid;
    Button bbs_huifu;
    String myyonghuming;
    EditText bbs_huifuneirong;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbs);

        bbs_1 = (TextView) findViewById(R.id.bbs_1);
        bbs_2 = (TextView) findViewById(R.id.bbs_2);
        bbs_3 = (TextView) findViewById(R.id.bbs_3);
        bbs_4 = (TextView) findViewById(R.id.bbs_4);
        bbs_huifu = (Button) findViewById(R.id.bbs_huifu);
        bbs_huifuneirong = (EditText) findViewById(R.id.bbs_huifuneirong);

        Intent intent = getIntent();

        bbs_1.setText(intent.getStringExtra("yonghuming"));
        bbs_2.setText(intent.getStringExtra("shijian"));
        bbs_3.setText(intent.getStringExtra("biaoti"));
        bbs_4.setText(intent.getStringExtra("neirong"));
        zhutiid = intent.getStringExtra("zhutiid");
        myyonghuming = intent.getStringExtra("myyonghuming");

        Tooltitle tooltitle = (Tooltitle) findViewById(R.id.toolbar);
        ImageButton toolbarback = (ImageButton) findViewById(iv_nav_back);
        toolbarback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        ImageButton toolbarright = (ImageButton) findViewById(iv_nav_right);
        toolbarright.setVisibility(View.INVISIBLE);

        bbs_listview = (ListView) findViewById(R.id.bbs_listview);
        new AsyncTask<String, Void, List>()
        {

            @Override
            protected List doInBackground(String... params)
            {
                String sql = params[0];
                List list = Splash.splashthis.jtds.getdata3(sql);
                return list;
            }

            @Override
            protected void onPostExecute(List list)
            {
                SimpleAdapter adapter = new SimpleAdapter(Bbs.this, list, R.layout.huitie,
                                                          new String[]{"yonghuming", "shijian", "neirong"},
                                                          new int[]{R.id.huifu_1, R.id.huifu_2, R.id.huifu_3});
                bbs_listview.setAdapter(adapter);
            }
        }.execute("select * from huifu where zhutiid = " + zhutiid + " order by shijian asc");

        bbs_huifu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String str = formatter.format(date);

                String sql = "insert into huifu values ('" + zhutiid.trim() + "','"
                        + myyonghuming + "','"
                        + bbs_huifuneirong.getText().toString() + "','"
                        + str.toString() + "')";
                new AsyncTask<String, Void, Boolean>()
                {
                    @Override
                    protected Boolean doInBackground(String... params)
                    {
                        return Splash.splashthis.jtds.executesql(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean)
                    {
                        if (aBoolean)
                        {
                            Toast.makeText(Bbs.this, "回复成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }.execute(sql);
            }
        });
    }
}
