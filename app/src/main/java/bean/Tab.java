package bean;

/**
 * Created by lyxsh on 2016/5/8.
 */
public class Tab
{
    private String title;
    private int icom;
    private Class fragment;

    public Tab(String title, int icom, Class fragment)
    {
        this.title = title;
        this.icom = icom;
        this.fragment = fragment;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getIcom()
    {
        return icom;
    }

    public void setIcom(int icom)
    {
        this.icom = icom;
    }

    public Class getFragment()
    {
        return fragment;
    }

    public void setFragment(Class fragment)
    {
        this.fragment = fragment;
    }
}