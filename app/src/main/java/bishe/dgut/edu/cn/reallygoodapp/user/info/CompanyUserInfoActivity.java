package bishe.dgut.edu.cn.reallygoodapp.user.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.cell.UserInfoCellFragment;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/18.
 */

public class CompanyUserInfoActivity extends Activity {

    private UserInfoCellFragment avatar, account, companyName, companyType, companyNumber, companyIndustry, companyArea, log;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_user_info);

        avatar = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_avatar);
        account = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_account);
        companyName = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_companyname);
        companyNumber = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_companynumber);
        companyType = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_companytype);
        companyIndustry = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_industry);
        companyArea = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_area);
        log = (UserInfoCellFragment) getFragmentManager().findFragmentById(R.id.companyuserinfo_log);
        setCellAttribute();

        //头像栏点击事件
        avatar.setOnLayoutClickListener(new UserInfoCellFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                startActivity(new Intent(CompanyUserInfoActivity.this, AvatarActivity.class));
            }
        });

        //个性签名栏点击事件
        log.setOnLayoutClickListener(new UserInfoCellFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick() {
                startActivity(new Intent(CompanyUserInfoActivity.this, LogActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {

    }

    private void setCellAttribute() {
        avatar.setTextText("头像");
        avatar.setInputTextGone(true);
        avatar.setAvatarGone(false);

        account.setTextText("帐号");
        account.setForewardGone(true);

        companyName.setTextText("名称");
        companyName.setForewardGone(true);

        companyType.setTextText("类型");
        companyType.setForewardGone(true);

        companyNumber.setTextText("规模");
        companyNumber.setForewardGone(true);

        companyIndustry.setTextText("行业");
        companyIndustry.setForewardGone(true);

        companyArea.setTextText("地区");
        companyArea.setForewardGone(true);

        log.setTextText("个性签名");
    }
}
