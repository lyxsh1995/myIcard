package Tencent;

import android.os.Bundle;
import android.util.Log;

import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by lyxsh on 2016/8/11.
 */
public class BaseUiListener implements IUiListener
{
    public boolean done = false;
    private String openid,access_token,expires_in;
    public JSONObject res;
    Bundle bundle;

    public Bundle getBundle()
    {
        return bundle;
    }
    public JSONObject getRes()
    {
        return res;
    }

    @Override
    public void onComplete(Object response)
    {
        done = false;
        res = (JSONObject) response;
        try
        {
            Iterator iterator = res.keys();
            String key, value;
            bundle = new Bundle();
            while (iterator.hasNext())
            {
                key = (String) iterator.next();
                value = res.getString(key);
                bundle.putString(key,value);
                Log.w(key,value);
            }
        }
        catch (JSONException e)
        {
        }
        done = true;
    }

    @Override
    public void onError(UiError e)
    {
        Log.w("onError:", "code:" + e.errorCode + ", msg:"
                + e.errorMessage + ", detail:" + e.errorDetail);
    }


    @Override
    public void onCancel()
    {
        Log.w("onCancel", "");
    }

    public String getopenid()
    {
        try
        {
            openid = res.getString("openid");
            access_token = res.getString("access_token");
            expires_in = res.getString("expires_in");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return "update qq set openid = '"+openid+"' , access_token = '"+access_token+"' , expires_in = '"+expires_in+"' where _id = 1;";
    }
}