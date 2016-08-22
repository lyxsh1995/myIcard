package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bean.myPagerAdapter;
import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/5/7.
 */
public class Fragment2 extends Fragment
{
    private View view;

    ViewPager pager = null;
    PagerTabStrip tabStrip = null;

    java.util.List<Fragment> mFragments = new ArrayList<Fragment>();
    java.util.List<String> titleContainer = new ArrayList<String>();
    public String TAG = "tag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_2, container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
        {
            parent.removeView(view);
        }

        addviewpager();

        return view;
    }

    //viewpager
    private void addviewpager()
    {
        pager = (ViewPager) view.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip) view.findViewById(R.id.tabstrip);

        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置当前tab页签的下划线颜色
        tabStrip.setTabIndicatorColor(255255255);
        tabStrip.setTextSpacing(200);

        mFragments.add(new Fragment2_1());
        mFragments.add(new Fragment2_2());
        titleContainer.add("支出信息");
        titleContainer.add("收入信息");

        pager.setAdapter(new myPagerAdapter(getActivity().getSupportFragmentManager(), mFragments, titleContainer));
    }
}
