package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/5.
 */

public class LoginActivity extends Activity {

    private AlertDialog.Builder registerChoose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //账号输入
        EditText account = (EditText) findViewById(R.id.login_account);
        ImageView accountClear = (ImageView) findViewById(R.id.login_accountclear);

        //密码输入
        EditText password = (EditText) findViewById(R.id.login_password);
        ImageView passwordClear = (ImageView) findViewById(R.id.login_passwordclear);

        //可视
        ImageView visibility = (ImageView) findViewById(R.id.login_visibility);

        //登录
        Button signIn = (Button) findViewById(R.id.login_signin);

        //注册按钮
        TextView register = (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               register();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void register() {
        String[] items = {
                "我是学生",
                "我是公司代表"
        };

        if (registerChoose == null) {
            registerChoose =  new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("请选择注册类型")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    startActivity(new Intent(LoginActivity.this, RegisterStudentActivity.class));
                                    break;

                                case 1:
                                    startActivity(new Intent(LoginActivity.this, RegisterCompanyActivity.class));
                                    break;

                                default:
                                    break;

                            }
                        }
                    }).setNegativeButton("取消",null);
        }

        registerChoose.show();
    }
}
