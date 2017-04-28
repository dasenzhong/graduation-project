package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
 * Created on 2017/4/29.
 */

public class UserResumeSchoolPostAddActivity extends Activity {

    private String describeString;          //职务描述

    private TextView postDescribeNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_school_post_add);

        //开始时间
        final TextView startTime = (TextView) findViewById(R.id.userresume_school_post_addactivity_starttime);
        RelativeLayout startTimeLayout = (RelativeLayout) findViewById(R.id.userresume_school_post_addactivity_starttimelayout);
        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(UserResumeSchoolPostAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startTime.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        //结束时间
        final TextView endTime = (TextView) findViewById(R.id.userresume_school_post_addactivity_endtime);
        RelativeLayout endTimeLayout = (RelativeLayout) findViewById(R.id.userresume_school_post_addactivity_endtimelayout);
        endTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(UserResumeSchoolPostAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endTime.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        //职务名称
        EditText postName = (EditText) findViewById(R.id.userresume_school_post_addactivity_postname);

        //职务描述
        postDescribeNumber = (TextView) findViewById(R.id.userresume_school_post_addactivity_postdescribenumber);
        RelativeLayout postDescribeLayout = (RelativeLayout) findViewById(R.id.userresume_school_post_addactivity_postdescribelayout);
        postDescribeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserResumeSchoolPostAddActivity.this, UserResumeSchoolPostAddDescribeActivty.class);
                if (describeString != null) {
                    intent.putExtra("describeString", describeString);
                }
                startActivityForResult(intent, getResources().getInteger(R.integer.USERRESUME_SCHOOL_POST_ADD_DESCRIBE_REQUESTCODE));
            }
        });

        //保存
        Button save = (Button) findViewById(R.id.userresume_school_post_addactivity_save);

        //返回
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_school_post_addactivity_back);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getResources().getInteger(R.integer.USERRESUME_SCHOOL_POST_ADD_DESCRIBE_REQUESTCODE)) {
            if (resultCode == getResources().getInteger(R.integer.USERRESUME_SCHOOL_POST_ADD_DESCRIBE_RESULTCODE)) {
                describeString = data.getStringExtra("describe");
                if (describeString != null && !describeString.isEmpty()) {
                    postDescribeNumber.setVisibility(View.VISIBLE);
                    postDescribeNumber.setText("已填" + describeString.length() + "字");
                    Log.d("描述", "-------" + describeString);
                } else {
                    postDescribeNumber.setVisibility(View.GONE);
                }
            }
        }
    }
}
