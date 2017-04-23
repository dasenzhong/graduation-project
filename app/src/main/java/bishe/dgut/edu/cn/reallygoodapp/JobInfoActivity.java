package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobinfo);

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
