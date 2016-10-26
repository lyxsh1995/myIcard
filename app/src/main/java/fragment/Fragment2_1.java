package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bean.MyAdapter;
import bean.myList;
import zhuaizhuai.icard.Log;
import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;
import zhuaizhuai.icard.Splash;

/**
 * Created by lyxsh on 2016/8/18.
 */
public class Fragment2_1 extends android.support.v4.app.Fragment
{
    MainActivity mactivity;

    public static Fragment2_1 fragment2_1this;
    public View view;
    ListView mainlist2;
    View viewlist;
    CheckBox checkbox1;
    SimpleAdapter adapter;
    TextView mainlist_1,mainlist_2,mainlist_3,mainlist_4,mainlist_5,mainlist_6;
    TextView datestart,dateend;
    dateFragment datefragment;
    Button sousuo;
    List list;
    HashMap listmap;
    MyAdapter adapter1;
    MyAdapter.ViewHolder viewholder;

    Message msg = new Message();
    private android.os.Handler handler = new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    list = (List) msg.obj;
                    adapter1 = new MyAdapter( list, getContext());
                    mainlist2.setAdapter(adapter1);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fragment2_1this = this;
        mactivity = (MainActivity) getActivity();

        if(view == null){
            view=inflater.inflate(R.layout.fragment_2_1, container,false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        mainlist2 = (ListView) view.findViewById(R.id.mainlist2);

        datestart = (TextView) view.findViewById(R.id.datestart);
        dateend = (TextView) view.findViewById(R.id.dateend);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        datestart.setText(formatter.format(System.currentTimeMillis()));
        dateend.setText(formatter.format(System.currentTimeMillis()));
        datestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                datefragment = new dateFragment(datestart);
                datefragment.show(getFragmentManager(),"zhichu_datestart");
            }
        });
        dateend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                datefragment = new dateFragment(dateend);
                datefragment.show(getFragmentManager(),"zhichu_datestart");
            }
        });

        sousuo = (Button) view.findViewById(R.id.sousuo);
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new Thread()
                {
                    public void run()
                    {
                        String sql="select * from jiaoyijilu where yonghuming = '"+mactivity.yonghuming+"' and io = 0 and time >= '"+datestart.getText().toString()+"' and time <= '"+dateend.getText().toString()+"' order by id desc";
                        List list = Splash.splashthis.jtds.getdata(sql);

                        msg = Message.obtain();
                        msg.what = 0;
                        msg.obj = list;
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });

        setview();
        return view;
    }

    private void setview()
    {
        new Thread()
        {
            public void run()
            {

                //查询最后一条信息
                String sql="select * from jiaoyijilu where yonghuming = '"+mactivity.yonghuming+"' and io = 0 order by id desc";
                List list = Splash.splashthis.jtds.getdata(sql);

                msg = Message.obtain();
                msg.what = 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
