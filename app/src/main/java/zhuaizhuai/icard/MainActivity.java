package zhuaizhuai.icard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bean.Leftlist;
import bean.Tab;
import fragment.Fragment1;
import fragment.Fragment2;
import fragment.Fragment2_1;
import fragment.Fragment3;
import fragment.Fragment4;

public class MainActivity extends AppCompatActivity
{
    private Message msg = new Message();
    private long exitTime = 0;//双击退出
    private LayoutInflater layoutInflater;
    private List<Tab> mTabs = new ArrayList<Tab>(4);
    private Leftlist leftlist = new Leftlist();

    //项目其他class public实例化
    public jTDS jtds = new jTDS();
    public zhuaizhuai.icard.FragmentTabHost mTabhost;
    public Tooltitle tooltitle;
    public Fragment1 fragment1;
    public ListView listview;
    public ImageView leftImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tooltitle = (Tooltitle) findViewById(R.id.toolbar);
        setSupportActionBar(tooltitle);

        listview = (ListView) findViewById(R.id.left_drawer);
        fragment1 = new Fragment1();

        List list = leftlist.getList();

        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.leftlist,new String[]{"shezhiming","shezhiming2"},new int[]{R.id.leftlist_imageview,R.id.leftlist_textview});
        listview.setAdapter(adapter);

        //Tabhost
        layoutInflater = LayoutInflater.from(this);
        mTabhost = (zhuaizhuai.icard.FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        leftImageview = (ImageView) findViewById(R.id.leftimageView);
        initTab();



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
    }


    //tabhost添加图标
    private void initTab()
    {
        Tab tab_home = new Tab("首页", R.drawable.tab_home_btn, Fragment1.class);
        Tab tab_message = new Tab("消息", R.drawable.tab_message_btn, Fragment2.class);
        Tab tab_yipai = new Tab("一拍", R.drawable.tab_yipai_btn, Fragment3.class);
        Tab tab_user = new Tab("我", R.drawable.tab_user_btn, Fragment4.class);

        mTabs.add(tab_home);
        mTabs.add(tab_message);
        mTabs.add(tab_yipai);
        mTabs.add(tab_user);

        for (Tab tab : mTabs)
        {
            TabHost.TabSpec tabspec = mTabhost.newTabSpec(tab.getTitle());
            View view = layoutInflater.inflate(R.layout.tab_indicator, null);
            ImageView img = (ImageView) view.findViewById(R.id.imageview);
            TextView text = (TextView) view.findViewById(R.id.textview);
            img.setImageDrawable(getResources().getDrawable(tab.getIcom()));

            text.setText(tab.getTitle());
            tabspec.setIndicator(view);
            mTabhost.addTab(tabspec, tab.getFragment(), null);
        }
        //取消分割线
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //默认选择第一个
        mTabhost.setCurrentTab(0);
    }

    //双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else
            {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
