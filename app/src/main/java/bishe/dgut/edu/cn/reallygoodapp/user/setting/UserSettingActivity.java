package bishe.dgut.edu.cn.reallygoodapp.user.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.cell.UserSettingCellFragment;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        UserSettingCellFragment aboutCell = (UserSettingCellFragment) getFragmentManager().findFragmentById(R.id.usersetting_about);
        aboutCell.setTextText(getResources().getString(R.string.user_setting_about_actionbar_title));
        aboutCell.setOnCellClickListener(new UserSettingCellFragment.OnCellClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(UserSettingActivity.this, AboutActivity.class));
            }
        });

        LinearLayout back = (LinearLayout) findViewById(R.id.usersetting_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
