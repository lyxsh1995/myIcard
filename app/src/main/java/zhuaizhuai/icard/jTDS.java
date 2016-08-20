package zhuaizhuai.icard;

import android.util.Log;
import android.widget.Toast;

import java.sql.*;
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
    public String Ipaddress = "zhuaizhuai.pub";
    public Connection con = null;
    public float oldbalance = 0;

    public float getOldbalance()
    {
        return oldbalance;
    }

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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //获取交易记录
    public java.util.List getdata()
    {
        lianjie();
        ArrayList<Map>  list = new ArrayList<Map>();
        Map<Object,Object> map = new HashMap<>();
        try
        {
            //查询最后一条信息
            String sql="select top 1 * from jiaoyijilu order by id desc";
//            String sql="SELECT * FROM jiaoyijilu where userID="+userID;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                map.put("time",rs.getTimestamp(2));
                if (rs.getBoolean(3))
                {
                    map.put("io","收入:");
                }
                else
                {
                    map.put("io","支出:");
                }
                map.put("detail",rs.getFloat(4));
                map.put("oldbalance",rs.getFloat(5));
            }
            list.add(map);
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
