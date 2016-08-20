package bean;

import java.util.HashMap;

import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/8/17.
 */
public class Leftlist extends myList
{
    public Leftlist()
    {
        super(5);
    }

    @Override
    protected void putdata(HashMap hashmap, int flag)
    {
        switch (flag)
        {
            case 0:
                hashmap.put("shezhiming", R.mipmap.icon_home_nor);
                hashmap.put("shezhiming2","!shezhineirong2");
                break;
            case 1:
                hashmap.put("shezhiming",R.mipmap.icon_yipai_nor);
                hashmap.put("shezhiming2","!shezhineirong2-2");
                break;
        }
    }
}
