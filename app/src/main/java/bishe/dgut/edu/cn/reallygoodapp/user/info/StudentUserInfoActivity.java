package bishe.dgut.edu.cn.reallygoodapp.user.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.cell.UserInfoCellFragment;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StudentUserInfoActivity extends Activity {

    private UserInfoCellFragment avatarFragment, accountFragment, nameFragment, sexFragment, areaFragment, schoolFragment, logFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        avatarFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_avatar);
        accountFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_account);
        nameFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_name);
        sexFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_sex);
        areaFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_area);
        schoolFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_school);
        logFragment = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.userinfo_log);
        setCellAttribute();

        //头像栏点击事件
        avatarFragment.setOnLayoutClickListener(new UserInfoCellFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                startActivity(new Intent(StudentUserInfoActivity.this, AvatarActivity.class));
            }
        });

        //昵称栏点击事件
        nameFragment.setOnLayoutClickListener(new UserInfoCellFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                startActivity(new Intent(StudentUserInfoActivity.this, NameActivity.class));
            }
        });

        //个性签名栏点击事件
        logFragment.setOnLayoutClickListener(new UserInfoCellFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                startActivity(new Intent(StudentUserInfoActivity.this, LogActivity.class));
            }
        });

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userinfo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        setCellAttribute();
        load();
    }

    private void load() {

    }

    private void setCellAttribute() {
        avatarFragment.setTextText("头像");
        avatarFragment.setInputTextGone(true);
        avatarFragment.setAvatarGone(false);

        accountFragment.setTextText("帐号");
        accountFragment.setForewardGone(true);

        nameFragment.setTextText("昵称");

        sexFragment.setTextText("性别");
        sexFragment.setForewardGone(true);

        areaFragment.setTextText("地区");
        areaFragment.setForewardGone(true);

        schoolFragment.setTextText("学校");
        schoolFragment.setForewardGone(true);

        logFragment.setTextText("个性签名");
    }


}
