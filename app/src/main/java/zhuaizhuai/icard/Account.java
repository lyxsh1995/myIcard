package zhuaizhuai.icard;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static zhuaizhuai.icard.R.id.iv_nav_back;
import static zhuaizhuai.icard.R.id.iv_nav_right;

/**
 * Created by lyxsh on 2016/10/24.
 */
public class Account extends Activity
{
    String ID;
    List list;
    HashMap listmap;
    String sql;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        Intent intent = getIntent();
        ID = intent.getStringExtra("id");

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

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final EditText jinge = (EditText) findViewById(R.id.jinge);
        final EditText beizhu = (EditText) findViewById(R.id.beizhu);
        final EditText xijie = (EditText) findViewById(R.id.xijie);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker2);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        sql = "select * from jiaoyijilu where id = '"+ ID +"'";
        new AsyncTask<String,Void,List>()
        {
            @Override
            protected List doInBackground(String... params)
            {
                return Splash.splashthis.jtds.getdata(params[0]);
            }

            @Override
            protected void onPostExecute(List list)
            {
                listmap = (HashMap) list.get(0);
                if (listmap.get("io").toString().equals("收入:"))
                {
                    spinner2.setSelection(1);
                }
                else
                {
                    spinner2.setSelection(0);
                }
                if (listmap.get("leixing") != null)
                {
                    xijie.setText(listmap.get("leixing").toString());
                }
                jinge.setText(listmap.get("detail").toString().substring(2));
                if (listmap.get("beizhu") != null)
                {
                    beizhu.setText(listmap.get("beizhu").toString());
                }
                String formattime = listmap.get("time").toString();
                datePicker.updateDate(Integer.parseInt(formattime.substring(0,4)),Integer.parseInt(formattime.substring(5,7))-1,Integer.parseInt(formattime.substring(8,10)));
                timePicker.setCurrentHour(Integer.parseInt(formattime.substring(11,13)));
                timePicker.setCurrentMinute(Integer.parseInt(formattime.substring(14,16)));
            }
        }.execute(sql);

        Button quxiao = (Button) findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        Button xiugai = (Button) findViewById(R.id.tianjia);
        xiugai.setText("修改");
        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Date date = new Date(datePicker.getYear()-1900, datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                sql = "update jiaoyijilu set time='"
                        + ft.format(date) +"',io = "
                        + spinner2.getSelectedItemPosition() +",detail = "
                        + jinge.getText().toString()+",leixing ='"
                        + xijie.getText().toString() +"',beizhu='"
                        + beizhu.getText().toString() +"' where id="
                        + ID;
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
                            Toast.makeText(Account.this,"修改成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }.execute(sql);
            }
        });
    }
}
