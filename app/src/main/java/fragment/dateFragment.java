package fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.zip.Inflater;

import zhuaizhuai.icard.R;

/**
 * Created by lyxsh on 2016/8/21.
 */
public class dateFragment extends DialogFragment
{
    private View view;
    public DatePicker datepicker;
    TextView textview;

    public dateFragment(TextView textview)
    {
        this.textview = textview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(view == null){
            view=inflater.inflate(R.layout.datefragment, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        datepicker = (DatePicker) view.findViewById(R.id.datePicker);
        int year = Integer.parseInt(textview.getText().subSequence(0,4).toString());
        int month = Integer.parseInt(textview.getText().subSequence((textview.getText().toString().indexOf('-'))+1, (textview.getText().toString().lastIndexOf('-'))).toString());
        int day = Integer.parseInt(textview.getText().subSequence((textview.getText().toString().lastIndexOf('-')+1), textview.getText().toString().length()).toString());
        datepicker.updateDate(year,month - 1,day);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if (datepicker.getMonth()+1<10)
        {
            textview.setText(datepicker.getYear() +"-0"+ (datepicker.getMonth()+1) +"-"+datepicker.getDayOfMonth());

        }
        else
        {
            textview.setText(datepicker.getYear() +"-"+ (datepicker.getMonth()+1) +"-"+datepicker.getDayOfMonth());
        }
        super.onDismiss(dialog);
    }
}
