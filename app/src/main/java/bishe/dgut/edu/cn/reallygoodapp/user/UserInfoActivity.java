package bishe.dgut.edu.cn.reallygoodapp.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.cell.UserInfoCellFragment;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserInfoActivity extends Activity {

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
        LinearLayout back = (LinearLayout) findViewById(R.id.userinfo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setCellAttribute();


    }

    private void setCellAttribute() {
        avatarFragment.setAvatarGone(false);
        avatarFragment.setInputTextGone(true);
        accountFragment.setForewardGone(true);

        avatarFragment.setTextText("头像");
        accountFragment.setTextText("帐号");
        nameFragment.setTextText("名字");
        sexFragment.setTextText("性别");
        areaFragment.setTextText("地区");
        schoolFragment.setTextText("学校");
        logFragment.setTextText("个性签名");
    }


}
