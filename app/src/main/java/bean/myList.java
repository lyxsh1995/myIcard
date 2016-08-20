package bean;

import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/8/11.
 */
public class myList
{
    protected Map<Object, Object> map;
    private ArrayList<Map> list;

    public myList(int itemnuber)
    {
        ArrayList<Map<Object, Object>> maplist =new ArrayList<Map<Object, Object>>();
        setmap(maplist,itemnuber);

        list = new ArrayList<Map>();
        int flag = 0;
        for (Map mapArray : maplist)
        {
            putdata((HashMap) mapArray,flag);
            list.add(mapArray);
            flag++;
        }
    }

    public java.util.List getList()
    {
        return list;
    }

    //添加每一行的数据
    protected void putdata(HashMap hashmap,int flag)
    {
        switch (flag)
        {
            case 0:
                hashmap.put("shezhiming","shezhineirong");
                hashmap.put("shezhiming2","shezhineirong2");
                break;
            case 1:
                hashmap.put("shezhiming","shezhineirong2-1");
                hashmap.put("shezhiming2","shezhineirong2-2");
                break;
        }
    }

    private void setmap(ArrayList<Map<Object, Object>> maplist,int count)
    {
        for (int i=0;i<count;i++)
        {
            map = new HashMap<>();
            maplist.add(map);
        }
    }
}
