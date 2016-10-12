package fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.connect.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import bean.myList;
import zhuaizhuai.icard.Log;
import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;
import zhuaizhuai.icard.Splash;


/**
 * Created by lyxsh on 2016/5/7.
 */
public class Fragment1 extends Fragment
{
    MainActivity mactivity;
    public Bitmap bitmap;
    private View view;

    OkHttpClient okhttp = new OkHttpClient();

    TextView textview1;
    ImageView imageview1;
    ListView mainlist;
    SimpleAdapter adapter;

    TextView mainlist_1,mainlist_2,mainlist_3,mainlist_4,mainlist_5,mainlist_6;

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
                    mainlist.setAdapter(adapter);

                    break;
                case 1:
                    imageview1.setImageBitmap((Bitmap) msg.obj);
                    mactivity.leftImageview.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 2:
                    textview1.setText(msg.getData().getString("nickname"));
                    break;
                case 3:
                    for(int i = 0;i<mainlist.getCount();i++)
                    {
                        View viewlist = mainlist.getChildAt(i);
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
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mactivity = (MainActivity) getActivity();

        if(view == null){
            view=inflater.inflate(R.layout.fragment_1, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        textview1 = (TextView) view.findViewById(R.id.textView1);
        String yonghuming = mactivity.yonghuming;
        if (yonghuming != null)
        {
            textview1.setText(mactivity.getIntent().getStringExtra("yonghuming"));
        }
        imageview1 = (ImageView) view.findViewById(R.id.imageView1);

        mainlist = (ListView) view.findViewById(R.id.mainlist);
        setview();

        imageview1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            }
        });

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    private void setIamgeview (String url)
    {
        Request request = new Request.Builder().url(url).build();
        try
        {
            /**
             *缩进的代码为
             * httpconection实现的方法
             */
            //HttpURLConnection connection = null;
//
//                try
//                {
//                    URL url = new URL("http://192.168.0.106:80/touxiang/touxiang1.png");
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
////                            connection.setConnectTimeout(8000);
////                            connection.setReadTimeout(8000);
//                    InputStream in = connection.getInputStream();
//                    //下面对获取到的输入流进行读取
//                    Bitmap bitmap = BitmapFactory.decodeStream(in);
//                    msg = Message.obtain();
//                    msg.what = 1;
//                    //将服务器返回的结果存放到Message中
//                    msg.obj = bitmap;
//                    handler.sendMessage(msg);
//                }
//                catch (MalformedURLException e)
//                {
//                    e.printStackTrace();
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//                finally
//                {
//                    if (connection != null)
//                    {
//                        connection.disconnect();
//                    }
//                }
            Response response = okhttp.newCall(request).execute();
            if (response.isSuccessful())
            {
                InputStream is = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(is);
                msg = Message.obtain();
                msg.what = 1;
                //将服务器返回的结果存放到Message中
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //初始化操作,统一在onCreatView最后操作
    boolean listboolean = true;
    private void setview()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                UserInfo userInfo = new UserInfo(getContext(), Splash.splashthis.mytencent.getQQToken());
                userInfo.getUserInfo(Splash.splashthis.listener);
                while (!(Splash.splashthis.listener.done))
                {
                }
                Splash.splashthis.listener.done = false;
                Bundle bundle = Splash.splashthis.listener.getBundle();
                if (!(bundle.getString("ret").equals("-1")))
                {
                    msg = Message.obtain();
                    msg.what = 2;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    while ((bundle.getString("figureurl_qq_2"))==null){}
                    setIamgeview(bundle.getString("figureurl_qq_2"));
                }
            }
        }.start();

        new Thread()
        {
            public void run()
            {

                //查询最后一条信息
                String sql="select top 1 * from jiaoyijilu where io = 0 order by id desc";
                List list = Splash.splashthis.jtds.getdata(sql);
                sql="select top 1 * from jiaoyijilu where io = 1 order by id desc";
                List list2 = Splash.splashthis.jtds.getdata(sql);
                list.addAll(list2);

                msg = Message.obtain();
                msg.what = 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }.start();

        listboolean = true;
        new Thread()
        {
            public void run()
            {
                while (listboolean)
                {
                    if ((mainlist.getChildAt(0)) != null)
                    {
                        msg = Message.obtain();
                        msg.what = 3;
                        handler.sendMessage(msg);

                        listboolean = false;
                    }
                }
            }
        }.start();
    }
}
