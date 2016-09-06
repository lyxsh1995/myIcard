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
public class Fragment3 extends Fragment
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
        return inflater.inflate(R.layout.fragment_3,container,false);
    }
}
