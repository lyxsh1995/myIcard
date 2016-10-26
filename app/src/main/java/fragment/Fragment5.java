package fragment;

/**
 * Created by lyxsh on 2016/10/23.
 */

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import zhuaizhuai.icard.MainActivity;
import zhuaizhuai.icard.R;
import zhuaizhuai.icard.Splash;

public class Fragment5 extends Fragment
{
    ListView listview;
    MainActivity mactivity;
    float oldbalance = 0;
    SimpleDateFormat ft;
    Date date;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText beizhu;
    String[] mItems;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_5, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        mactivity = (MainActivity) getActivity();

        datePicker = (DatePicker) view.findViewById(R.id.datePicker2);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        String sql="select top 1 oldbalance from jiaoyijilu where yonghuming = '"+mactivity.yonghuming+"' order by id desc";
        new AsyncTask<String, Void, Float>()
        {
            @Override
            protected Float doInBackground(String... params)
            {
                return Splash.splashthis.jtds.getoldbalance(params[0]);
            }

            @Override
            protected void onPostExecute(Float aFloat)
            {
                oldbalance = aFloat;
            }
        }.execute(sql);


        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) view.findViewById(R.id.spinner3);
        final EditText jinge = (EditText) view.findViewById(R.id.jinge);
        final EditText beizhu = (EditText) view.findViewById(R.id.beizhu);
        final EditText xijie = (EditText) view.findViewById(R.id.xijie);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    mItems = getResources().getStringArray(R.array.zhichuleixing);
                    // 建立Adapter并且绑定数据源
                    ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mItems);
                    //绑定 Adapter到控件
                    spinner3.setAdapter(_Adapter);
                }
                else
                {
                    mItems = getResources().getStringArray(R.array.shouruleixing);
                    // 建立Adapter并且绑定数据源
                    ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mItems);
                    //绑定 Adapter到控件
                    spinner3.setAdapter(_Adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                mItems = getResources().getStringArray(R.array.zhichuleixing);
                // 建立Adapter并且绑定数据源
                ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mItems);
                //绑定 Adapter到控件
                spinner3.setAdapter(_Adapter);
            }
        });//绑定sprinner3数据

        xijie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(),"xijie",Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(getContext()).setTitle("单选框").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        mItems, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                xijie.setText(mItems[which]);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        Button quxiao = (Button) view.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                jinge.setText("");
                beizhu.setText("");
            }
        });

        Button tianjia = (Button) view.findViewById(R.id.tianjia);
        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (jinge.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(),"请填写金额",Toast.LENGTH_SHORT).show();
                    return;
                }

                date = new Date(datePicker.getYear()-1900,datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if(spinner2.getSelectedItemPosition() == 1)
                {
                    oldbalance += Float.parseFloat(jinge.getText().toString());
                }
                else
                {
                    oldbalance -= Float.parseFloat(jinge.getText().toString());
                }
                String sql = "insert into jiaoyijilu values ('" + ft.format(date) + "','"
                        + spinner2.getSelectedItemPosition() + "','"
                        + jinge.getText() + "','"
                        + oldbalance + "','"
                        + mactivity.yonghuming + "','"
                        + xijie.getText().toString() + "','"
                        + beizhu.getText().toString().trim() + "')";
                new AsyncTask<String, Void, Boolean>()
                {
                    @Override
                    protected Boolean doInBackground(String... params)
                    {
                        return Splash.splashthis.jtds.executesql(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean)
                    {
                        if (aBoolean)
                        {
                            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(sql);
            }
        });
        return view;
    }
}
