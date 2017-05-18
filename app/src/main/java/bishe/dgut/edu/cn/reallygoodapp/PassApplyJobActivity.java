package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.Job;
import bishe.dgut.edu.cn.reallygoodapp.bean.NewsStudent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/18.
 */

public class PassApplyJobActivity extends Activity {

    private TextView jobName;
    private TextView jobMoney;
    private TextView meetTime;
    private TextView meetAddress;
    private TextView meetTelephone;

    private int newsId;
    private NewsStudent newsStudent;
    private Job job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_passapply_job);

        if (getIntent() != null) {
            newsId = getIntent().getIntExtra("newsId", -1);
        }

        jobName = (TextView) findViewById(R.id.pass_dealapply_jobname);
        jobMoney = (TextView) findViewById(R.id.pass_dealapply_jobmoney);
        meetTime = (TextView) findViewById(R.id.pass_dealapply_meettime);
        meetAddress = (TextView) findViewById(R.id.pass_dealapply_meetaddress);
        meetTelephone = (TextView) findViewById(R.id.pass_dealapply_telephone);

        //评价
        FrameLayout evaluation = (FrameLayout) findViewById(R.id.pass_dealapply_evalautionLayout);
        evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEvaluation();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.pass_dealapply_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void goEvaluation() {
        Intent intent = new Intent(PassApplyJobActivity.this, EvaluationStudentActivity.class);
        intent.putExtra("companyuserId", job.getCompanyUser().getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNews();
    }

    private void loadNews() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getstudentnewsbyid/" + newsId).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getnewsbyid--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    newsStudent = new ObjectMapper().readValue(response.body().string(), NewsStudent.class);
                    if (newsStudent != null) {
                        job = newsStudent.getJob();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                jobName.setText(job.getJobName());
                                jobMoney.setText(job.getMoney());

                                meetTime.setText(newsStudent.getMeetTime());
                                meetAddress.setText(newsStudent.getMeetAddress());
                                meetTelephone.setText(newsStudent.getTelephone());
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.d("addnewsbyid--failure", e.getMessage());
                }
            }
        });
    }
}
