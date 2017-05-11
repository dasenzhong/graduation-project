package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/4/27.
 */

public class UserResumeWorkExperienceAddActivity extends Activity {

    private String describeString;          //职位描述
    private TextView describe;
    private TextView entry;
    private TextView leave;
    private EditText company;
    private EditText job;

    private String type="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_workexperience_add);


        //入职时间
        entry = (TextView) findViewById(R.id.userresume_workexperience_addactivity_entry);
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
        leave = (TextView) findViewById(R.id.userresume_workexperience_addactivity_leave);
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
        company = (EditText) findViewById(R.id.userresume_workexperience_addactivity_company);
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
        job = (EditText) findViewById(R.id.userresume_workexperience_addactivity_job);
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
        final RadioButton allTime = (RadioButton) findViewById(R.id.userresume_workexperience_addactivity_alltime);
        final RadioButton partTime = (RadioButton) findViewById(R.id.userresume_workexperience_addactivity_parttime);
        allTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideSoftInput(buttonView);
                if (isChecked) {
                    partTime.setChecked(false);
                    type = "全职";
                }
            }
        });
        partTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideSoftInput(buttonView);
                if (isChecked) {
                    allTime.setChecked(false);
                    type = "兼职";
                }
            }
        });

        //保存
        Button save = (Button) findViewById(R.id.userresume_workexperience_addactivity_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
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

    /**
     * 发送到服务器
     */
    private void sendToServer() {

        if (entry.getText().equals("")) {
            Toast.makeText(this, "请选择入职时间", Toast.LENGTH_SHORT).show();
        } else if (leave.getText().equals("")) {
            Toast.makeText(this, "请选择离职时间", Toast.LENGTH_SHORT).show();
        } else if (company.getText().toString().equals("")) {
            Toast.makeText(this, "请填写公司名称", Toast.LENGTH_SHORT).show();
        } else if (job.getText().toString().equals("")) {
            Toast.makeText(this, "请填写公司职位名", Toast.LENGTH_SHORT).show();
        } else if (describeString.isEmpty() || describeString == null) {
            Toast.makeText(this, "请填写职位描述", Toast.LENGTH_SHORT).show();
        } else if (type == null || type.isEmpty()) {
            Toast.makeText(this, "请选择类型", Toast.LENGTH_SHORT).show();
        } else {

            MultipartBody body = new MultipartBody.Builder()
                    .addFormDataPart("startTime", entry.getText().toString())
                    .addFormDataPart("endTime", leave.getText().toString())
                    .addFormDataPart("companyName", company.getText().toString())
                    .addFormDataPart("companyPost", job.getText().toString())
                    .addFormDataPart("describe", describeString)
                    .addFormDataPart("type", type)
                    .build();

            //稍等进度条
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("请稍等");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Link.getClient().newCall(
                    Link.getRequestAddress("/addexperience").post(body).build()
            ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            new AlertDialog.Builder(UserResumeWorkExperienceAddActivity.this)
                                    .setMessage(e.getMessage())
                                    .setTitle("请求失败")
                                    .setNegativeButton("好", null).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    //从服务器接受到数据
                    final String responseString = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            try {
                                new AlertDialog.Builder(UserResumeWorkExperienceAddActivity.this)
                                        .setTitle("请求成功")
                                        .setMessage(responseString)
                                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            } catch (Exception e) {
                                new AlertDialog.Builder(UserResumeWorkExperienceAddActivity.this)
                                        .setMessage(e.getMessage())
                                        .setTitle("回应失败")
                                        .setNegativeButton("好", null).show();
                            }
                        }
                    });
                }
            });

        }
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
//                    Log.d("描述", "-------" + describeString);
                } else {
                    describe.setVisibility(View.GONE);
                }
            }
        }
    }
}
