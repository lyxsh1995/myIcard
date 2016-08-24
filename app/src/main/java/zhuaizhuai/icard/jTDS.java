package zhuaizhuai.icard;

import android.util.Log;
import android.widget.Toast;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lyxsh on 2016/7/20.
 */
public class jTDS
{
    Map<String,Integer> index = new HashMap<String, Integer>();

    public String Ipaddress = "zhuaizhuai.pub";
    public Connection con = null;
    public float oldbalance = 0;

    public float getOldbalance()
    {
        return oldbalance;
    }

    public jTDS()
    {
        /**
         *以下是数据库表索引列
         */
        index.put("J_time",2);
        index.put("J_io",3);
        index.put("J_detail",4);
        index.put("J_oldbalance",5);
    }

    SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void lianjie()
    {
        String UserName = "Icard";//用户名
        String Password = "123";//密码
        try
        { // 加载驱动程序
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            if (con != null)
            {
                if (con.isClosed() == false)
                {
                    closecon();
                }
            }
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://"+Ipaddress+":1433/Icard", UserName,Password);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动程序出错");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e)
        {
        }
    }

    //获取交易记录
    public List getdata(String sql)
    {
        if (con != null)
        {
            try
            {
                if (con.isClosed() == true)
                {
                    lianjie();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        ArrayList<Map>  list = new ArrayList<Map>();
        Map<Object,Object> map;
        try
        {
//            String sql="SELECT * FROM jiaoyijilu where userID="+userID;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                map = new HashMap<>();
                map.put("time",formattime.format(rs.getTimestamp(index.get("J_time"))));
                if (rs.getBoolean(index.get("J_io")))
                {
                    map.put("io","收入:");
                }
                else
                {
                    map.put("io","支出:");
                }
                map.put("detail",rs.getFloat(index.get("J_detail")));
                map.put("oldbalance",rs.getFloat(index.get("J_oldbalance")));
                list.add(map);
            }
            rs.close();
            stmt.close();
            closecon();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    private boolean closecon()
    {
        if (con != null)
        {
            try
            {
                con.close();
                return true;
            }
            catch (SQLException e)
            {
                Log.w("SQL","数据库连接未正常关闭");
                return false;
            }
        }
        return false;
    }

    public boolean chongfu(String yonghuming)
    {
        if (con != null)
        {
            try
            {
                if (con.isClosed() == true)
                {
                    lianjie();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        String sql="SELECT * FROM yonghuxinxi where yonghuming = '"+yonghuming+"'";
        Statement stmt = null;
        try
        {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                rs.close();
                stmt.close();
                closecon();
                return true;
            }
            rs.close();
            stmt.close();
            closecon();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean executesql(String sql)
    {
        if (con != null)
        {
            try
            {
                if (con.isClosed() == true)
                {
                    lianjie();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        Statement stmt = null;
        try
        {
            stmt = con.createStatement();
            int rs = stmt.executeUpdate(sql);
            while (rs > 0)
            {
                stmt.close();
                closecon();
                return true;
            }
            stmt.close();
            closecon();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean yijingzhuce(String openid)
    {
        if (con != null)
    {
        try
        {
            if (con.isClosed() == true)
            {
                lianjie();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
        String sql="SELECT * FROM yonghuxinxi where qqid = '"+openid+"'";
        Statement stmt = null;
        try
        {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                rs.close();
                stmt.close();
                closecon();
                return true;
            }
            rs.close();
            stmt.close();
            closecon();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean denglu(String yonghuming,String mima)
    {
        if (con != null)
        {
            try
            {
                if (con.isClosed() == true)
                {
                    lianjie();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        String sql="SELECT mima FROM yonghuxinxi where yonghuming = '"+yonghuming+"'";
        Statement stmt = null;
        try
        {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                if (rs.getString("mima").equals(mima))
                {
                    rs.close();
                    stmt.close();
                    closecon();
                    return true;
                }
            }
            rs.close();
            stmt.close();
            closecon();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
//    public void testConnection(Connection con) throws java.sql.SQLException {
//
//        try {
//
//            String sql = "SELECT * FROM table_test";//查询表名为“table_test”的所有内容
//            Statement stmt = con.createStatement();//创建Statement
//            ResultSet rs = stmt.executeQuery(sql);//ResultSet类似Cursor
//
//            while (rs.next()) {//<code>ResultSet</code>最初指向第一行
//                System.out.println(rs.getString("test_id"));//输出第n行，列名为“test_id”的值
//                System.out.println(rs.getString("test_name"));
//            }
//
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage().toString());
//        } finally {
//            if (con != null)
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                }
//        }
//    }
}
