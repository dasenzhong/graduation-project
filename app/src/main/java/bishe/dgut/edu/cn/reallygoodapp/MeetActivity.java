package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.Calendar;

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
 * Created on 2017/5/18.
 */

public class MeetActivity extends Activity {

    private TextView time1;
    private TextView time2;
    private EditText address;
    private EditText telephone;

    private int newscompanyId;
    private int jobId;
    private int studentUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        if (getIntent() != null) {
            newscompanyId = getIntent().getIntExtra("newscompanyid", -1);
            jobId = getIntent().getIntExtra("jobid", -1);
            studentUserId = getIntent().getIntExtra("studentuserid", -1);
        }

        time1 = (TextView) findViewById(R.id.meet_time1);
        time2 = (TextView) findViewById(R.id.meet_time2);
        address = (EditText) findViewById(R.id.meet_address);
        telephone = (EditText) findViewById(R.id.meet_telephone);

        RelativeLayout timeLayout1 = (RelativeLayout) findViewById(R.id.meet_timelayout1);
        timeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(MeetActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        time1.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        RelativeLayout timeLayout2 = (RelativeLayout) findViewById(R.id.meet_timelayout2);
        timeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(MeetActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time2.setText(hourOfDay + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            }
        });

        TextView ok = (TextView) findViewById(R.id.meet_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passSendToServer();
            }
        });

        ImageView back = (ImageView) findViewById(R.id.meet_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void passSendToServer() {
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("studentUserId", String.valueOf(studentUserId))
                .addFormDataPart("jobId", String.valueOf(jobId))
                .addFormDataPart("newscompanyId", String.valueOf(newscompanyId))
                .addFormDataPart("meettime", time1.getText().toString() + "  " + time2.getText().toString())
                .addFormDataPart("meetaddress", address.getText().toString())
                .addFormDataPart("telephone", telephone.getText().toString())
                .build();

        //稍等进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍等");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Link.getClient().newCall(
                Link.getRequestAddress("/jobapplypass").post(body).build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        new AlertDialog.Builder(MeetActivity.this)
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
                            new AlertDialog.Builder(MeetActivity.this)
                                    .setTitle("请求成功")
                                    .setMessage(responseString)
                                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        } catch (Exception e) {
                            new AlertDialog.Builder(MeetActivity.this)
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
