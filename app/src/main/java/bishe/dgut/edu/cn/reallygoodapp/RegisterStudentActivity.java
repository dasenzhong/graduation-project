package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/5.
 */

public class RegisterStudentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        EditText account = (EditText) findViewById(R.id.register_company_account);
        ImageView accountClear = (ImageView) findViewById(R.id.register_student_accountclear);

        EditText password = (EditText) findViewById(R.id.register_student_password);
        ImageView passwwrdClear = (ImageView) findViewById(R.id.register_student_passwordclear);

        ImageView visibility = (ImageView) findViewById(R.id.register_student_visibility);

        LinearLayout maleLayout = (LinearLayout) findViewById(R.id.register_student_malelayout);
        ImageView maleImage = (ImageView) findViewById(R.id.register_student_maleimage);
        TextView maleText = (TextView) findViewById(R.id.register_student_maletext);

        LinearLayout femaleLayout = (LinearLayout) findViewById(R.id.register_student_femalelayout);
        ImageView femaleImage = (ImageView) findViewById(R.id.register_student_femaleimage);
        TextView femaleText = (TextView) findViewById(R.id.register_student_femaletext);

        TextView ok = (TextView) findViewById(R.id.register_student_ok);

        ImageView back = (ImageView) findViewById(R.id.register_student_back);

    }
}
