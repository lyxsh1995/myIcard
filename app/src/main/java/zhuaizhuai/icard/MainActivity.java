package zhuaizhuai.icard;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
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
    public DrawerLayout drawerLayout;
    public zhuaizhuai.icard.FragmentTabHost mTabhost;
    public Tooltitle tooltitle;
    public TextView toolbartitle;
    public ImageButton toolbarright;
    public Fragment1 fragment1;
    public ListView listview;
    public ImageView leftImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tooltitle = (Tooltitle) findViewById(R.id.toolbar);
        setSupportActionBar(tooltitle);
        toolbartitle = (TextView) findViewById(R.id.tv_nav_title);
        toolbartitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                toolbartitle.setText("拽拽");
            }
        });
        toolbarright = (ImageButton) findViewById(R.id.iv_nav_right);
        toolbarright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

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
        Tab tab_message = new Tab("记录", R.drawable.tab_message_btn, Fragment2.class);
        Tab tab_yipai = new Tab("消息", R.drawable.tab_yipai_btn, Fragment3.class);
        Tab tab_user = new Tab("设置", R.drawable.tab_user_btn, Fragment4.class);

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
