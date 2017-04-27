package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * Created on 2017/4/27.
 */

public class UserResumeWorkExperienceAddActivity extends Activity {

    private String describeString;
    private TextView describe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_workexperience_add);


        //入职时间
        final TextView entry = (TextView) findViewById(R.id.userresume_workexperience_addactivity_entry);
        RelativeLayout entryLayout = (RelativeLayout) findViewById(R.id.userresume_workexperience_addactivity_entrylayout);
        entryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(UserResumeWorkExperienceAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        entry.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //离职时间
        final TextView leave = (TextView) findViewById(R.id.userresume_workexperience_addactivity_leave);
        RelativeLayout leaveLayout = (RelativeLayout) findViewById(R.id.userresume_workexperience_addactivity_leavelayout);
        leaveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(UserResumeWorkExperienceAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        leave.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //公司
        EditText company = (EditText) findViewById(R.id.userresume_workexperience_addactivity_company);
        company.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput(v);
                }
                return false;
            }
        });

        //职位
        EditText job = (EditText) findViewById(R.id.userresume_workexperience_addactivity_job);
        job.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput(v);
                }
                return false;
            }
        });

        //描述
        describe = (TextView) findViewById(R.id.userresume_workexperience_addactivity_describe);
        RelativeLayout describeLayout = (RelativeLayout) findViewById(R.id.userresume_workexperience_addactivity_describelayout);
        describeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserResumeWorkExperienceAddActivity.this, UserResumeWorkExperienceAddDescribeActivity.class);
                if (describeString != null) {
                    intent.putExtra("describeString", describeString);
                }
                startActivityForResult(intent, getResources().getInteger(R.integer.USERRESUME_WORKEXPERIENCE_ADD_DESCRIBE_REQUESTCODE));
            }
        });

        //全职
        final CheckBox allTime = (CheckBox) findViewById(R.id.userresume_workexperience_addactivity_alltime);
        final CheckBox partTime = (CheckBox) findViewById(R.id.userresume_workexperience_addactivity_parttime);
        allTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideSoftInput(buttonView);
                if (isChecked) {
                    partTime.setChecked(false);
                }
            }
        });
        partTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideSoftInput(buttonView);
                if (isChecked) {
                    allTime.setChecked(false);
                }
            }
        });

        //保存
        Button save = (Button) findViewById(R.id.userresume_workexperience_addactivity_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //保存上传操作


            }
        });

        //返回
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_workexperience_addactivity_back);
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
        if (requestCode == getResources().getInteger(R.integer.USERRESUME_WORKEXPERIENCE_ADD_DESCRIBE_REQUESTCODE)) {
            if (resultCode == getResources().getInteger(R.integer.USERRESUME_WORKEXPERIENCE_ADD_DESCRIBE_RESULTCODE)) {
                describeString = data.getStringExtra("describe");
                if (describeString != null && !describeString.isEmpty()) {
                    describe.setVisibility(View.VISIBLE);
                    describe.setText("已填" + describeString.length() + "字");
                    Log.d("描述", "-------" + describeString);
                } else {
                    describe.setVisibility(View.GONE);
                }
            }
        }
    }
}
