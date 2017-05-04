package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText account = (EditText) findViewById(R.id.login_account);
        ImageView accountClear = (ImageView) findViewById(R.id.login_accountclear);

        EditText password = (EditText) findViewById(R.id.login_password);
        ImageView passwordClear = (ImageView) findViewById(R.id.login_passwordclear);

        ImageView visibility = (ImageView) findViewById(R.id.login_visibility);

        Button signIn = (Button) findViewById(R.id.login_signin);

        TextView register = (TextView) findViewById(R.id.login_register);

        ImageView back = (ImageView) findViewById(R.id.login_back);


    }
}
