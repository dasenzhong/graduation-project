package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/4/26.
 */

public class UserResumeWorkExperienceActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_workexperience);

        //列表
        ListView workExperienceList = (ListView) findViewById(R.id.userresume_workexperienceactivity_list);

        //添加经验
        LinearLayout addLayout = (LinearLayout) findViewById(R.id.userresume_workexperienceactivity_addlayout);
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserResumeWorkExperienceActivity.this, UserResumeWorkExperienceAddActivity.class));
            }
        });

        //编辑按钮
        Button edit = (Button) findViewById(R.id.userresume_workexperienceactivity_edit);

        //返回
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_workexperienceactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
