package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/5.
 */

public class RegisterCompanyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        EditText account = (EditText) findViewById(R.id.register_company_account);
        ImageView accountClear = (ImageView) findViewById(R.id.register_company_accountclear);

        EditText password = (EditText) findViewById(R.id.register_company_password);
        ImageView passwordClear = (ImageView) findViewById(R.id.register_company_passwordclear);

        ImageView visibility = (ImageView) findViewById(R.id.register_company_visibility);

        RelativeLayout areaLayout = (RelativeLayout) findViewById(R.id.register_company_arealayout);
        TextView area = (TextView) findViewById(R.id.register_company_area);

        EditText companyName = (EditText) findViewById(R.id.register_company_name);

        TextView ok = (TextView) findViewById(R.id.register_company_ok);

        ImageView back = (ImageView) findViewById(R.id.register_company_back);

    }
}
