package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.Job;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/19.
 */

public class JobInfoActivity extends Activity {

    private TextView jobName;
    private TextView createTime;
    private TextView money;
    private TextView workPlace;
    private TextView employPerson;
    private TextView education;
    private TextView workPlaceDetail;
    private TextView companyName;
    private TextView companyType;
    private TextView companyPerson;
    private TextView companyIndustry;
    private TextView describe;

    private JobInfoApplyFragment applyFragment;
    private int jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobinfo);

        if (getIntent() != null) {
            jobId = getIntent().getIntExtra("jobid", 0);
        }

        if (savedInstanceState != null) {
            applyFragment = (JobInfoApplyFragment) getFragmentManager().findFragmentByTag("apply");
            if (applyFragment != null) {
                setApplyFragmentClick();
            }
        }


        jobName = (TextView) findViewById(R.id.jobinfo_jobname);
        createTime = (TextView) findViewById(R.id.jobinfo_time);
        money = (TextView) findViewById(R.id.jobinfo_money);
        workPlace = (TextView) findViewById(R.id.jobinfo_workplace);
        employPerson = (TextView) findViewById(R.id.jobinfo_employperson);
        education = (TextView) findViewById(R.id.jobinfo_eduction);
        workPlaceDetail = (TextView) findViewById(R.id.jobinfo_workplace_detail);
        companyName = (TextView) findViewById(R.id.jobinfo_companyname);
        companyType = (TextView) findViewById(R.id.jobinfo_companytype);
        companyPerson = (TextView) findViewById(R.id.jobinfo_companyperson);
        companyIndustry = (TextView) findViewById(R.id.jobinfo_companyindustry);
        describe = (TextView) findViewById(R.id.jobinfo_describe);

        //返回操作
        ImageView back = (ImageView) findViewById(R.id.jobinfo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //收藏操作
        FrameLayout collectLayout = (FrameLayout) findViewById(R.id.jobinfo_collectlayout);
        collectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //申请操作
        FrameLayout applyLayout = (FrameLayout) findViewById(R.id.jobinfo_applylayout);
        applyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showApplyLayout();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJob();
    }

    private void loadJob() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getjob/" + jobId).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getnewjob--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final Job job = new ObjectMapper().readValue(response.body().string(), Job.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jobName.setText(job.getJobName());
                            createTime.setText(job.getCreateDate().toString());
                            money.setText(job.getMoney());
                            if (job.getWorkTown() != null) {
                                workPlace.setText(job.getWorkTown());
                            } else {
                                if (job.getWorkCity() != null) {
                                    workPlace.setText(job.getWorkCity());
                                } else {
                                    workPlace.setText(job.getWorkProvince());
                                }
                            }
                            employPerson.setText(job.getNumber());
                            education.setText(job.getEducation());
                            workPlaceDetail.setText(job.getWorkAddress());
                            companyName.setText(job.getCompanyUser().getCompanyName());
                            companyType.setText(job.getCompanyUser().getCompanyType());
                            companyPerson.setText(job.getCompanyUser().getCompanyNumber());
                            companyIndustry.setText(job.getCompanyUser().getCompanyIndustry());
                            describe.setText(job.getDecribe());
                        }
                    });
                } catch (Exception e) {
                    Log.d("addnewjob--failure", e.getMessage());
                }
            }
        });
    }

    /**
     * 显示申请fragment
     */
    private void showApplyLayout() {
        if (applyFragment == null) {
            applyFragment = new JobInfoApplyFragment();
            setApplyFragmentClick();
        }

        if (applyFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(applyFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.jobinfo_mainlayout, applyFragment, "apply").commit();
        }
    }

    /**
     * 移除申请fragment
     */
    private void removeApplyLayout() {
        if (applyFragment.isAdded()) {
            getFragmentManager().beginTransaction().remove(applyFragment).commit();
        }
    }

    /**
     * 设置申请fragment的点击事件
     */
    private void setApplyFragmentClick() {
        //空白点击回调
        applyFragment.setOnBlankClickListener(new JobInfoApplyFragment.OnBlankClickListener() {
            @Override
            public void onBlankClick() {
                removeApplyLayout();
            }
        });

        //申请工作点击回调
        applyFragment.setOnAgentLayoutClickListener(new JobInfoApplyFragment.OnAgentLayoutClickListener() {
            @Override
            public void onAgentLayoutClick() {
                Toast.makeText(JobInfoActivity.this, "你申请了工作", Toast.LENGTH_SHORT).show();
            }
        });

        //申请代理人回调
        applyFragment.setOnJobLayoutClickListener(new JobInfoApplyFragment.OnJobLayoutClickListener() {
            @Override
            public void onJobLayoutClick() {
                Toast.makeText(JobInfoActivity.this, "你申请了成为代理人", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
