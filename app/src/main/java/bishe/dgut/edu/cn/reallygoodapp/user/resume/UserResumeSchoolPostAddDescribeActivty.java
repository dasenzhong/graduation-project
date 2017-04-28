package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/4/29.
 */

public class UserResumeSchoolPostAddDescribeActivty extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_school_post_add_describe);

        //输入提示
        final TextView tip = (TextView) findViewById(R.id.userresume_school_post_add_describeactivity_tip);

        //编辑框
        final EditText describe = (EditText) findViewById(R.id.userresume_school_post_add_describeactivity_describe);
        describe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((s.length()) <= 500) {
                    tip.setText(String.valueOf(s.length()));
                } else {
                    describe.setText(s.subSequence(0, 500));
                    describe.setSelection(500);
                }
            }
        });

        //完成
        Button ok = (Button) findViewById(R.id.userresume_school_post_add_describeactivity_finish);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("describe", describe.getText().toString());
                setResult(getResources().getInteger(R.integer.USERRESUME_SCHOOL_POST_ADD_DESCRIBE_RESULTCODE), intent);
                finish();
            }
        });

        //返回
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_school_post_add_describeactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String describeString = getIntent().getStringExtra("describeString");
        if (describeString != null) {
            describe.setText(describeString);
            describe.setSelection(describe.length());
        }
    }
}
