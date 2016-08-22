package fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.myList;
import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/8/18.
 */
public class Fragment2_2 extends android.support.v4.app.Fragment
{
    MainActivity mactivity;

    public static Fragment2_2 fragment2_2this;
    public View view;
    ListView mainlist2;
    SimpleAdapter adapter;
    TextView mainlist_1,mainlist_2,mainlist_3,mainlist_4,mainlist_5,mainlist_6;
    TextView datestart,dateend;
    dateFragment datefragment;
    Button sousuo;

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
                    List list = (List) msg.obj;
                    adapter = new SimpleAdapter(getContext(), list, R.layout.mainlist,
                                                new String[]{"time","io","detail","oldbalance"},
                                                new int[]{R.id.mainlist_2,R.id.mainlist_3,R.id.mainlist_4,R.id.mainlist_6});
                    mainlist2.setAdapter(adapter);

                    msg = Message.obtain();
                    msg.what = 3;
                    msg.obj = list;
                    handler.sendMessage(msg);

                    break;
                case 3:
                    for(int i = 0;i<mainlist2.getCount();i++)
                    {
                        View viewlist = mainlist2.getChildAt(i);
                        mainlist_4 = (TextView) viewlist.findViewById(R.id.mainlist_4);
                        mainlist_3 = (TextView) viewlist.findViewById(R.id.mainlist_3);
                        if (mainlist_3.getText().equals("收入:"))
                        {
                            mainlist_4.setTextColor(Color.GREEN);
                            mainlist_4.setText("+ " + mainlist_4.getText().toString());
                        } else
                        {
                            mainlist_4.setText("- " + mainlist_4.getText().toString());
                        }
                    }
                    break;
                case 4:
                    datestart.setText("456");
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fragment2_2this = this;
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

//                        String sql="select * from jiaoyijilu where io = 1 order by id desc";
                        String sql="select * from jiaoyijilu where io = 1 and time >= '"+datestart.getText().toString()+"' and time <= '"+dateend.getText().toString()+"' order by id desc";
                        List list = mactivity.jtds.getdata(sql);

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
                String sql="select * from jiaoyijilu where io = 1 order by id desc";
                List list = mactivity.jtds.getdata(sql);

                msg = Message.obtain();
                msg.what = 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
