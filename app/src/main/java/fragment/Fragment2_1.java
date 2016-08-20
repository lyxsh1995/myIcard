package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import bean.myList;
import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/8/18.
 */
public class Fragment2_1 extends android.support.v4.app.Fragment
{
    public View view;
    ListView mainlist2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(view == null){
            view=inflater.inflate(R.layout.fragment_2_1, container,false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        mainlist2 = (ListView) view.findViewById(R.id.mainlist2);
        myList mylist = new myList(3);
        java.util.List list = mylist.getList();

        SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.mainlist, new String[]{}, new int[]{});
        mainlist2.setAdapter(adapter);

        return view;
    }
}
