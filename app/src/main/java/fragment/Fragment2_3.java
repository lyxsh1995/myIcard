package fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import bean.MyAdapter;
import zhuaizhuai.icard.Account;
import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;
import zhuaizhuai.icard.Splash;

/**
 * Created by lyxsh on 2016/8/18.
 */
public class Fragment2_3 extends android.support.v4.app.Fragment
{
    MainActivity mactivity;

    public static Fragment2_3 fragment2_3this;
    public View view;
    ListView mainlist2;
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        fragment2_3this = this;
        mactivity = (MainActivity) getActivity();

        if(view == null){
            view=inflater.inflate(R.layout.fragment_2_1, container,false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }


        mainlist2 = (ListView) view.findViewById(R.id.mainlist2);
        final LinearLayout bianjianniu = (LinearLayout) view.findViewById(R.id.bianjianniu);

        // 绑定listView的监听器
        mainlist2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if (bianjianniu.getVisibility() == View.GONE)
                {
                    Intent intent = new Intent(getContext(),Account.class);
                    listmap = (HashMap) list.get(arg2);
                    intent.putExtra("id",listmap.get("id").toString());
                    startActivity(intent);
                }
                else
                {
                    // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                    MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) arg1.getTag();
                    // 改变CheckBox的状态
                    if (holder.cb.getVisibility() == View.VISIBLE)
                    {
                        holder.cb.toggle();
                        // 将CheckBox的选中状况记录下来
                        MyAdapter.getIsSelected().put(arg2, holder.cb.isChecked());
                    }
                }
            }
        });

        mainlist2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View view,final int location, long arg3) {
                Toast.makeText(getContext(),"changan",Toast.LENGTH_SHORT).show();
                viewholder = (MyAdapter.ViewHolder) view.getTag();
                if (viewholder.cb.getVisibility() == View.GONE)
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        MyAdapter.getIsSelected().put(i, false);
                        MyAdapter.getIsVisible().put(i, View.VISIBLE);
                    }
                    bianjianniu.setVisibility(View.VISIBLE);
                    adapter1.notifyDataSetChanged();
                } else
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        MyAdapter.getIsSelected().put(i, false);
                        MyAdapter.getIsVisible().put(i, View.GONE);
                    }
                    bianjianniu.setVisibility(View.GONE);
                    adapter1.notifyDataSetChanged();
                }
                return true;
            }
        });

        Button quanxuan = (Button) view.findViewById(R.id.quanxuan);
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                for (int i = 0;i<list.size();i++)
                {
                    MyAdapter.getIsSelected().put(i,true);
                }
                adapter1.notifyDataSetChanged();
            }
        });

        Button quxiao = (Button) view.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),"quanxuan",Toast.LENGTH_SHORT).show();
                for (int i = 0;i<list.size();i++)
                {
                    MyAdapter.getIsSelected().put(i,false);
                }
                adapter1.notifyDataSetChanged();
            }
        });

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

                        //查询最后一条信息
//                        String sql="select * from jiaoyijilu where io = 0 order by id desc";
                        String sql="select * from jiaoyijilu where yonghuming = '"+mactivity.yonghuming+"' and time >= '"+datestart.getText().toString()+"' and time <= '"+dateend.getText().toString()+"' order by id desc";
                        List list = Splash.splashthis.jtds.getdata(sql);

                        msg = Message.obtain();
                        msg.what = 0;
                        msg.obj = list;
                        handler.sendMessage(msg);
                    }
                }.start();
            }
        });

        Button shanchu = (Button) view.findViewById(R.id.shanchu);
        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String sql = null;
                for (int i = 0;i<list.size();i++)
                {
                    listmap = (HashMap) list.get(i);
                    if (MyAdapter.getIsSelected().get(i) == true)
                    {
                        if (sql == null)
                        {
                            sql = "delete from jiaoyijilu where id = " + listmap.get("id");
                        }
                        else
                        {
                            sql += " or id = " + listmap.get("id");
                        }
                    }
                }
                if (sql == null)
                {
                    Toast.makeText(getContext(),"未选择记录",Toast.LENGTH_SHORT).show();
                }
                else
                {
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
                                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute(sql);
                }
            }
        });

        final Button tongji = (Button) view.findViewById(R.id.tongji);
        tongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CategorySeries bingzhuangtu = new CategorySeries("统计");
                float zhichu = 0;
                float shouru = 0;
                for (int i = 0;i<list.size();i++)
                {
                    if (MyAdapter.getIsSelected().get(i) == true)
                    {
                        listmap = (HashMap) list.get(i);
                        float ft = Float.parseFloat(listmap.get("detail").toString().substring(2));
                        if (listmap.get("io").equals("收入:"))
                        {
                            shouru += ft;
                        }
                        else
                        {
                            zhichu += ft;
                        }
                    }
                }
                bingzhuangtu.add("支出",zhichu);
                bingzhuangtu.add("收入",shouru);


                int[] yanse = new int[]{Color.RED,Color.GREEN};
                DefaultRenderer renderer = new DefaultRenderer();     /* 默认的饼图图表渲染器 */
                renderer.setMargins(new int[]{20, 30, 15, 0});     /* 设置边距 */

                DecimalFormat df1 = new DecimalFormat(".0");
                for (int color : yanse)
                {
                    SimpleSeriesRenderer r = new SimpleSeriesRenderer();    /* 饼状图中单个数据的颜色渲染器 */
                    r.setColor(color);
                    r.setChartValuesFormat(df1);    //设置文字格式
                    renderer.addSeriesRenderer(r);                      /* 将单个元素渲染器设置到饼图图表渲染器中 */
                }
                //显示标签
                renderer.setShowLabels(true);
                //不显示底部说明
                renderer.setShowLegend(false);
                //设置标签字体大小
                renderer.setLabelsTextSize(40);
                renderer.setLabelsColor(Color.BLACK);
                renderer.setZoomEnabled(false);
                renderer.setPanEnabled(false);

                renderer.setDisplayValues(true);// 显示数据
                renderer.setFitLegend(true);// 设置是否显示图例
                renderer.setChartTitle("饼状图统计");// 设置饼图标题
                renderer.setChartTitleTextSize(80);// 设置饼图标题大小
                Intent myintent = ChartFactory.getPieChartIntent(getContext(),bingzhuangtu,renderer,"饼状统计图");
                startActivity(myintent);
            }
        });

        Button jiaoyishuaxin = (Button) view.findViewById(R.id.jiaoyishuaxin);
        jiaoyishuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bianjianniu.setVisibility(View.GONE);
                setview();
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
                String sql="select * from jiaoyijilu where yonghuming = '"+mactivity.yonghuming+"' order by id desc";
                List list = Splash.splashthis.jtds.getdata(sql);

                msg = Message.obtain();
                msg.what = 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
