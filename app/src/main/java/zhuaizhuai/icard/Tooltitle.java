package zhuaizhuai.icard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by lyxsh on 2016/8/15.
 */
public class Tooltitle extends Toolbar
{
    public ImageButton right1;
    public ImageButton back1;
    public TextView title1;
    private LayoutInflater inflater;

    public Tooltitle(Context context)
    {
        this(context,null);
    }

    public Tooltitle(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public Tooltitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        inflater = inflater.from(context);
        initView();
    }

    private void initView()
    {
        View view = inflater.inflate(R.layout.title,null);
        right1 = (ImageButton)findViewById(R.id.iv_nav_right);
        title1 = (TextView)findViewById(R.id.tv_nav_title);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

        addView(view,lp);
    }
}
