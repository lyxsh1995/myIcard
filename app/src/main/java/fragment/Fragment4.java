package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import bean.myList;
import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/5/7.
 */
public class Fragment4 extends Fragment
{
    ListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_4,container,false);
        listview = (ListView) view.findViewById(R.id.listView);
        ImageView imageView = (ImageView) view.findViewById(R.id.shezhi_imageView);
        final MainActivity myactivity = (MainActivity) getActivity();
//        imageView.setImageDrawable(myactivity.fragment1.imageview1.getDrawable());

        myList mylist = new myList(3);
        List list = mylist.getList();

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.listview_shezhi,
                                                  new String[]{"shezhiming",           "shezhiming2"},
                                                  new int[]{R.id.listview_textview,R.id.listview_textview2});
        listview.setAdapter(adapter);

        return view;
    }
}
