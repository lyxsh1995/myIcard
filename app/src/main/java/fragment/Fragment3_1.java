package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/9/6.
 */

public class Fragment3_1 extends Fragment
{
    private View view;

    ListView mainlist3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_3_1, container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
        {
            parent.removeView(view);
        }

        mainlist3 = (ListView) view.findViewById(R.id.mainlist3);

        return view;
    }
}
