package fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

import zhuaizhuai.icard.Bbs;
import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;
import zhuaizhuai.icard.Splash;

/**
 * Created by lyxsh on 2016/9/6.
 */

public class Fragment3_1 extends Fragment
{
    MainActivity mactivity;
    private View view;

    ListView mainlist3;
    SimpleAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mactivity = (MainActivity) getActivity();

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
        new AsyncTask<String, Void, List>()
        {

            @Override
            protected List doInBackground(String... params)
            {
                String sql = params[0];
                List list = Splash.splashthis.jtds.getdata2(sql);
                return list;
            }

            @Override
            protected void onPostExecute(List list)
            {
                adapter = new SimpleAdapter(getContext(), list, R.layout.xinxilist,
                                            new String[]{"yonghuming","shijian","biaoti","neirong","id"},
                                            new int[]{R.id.xinxilist_yonghuming,R.id.xinxilist_shijian,R.id.xinxilist_biaoti,R.id.xinxilist_neirong,R.id.xinxilist_zhutiid});
                mainlist3.setAdapter(adapter);
            }
        }.execute("select * from shiqudiushi where neixing = 1 order by shijian desc");

        mainlist3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getContext(),Bbs.class);

                TextView yonghuming = (TextView) view.findViewById(R.id.xinxilist_yonghuming);
                TextView shijian = (TextView) view.findViewById(R.id.xinxilist_shijian);
                TextView biaoti = (TextView) view.findViewById(R.id.xinxilist_biaoti);
                TextView neirong = (TextView) view.findViewById(R.id.xinxilist_neirong);
                TextView zhutiid = (TextView) view.findViewById(R.id.xinxilist_zhutiid);

                intent.putExtra("yonghuming",yonghuming.getText().toString());
                intent.putExtra("shijian",shijian.getText().toString());
                intent.putExtra("biaoti",biaoti.getText().toString());
                intent.putExtra("neirong",neirong.getText().toString());
                intent.putExtra("zhutiid",zhutiid.getText().toString());
                intent.putExtra("myyonghuming",mactivity.yonghuming);

                startActivity(intent);
            }
        });
        return view;
    }
}
