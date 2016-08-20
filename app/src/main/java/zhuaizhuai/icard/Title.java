package zhuaizhuai.icard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/7/19.
 */
public class Title extends Toolbar
{
    private Context mContext;

    public ImageButton right1;
    public TextView title1;

    //更改导航栏信息
    public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    title1.setText("ok");
                    break;
            }
        }
    };

    public Title(Context context,AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        right1 = (ImageButton)findViewById(R.id.iv_nav_right);
        title1 = (TextView)findViewById(R.id.tv_nav_title);


        title1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                title1.setText("标题");
            }
        });
    }
}
