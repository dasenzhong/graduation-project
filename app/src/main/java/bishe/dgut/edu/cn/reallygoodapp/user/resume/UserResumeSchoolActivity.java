package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/4/28.
 */

public class UserResumeSchoolActivity extends Activity {

    private UserResumeSchoolHonorFragment honorFragment;
    private UserResumeSchoolPostFragment postFragment;

    private boolean isHonorShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_school);


        final FrameLayout honorLayout = (FrameLayout) findViewById(R.id.userresume_schoolactivity_honorlayout);
        final TextView honorText = (TextView) findViewById(R.id.userresume_schoolactivity_honortext);
        final FrameLayout postLayout = (FrameLayout) findViewById(R.id.userresume_schoolactivity_postlayout);
        final TextView postText = (TextView) findViewById(R.id.userresume_schoolactivity_posttext);

        //编辑
        Button edit = (Button) findViewById(R.id.userresume_schoolactivity_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //编辑→完成操作，主要删除不要的校内情况
            }
        });

        //返回
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_schoolactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        honorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    hidePostFragment();
                    showHonorFramgnt();
                    setToggle(honorLayout, honorText, postLayout, postText);
                }
            }
        });
        postLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    hideHonorFragment();
                    showPostFragmnet();
                    setToggle(postLayout, postText, honorLayout, honorText);
                }
            }
        });

        if (savedInstanceState != null) {
            honorFragment = (UserResumeSchoolHonorFragment) getFragmentManager().findFragmentByTag("honor");
            postFragment = (UserResumeSchoolPostFragment) getFragmentManager().findFragmentByTag("post");
            isHonorShow = savedInstanceState.getBoolean("isHonorShow", true);
        } else {
            isHonorShow = true;
        }

        if (isHonorShow) {
            hidePostFragment();
            showHonorFramgnt();
            setToggle(honorLayout, honorText, postLayout, postText);
        } else {
            hideHonorFragment();
            showPostFragmnet();
            setToggle(postLayout, postText, honorLayout, honorText);
        }
    }

    /**
     * 显示校内荣誉界面
     */
    private void showHonorFramgnt() {
        if (honorFragment == null) {
            honorFragment = new UserResumeSchoolHonorFragment();
        }

        if (honorFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(honorFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.userresume_schoolactivity_container, honorFragment, "honor").commit();
        }
        isHonorShow = true;
    }

    /**
     * 隐藏校内荣誉界面
     */
    private void hideHonorFragment() {
        if (honorFragment != null) {
            if (honorFragment.isAdded()) {
                getFragmentManager().beginTransaction().hide(honorFragment).commit();
            }
        }
    }

    /**
     * 显示校内职务界面
     */
    private void showPostFragmnet() {
        if (postFragment == null) {
            postFragment = new UserResumeSchoolPostFragment();
        }

        if (postFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(postFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.userresume_schoolactivity_container, postFragment, "post").commit();
        }
        isHonorShow = false;
    }

    /**
     * 隐藏校内职务界面
     */
    private void hidePostFragment() {
        if (postFragment != null) {
            if (postFragment.isAdded()) {
                getFragmentManager().beginTransaction().hide(postFragment).commit();
            }
        }
    }

    /**
     * 设置颜色选择反转
     *
     * @param selectLayout   要显示选择的layout
     * @param selectText     要显示选择的text
     * @param unSelectLayout 要显示不选择的layout
     * @param unSelectText   要显示不选择的text
     */
    private void setToggle(FrameLayout selectLayout, TextView selectText, FrameLayout unSelectLayout, TextView unSelectText) {
        selectLayout.setSelected(true);
        selectText.setSelected(true);
        unSelectLayout.setSelected(false);
        unSelectText.setSelected(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isHonorShow", isHonorShow);
        super.onSaveInstanceState(outState);
    }
}
