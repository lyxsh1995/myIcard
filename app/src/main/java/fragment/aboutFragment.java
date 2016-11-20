package fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/10/27.
 */
public class aboutFragment extends DialogFragment
{
    View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = inflater.inflate(R.layout.aboutfragment, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
        {
            parent.removeView(view);
        }

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }
}
