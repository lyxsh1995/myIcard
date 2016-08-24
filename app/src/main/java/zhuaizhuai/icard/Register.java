package zhuaizhuai.icard;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lyxsh on 2016/8/23.
 */
public class Register extends Activity
{
    Button register;
    EditText xuehao,yonghuming,mima;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        xuehao = (EditText) findViewById(R.id.xuehao);
        yonghuming = (EditText) findViewById(R.id.yonghuming);
        mima = (EditText) findViewById(R.id.mima);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (xuehao.getText().length() != 10)
                {
                    Toast.makeText(Register.this,"请输入正确的学号",Toast.LENGTH_SHORT).show();
                }
                else if (yonghuming.getText().length()<4 || yonghuming.getText().length()>16)
                {
                    Toast.makeText(Register.this,"用户名长度不正确",Toast.LENGTH_SHORT).show();
                }
                else if (mima.length()<6)
                {
                    Toast.makeText(Register.this,"密码长度太短",Toast.LENGTH_SHORT).show();
                }else
                {
                    //异步判断用户名是否重复
                    new AsyncTask<String,Void,Boolean>()
                    {
                        @Override
                        protected Boolean doInBackground(String... params)
                        {
                            return Splash.splashthis.jtds.chongfu(params[0]);
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean)
                        {
                            if (aBoolean)
                            {
                                Toast.makeText(Register.this,"用户名已经被注册过了",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String sql = "insert into yonghuxinxi values ('"+yonghuming.getText().toString().trim()+"','"
                                                                                +mima.getText().toString()+"','"
                                                                                +xuehao.getText().toString()+"','"
                                                                                +getIntent().getStringExtra("openid")+"','"
                                                                                +getIntent().getStringExtra("access_token")+"','"
                                                                                +getIntent().getStringExtra("expires_in") +"',NULL)";
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
                                            Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(Register.this,"注册失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }.execute(sql);
                            }
                        }
                    }.execute(yonghuming.getText().toString().trim());
                }
            }
        });
    }
}
