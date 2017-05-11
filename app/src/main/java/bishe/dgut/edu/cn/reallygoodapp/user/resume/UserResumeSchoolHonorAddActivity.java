package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
 * Created on 2017/4/28.
 */

public class UserResumeSchoolHonorAddActivity extends Activity {

    private EditText level;
    private EditText prize;
    private TextView time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_school_honor_add);

        //等级
        level = (EditText) findViewById(R.id.userresume_school_honor_addactivity_level);

        //奖项
        prize = (EditText) findViewById(R.id.userresume_school_honor_addactivity_prize);

        //时间
        time = (TextView) findViewById(R.id.userresume_school_honor_addactivity_time);
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
            }
        });

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_school_honor_addactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendToServer() {

        if (time.getText().equals("")) {
            Toast.makeText(this, "请选择时间", Toast.LENGTH_SHORT).show();
        } else if (prize.getText().toString().equals("")) {
            Toast.makeText(this, "请填写荣誉名称", Toast.LENGTH_SHORT).show();
        } else if (level.getText().toString().equals("")) {
            Toast.makeText(this, "请填写奖项等级", Toast.LENGTH_SHORT).show();
        }  else {

            MultipartBody body = new MultipartBody.Builder()
                    .addFormDataPart("time", time.getText().toString())
                    .addFormDataPart("honorName", prize.getText().toString())
                    .addFormDataPart("level", level.getText().toString())
                    .build();

            //稍等进度条
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("请稍等");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Link.getClient().newCall(
                    Link.getRequestAddress("/addhonor").post(body).build()
            ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            new AlertDialog.Builder(UserResumeSchoolHonorAddActivity.this)
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
                                new AlertDialog.Builder(UserResumeSchoolHonorAddActivity.this)
                                        .setTitle("请求成功")
                                        .setMessage(responseString)
                                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            } catch (Exception e) {
                                new AlertDialog.Builder(UserResumeSchoolHonorAddActivity.this)
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
}
