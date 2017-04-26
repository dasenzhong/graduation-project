package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.module.chooseplace.ChoosePlaceActivity;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/26.
 */

public class UserResumeInfoActivity extends Activity {

    private TextView address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume_info);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.userresume_infoactivity_mainlayout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
            }
        });

        //姓名输入框
        EditText name = (EditText) findViewById(R.id.userresume_infoactivity_name);
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput(v);
                }
                return false;
            }
        });

        //男性
        final ImageView maleImage = (ImageView) findViewById(R.id.userresume_infoactivity_sex_maleimage);
        final TextView maleText = (TextView) findViewById(R.id.userresume_infoactivity_sex_maletext);
        LinearLayout maleLayout = (LinearLayout) findViewById(R.id.userresume_infoactivity_sex_malelayout);
        //女性
        final ImageView femaleImage = (ImageView) findViewById(R.id.userresume_infoactivity_sex_femaleimage);
        final TextView femaleText = (TextView) findViewById(R.id.userresume_infoactivity_sex_femaletext);
        LinearLayout femaleLayout = (LinearLayout) findViewById(R.id.userresume_infoactivity_sex_femalelayout);
        //男性点击事件
        maleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                if (!maleImage.isSelected()) {
                    maleImage.setSelected(true);
                    maleText.setSelected(true);
                    femaleImage.setSelected(false);
                    femaleText.setSelected(false);
                }
            }
        });
        //女性点击事件
        femaleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                if (!femaleImage.isSelected()) {
                    femaleImage.setSelected(true);
                    femaleText.setSelected(true);
                    maleImage.setSelected(false);
                    maleText.setSelected(false);
                }
            }
        });

        //出生年月
        final TextView birthday = (TextView) findViewById(R.id.userresume_infoactivity_birthday);
        RelativeLayout birthdayLayout = (RelativeLayout) findViewById(R.id.userresume_infoactivity_birthdaylayout);
        birthdayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(UserResumeInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthday.setText(year + "-" + month + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //电话号码
        EditText telephone = (EditText) findViewById(R.id.userresume_infoactivity_telephone);
        telephone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput(v);
                }
                return false;
            }
        });

        //居住地
        address = (TextView) findViewById(R.id.userresume_infoactivity_address);
        RelativeLayout addressLayout = (RelativeLayout) findViewById(R.id.userresume_infoactivity_addresslayout);
        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UserResumeInfoActivity.this, ChoosePlaceActivity.class), getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_USERRESUME_INFOADDRESS));
            }
        });

        //学校
        EditText school = (EditText) findViewById(R.id.userresume_infoactivity_school);
        school.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput(v);
                }
                return false;
            }
        });

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_infoactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //保存键
        Button ok = (Button) findViewById(R.id.userresume_infoactivity_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //上传数据

            }
        });
    }

    private void hideSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getResources().getInteger(R.integer.CHOOSEPLACE_RESULTCODE)) {
            if (requestCode == getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_USERRESUME_INFOADDRESS)) {
                if (data.getStringExtra("city") != null) {
                    if (data.getStringExtra("town") != null) {
                        address.setText(data.getStringExtra("province") + " " + data.getStringExtra("city") + " " + data.getStringExtra("town"));
                    } else {
                        address.setText(data.getStringExtra("province") + " " + data.getStringExtra("city"));
                    }
                } else {
                    address.setText(data.getStringExtra("province"));
                }
            }
        }
    }
}
