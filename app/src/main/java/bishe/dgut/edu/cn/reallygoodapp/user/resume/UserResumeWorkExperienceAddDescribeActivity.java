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
 * Created on 2017/4/27.
 */

public class UserResumeWorkExperienceAddDescribeActivity extends Activity {

//    private int numberCounter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_workexperience_add_describe);

        //输入字数提示框
        final TextView tip = (TextView) findViewById(R.id.userresume_workexperience_add_describeactivity_tip);


        //描述输入框
        final EditText describe = (EditText) findViewById(R.id.userresume_workexperience_add_describeactivity_describe);
        describe.addTextChangedListener(new TextWatcher() {
            /**
             * text改变后的内容
             * @param s 之前的内容
             * @param start 最后一个空格后第一个字符位置
             * @param count 输入的字体数
             * @param after 添加的文字总数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e("beforeTextChanged", "--------new-----------------------");
//                Log.e("beforeTextChanged", "s:" + s + " start:" + start + " count:"
//                        + count + " after:" + after);
            }

            /**
             * text改变后的内容
             * @param s 之后的内容
             * @param start 最后一个空格后第一个字符位置
             * @param before    输入的字体数
             * @param count     新增的数目
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e("onTextChanged", "-----------------------");
//                Log.e("onTextChanged", "s:" + s + " start:" + start + " before:"
//                        + before + " count:" + count);
            }

            /**
             * @param s 最终的内容
             */
            @Override
            public void afterTextChanged(Editable s) {
//                Log.e("afterTextChanged", "-----------------------");
//                Log.e("afterTextChanged", "s:" + s);
                if ((s.length()) <= 500) {
                    tip.setText(String.valueOf(s.length()));
                } else {
                    describe.setText(s.subSequence(0, 500));
                    describe.setSelection(500);
                }
            }
        });

        //完成按钮
        Button finish = (Button) findViewById(R.id.userresume_workexperience_add_describeactivity_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("describe", describe.getText().toString());
                setResult(getResources().getInteger(R.integer.USERRESUME_WORKEXPERIENCE_ADD_DESCRIBE_RESULTCODE), intent);
                finish();
            }
        });

        //返回
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_workexperience_add_describeactivity_back);
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
