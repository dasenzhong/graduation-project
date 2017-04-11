package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/26.
 */

public class EmployPersonActivity extends Activity {

    private FrameLayout jobInfoLayout;
    private FrameLayout jobAddressLayout;

    private EmployPresonJobInfoFragment jobInfoFragment;                                //兼职信息填写模块
    private EmployPersonJobAddressFragment jobAddressFragment;                          //兼职地址填写模块

    private EmployPersonJobInfoPersonNumberFragment personNumberChooseFragment;         //兼职招聘人数选择模块
    private EmployPersonJobInfoEducationFragment educationChooseFragment;               //兼职学历要求选择模块

    private boolean canInfoLayoutClick = false;                 //拦截点击事件标志
    private boolean canAddressLayoutClick = false;              //拦截点击事件标志

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parttimejob_employperson);

        if (savedInstanceState != null) {
            jobInfoFragment = (EmployPresonJobInfoFragment) getFragmentManager().findFragmentByTag("info");
            jobAddressFragment = (EmployPersonJobAddressFragment) getFragmentManager().findFragmentByTag("address");

            personNumberChooseFragment = (EmployPersonJobInfoPersonNumberFragment) getFragmentManager().findFragmentByTag("personnumber");
            educationChooseFragment = (EmployPersonJobInfoEducationFragment) getFragmentManager().findFragmentByTag("education");

            canAddressLayoutClick = savedInstanceState.getBoolean("canAddressLayoutClick");
            canInfoLayoutClick = savedInstanceState.getBoolean("canInfoLayoutClick");
        }

        showJobInfoFragment();

        jobInfoLayout = (FrameLayout) findViewById(R.id.employperson_infolayout);
        jobInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canInfoLayoutClick) {
                    hideJobAddressFragment();
                    showJobInfoFragment();
                }
            }
        });

        jobAddressLayout = (FrameLayout) findViewById(R.id.employperson_addresslayout);
        jobAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canAddressLayoutClick) {
                    hideJobInfoFragment();
                    showJobAddressFragment();
                }
            }
        });

        //下一步按钮
        Button stepButton = (Button) findViewById(R.id.employperson_button_step);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一步操作
                if (((Button) v).getText().toString().equals(getResources().getString(R.string.employperson_step_next))) {

                    //这里添加一个判定是否填写完全的判断

                    showJobAddressFragment();
                    hideJobInfoFragment();
                    canInfoLayoutClick = true;
                    canAddressLayoutClick = true;
                    ((Button) v).setText(getResources().getString(R.string.employperson_step_send));
                }

                //发布操作
                if (((Button) v).getText().toString().equals(getResources().getString(R.string.employperson_step_send))) {

                    //发布兼职招聘
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
    private void hideJobInfoFragment() {
        if (jobInfoFragment != null && jobInfoFragment.isAdded()) {
            getFragmentManager().beginTransaction().hide(jobInfoFragment).commit();
        }
    }

    /**
     * 隐藏地址fragment
     */
    private void hideJobAddressFragment() {
        if (jobAddressFragment != null && jobAddressFragment.isAdded()) {
            getFragmentManager().beginTransaction().hide(jobAddressFragment).commit();
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
        if (jobAddressFragment == null) {
            jobAddressFragment = new EmployPersonJobAddressFragment();
        }

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
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("canAddressLayoutClick",canAddressLayoutClick);
        outState.putBoolean("canInfoLayoutClick", canInfoLayoutClick);
        super.onSaveInstanceState(outState);
    }
}
