package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/26.
 */

public class EmployPersonActivity extends Activity {

    private FrameLayout jobInfoLayout;
//    private FrameLayout jobAddressLayout;

    private EmployPresonJobInfoFragment jobInfoFragment;                                //兼职信息填写模块
    private EmployPersonJobAddressFragment jobAddressFragment;                          //兼职地址填写模块

    private EmployPersonJobInfoPersonNumberFragment personNumberChooseFragment;         //兼职招聘人数选择模块
    private EmployPersonJobInfoEducationFragment educationChooseFragment;               //兼职学历要求选择模块

    private boolean canInfoLayoutClick = false;                 //拦截点击事件标志
//    private boolean canAddressLayoutClick = false;              //拦截点击事件标志

    private Toast noticeToast;

    private String jobName;             //工作名
    private String jobType;             //工作类型
    private String education;           //学历
    private String personNumber;        //招聘人数
    private String money;               //薪酬
    private String describe;            //工作描述

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parttimejob_employperson);

        if (savedInstanceState != null) {
            jobInfoFragment = (EmployPresonJobInfoFragment) getFragmentManager().findFragmentByTag("info");
            jobAddressFragment = (EmployPersonJobAddressFragment) getFragmentManager().findFragmentByTag("address");

            personNumberChooseFragment = (EmployPersonJobInfoPersonNumberFragment) getFragmentManager().findFragmentByTag("personnumber");
            educationChooseFragment = (EmployPersonJobInfoEducationFragment) getFragmentManager().findFragmentByTag("education");

//            canAddressLayoutClick = savedInstanceState.getBoolean("canAddressLayoutClick");
            canInfoLayoutClick = savedInstanceState.getBoolean("canInfoLayoutClick");

            jobName = savedInstanceState.getString("jobName");
            jobType = savedInstanceState.getString("jobType");
            education = savedInstanceState.getString("education");
            personNumber = savedInstanceState.getString("personNumber");
            money = savedInstanceState.getString("money");
            describe = savedInstanceState.getString("describe");
        }

        noticeToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        noticeToast.setGravity(Gravity.CENTER, 0, 20);

        showJobInfoFragment();

//        jobAddressLayout = (FrameLayout) findViewById(R.id.employperson_addresslayout);
//        jobAddressLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (canAddressLayoutClick) {
//                    hideJobInfoFragment();
//                    showJobAddressFragment();
//                }
//            }
//        });

        //下一步按钮
        final Button stepButton = (Button) findViewById(R.id.employperson_button_step);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一步操作
                if (((Button) v).getText().toString().equals(getResources().getString(R.string.employperson_step_next))) {

                    //这里添加一个判定是否填写完全的判断
                    jobName = jobInfoFragment.getJobNameViewText();
                    jobType = jobInfoFragment.getJobTypeViewText();
                    education = jobInfoFragment.getEducationViewText();
                    personNumber = jobInfoFragment.getPersonNumberViewText();
                    money = jobInfoFragment.getMoneyViewText();
                    describe = jobInfoFragment.getDescribeText();

                    if (jobName.isEmpty()) {
                        noticeToast.setText("请输入你要发布工作的名字");
                        noticeToast.show();
                        return;
                    }
                    if (jobType.isEmpty()) {
                        noticeToast.setText("请选择工作类型");
                        noticeToast.show();
                        return;
                    }
                    if (education.isEmpty()) {
                        education = "不限";
                    }
                    if (personNumber.isEmpty()) {
                        personNumber = "若干人";
                    }
                    if (jobInfoFragment.getMoneyText().isEmpty()) {
                        noticeToast.setText("请输入薪酬");
                        noticeToast.show();
                        return;
                    }

                    jobInfoLayout.setBackgroundColor(ContextCompat.getColor(EmployPersonActivity.this, R.color.orange));
                    showJobAddressFragment();
                    hideSoftwareInput();
                    canInfoLayoutClick = true;
//                    canAddressLayoutClick = true;
                    ((Button) v).setText(getResources().getString(R.string.employperson_step_send));
                    return;
//                    Log.d("money-----", money);
                }

                //发布操作
                if (((Button) v).getText().toString().equals(getResources().getString(R.string.employperson_step_send))) {

                    //发布兼职招聘
                    if (jobAddressFragment.getAreaText().isEmpty()) {
                        noticeToast.setText("请选择招聘地址");
                        noticeToast.show();
                        return;
                    }

                    if (jobAddressFragment.getWorkPlaceText().isEmpty() || jobAddressFragment.getDetailWorkPlaceText().isEmpty()) {
                        noticeToast.setText("工作地址未填写完成");
                        noticeToast.show();
                        return;
                    }
                    Log.d("jobName:", jobName);
                    Log.d("jobType:", jobType);
                    Log.d("education", education);
                    Log.d("personNumber", personNumber);
                    Log.d("money:", money);
                    Log.d("describe:", describe);
                    Log.d("招聘地址：", jobAddressFragment.getProvinceNameArea() + "," + jobAddressFragment.getCityNameArea() + "," + jobAddressFragment.getTownNameArea());
                    Log.d("工作地址:", jobAddressFragment.getProvinceNameWorkPlace() + "," + jobAddressFragment.getCityNameWorkPlace() + "," + jobAddressFragment.getTownNameWorkPlace());
                }
            }
        });

        //步骤显示条
        jobInfoLayout = (FrameLayout) findViewById(R.id.employperson_infolayout);
        if (canInfoLayoutClick) {
            jobInfoLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            stepButton.setText(getResources().getString(R.string.employperson_step_send));
        }
        jobInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canInfoLayoutClick) {
                    stepButton.setText("下一步");
                    canInfoLayoutClick = false;
                    v.setBackground(getDrawable(R.drawable.employperson_step_layout_background));
                    hideJobAddressFragment();
                    hideSoftwareInput();
                }
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.employperson_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //兼职招聘人数选择模块
        if (personNumberChooseFragment == null) {
            personNumberChooseFragment = new EmployPersonJobInfoPersonNumberFragment();
        }
        personNumberChooseFragment.setOnLayoutClickListener(new EmployPersonJobInfoPersonNumberFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                if (personNumberChooseFragment.isAdded() && personNumberChooseFragment != null) {
                    getFragmentManager().beginTransaction().remove(personNumberChooseFragment).commit();
                }
            }
        });
        personNumberChooseFragment.setOkClickListener(new EmployPersonJobInfoPersonNumberFragment.OnOkClickListener() {
            @Override
            public void onOkClick(String personNumberText) {
                jobInfoFragment.setPersonNumberViewText(personNumberText);
            }
        });

        //兼职学历要求选择模块
        if (educationChooseFragment == null) {
            educationChooseFragment = new EmployPersonJobInfoEducationFragment();
        }
        educationChooseFragment.setOnLayoutClickListener(new EmployPersonJobInfoEducationFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                if (educationChooseFragment.isAdded() && educationChooseFragment != null) {
                    getFragmentManager().beginTransaction().remove(educationChooseFragment).commit();
                }
            }
        });
        educationChooseFragment.setOnOkClickListener(new EmployPersonJobInfoEducationFragment.OnOkClickListener() {
            @Override
            public void onOkClick(String educationText) {
                jobInfoFragment.setEducationViewText(educationText);
            }
        });
    }

    /**
     * 隐藏信息fragment
     */
//    private void hideJobInfoFragment() {
//        if (jobInfoFragment != null && jobInfoFragment.isAdded()) {
//            getFragmentManager().beginTransaction().hide(jobInfoFragment).commit();
//        }
//    }

    /**
     * 隐藏地址fragment
     */
    private void hideJobAddressFragment() {
        if (jobAddressFragment != null && jobAddressFragment.isAdded()) {
//            getFragmentManager().beginTransaction().hide(jobAddressFragment).commit();
            getFragmentManager().beginTransaction().remove(jobAddressFragment).commit();
        }
    }

    /**
     * 显示信息fragment
     */
    private void showJobInfoFragment() {
        if (jobInfoFragment == null) {
            jobInfoFragment = new EmployPresonJobInfoFragment();
        }

        jobInfoFragment.setOnShowChooseFragmentListener(new EmployPresonJobInfoFragment.OnShowChooseFragmentListener() {
            @Override
            public void onShowChooseFragment(View view) {
                switch (view.getId()) {
                    case R.id.employperson_info_jobtype:                //弹出类型模块
                        startActivityForResult(new Intent(EmployPersonActivity.this, EmployPersonJobInfoJobTypeActivity.class), getResources().getInteger(R.integer.JOBTYPE_REQUESTCODE));
                        break;
                    case R.id.employperson_info_personnumber:           //弹出人数模块
                        showChooseFragment(personNumberChooseFragment, "personnumber");
                        break;
                    case R.id.employperson_info_education:              //弹出学历模块
                        showChooseFragment(educationChooseFragment, "education");
                        break;
                    default:
                }
            }
        });

        if (!jobInfoFragment.isAdded()) {
            getFragmentManager().beginTransaction().add(R.id.employperson_container, jobInfoFragment, "info").commit();
        } else {
            getFragmentManager().beginTransaction().show(jobInfoFragment).commit();
        }
    }


    /**
     * 显示地址fragment
     */
    private void showJobAddressFragment() {
        jobAddressFragment = new EmployPersonJobAddressFragment();


        if (!jobAddressFragment.isAdded()) {
            getFragmentManager().beginTransaction().add(R.id.employperson_container, jobAddressFragment, "address").commit();
        } else {
            getFragmentManager().beginTransaction().show(jobAddressFragment).commit();
        }
    }

    /**
     * 显示选择fragment
     *
     * @param fragment 要显示的fragment
     * @param tag      标记
     */
    private void showChooseFragment(Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            getFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.employperson_container_choose, fragment, tag).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.JOBTYPE_REQUESTCODE)) {
            if (resultCode == getResources().getInteger(R.integer.JOBTYPE_RESULTCODE)) {
                String text = data.getStringExtra("choosetext");
                if (text.isEmpty()) {
                    jobInfoFragment.setJobTypeViewText("");
                } else {
                    jobInfoFragment.setJobTypeViewText(text);
                }
            }
        }
    }

    private void hideSoftwareInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putBoolean("canAddressLayoutClick",canAddressLayoutClick);
        outState.putBoolean("canInfoLayoutClick", canInfoLayoutClick);
        outState.putString("jobName", jobName);
        outState.putString("jobType", jobType);
        outState.putString("education", education);
        outState.putString("personNumber", personNumber);
        outState.putString("money", money);
        outState.putString("describe", describe);
        super.onSaveInstanceState(outState);
    }
}
