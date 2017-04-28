package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/4/28.
 */

public class UserResumeSchoolHonorAddActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_school_honor_add);

        //等级
        EditText level = (EditText) findViewById(R.id.userresume_school_honor_addactivity_level);

        //奖项
        EditText prize = (EditText) findViewById(R.id.userresume_school_honor_addactivity_prize);

        //时间
        final TextView time = (TextView) findViewById(R.id.userresume_school_honor_addactivity_time);
        RelativeLayout timeLayout = (RelativeLayout) findViewById(R.id.userresume_school_honor_addactivity_timelayout);
        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(UserResumeSchoolHonorAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        time.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        //保存
        Button save = (Button) findViewById(R.id.userresume_school_honor_addactivity_save);

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_school_honor_addactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void hideSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
