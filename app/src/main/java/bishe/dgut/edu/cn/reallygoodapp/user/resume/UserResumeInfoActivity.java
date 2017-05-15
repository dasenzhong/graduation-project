package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Calendar;

import bishe.dgut.edu.cn.reallygoodapp.LoginActivity;
import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.StudentUser;
import bishe.dgut.edu.cn.reallygoodapp.module.chooseplace.ChoosePlaceActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/26.
 */

public class UserResumeInfoActivity extends Activity {

    private TextView address;
    private EditText name;
    private TextView birthday;
    private EditText telephone;
    private EditText school;

    private String provinceGet;
    private String cityGet;
    private String townGet;

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
        name = (EditText) findViewById(R.id.userresume_infoactivity_name);
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideSoftInput(v);
                }
                return false;
            }
        });


        //出生年月
        birthday = (TextView) findViewById(R.id.userresume_infoactivity_birthday);
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
        telephone = (EditText) findViewById(R.id.userresume_infoactivity_telephone);
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
        school = (EditText) findViewById(R.id.userresume_infoactivity_school);
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

                sendToServer();//上传数据

            }
        });
    }

    private void sendToServer() {
        String nameGet = name.getText().toString();
        String birthdayGet = birthday.getText().toString();
        String telephoneGet = telephone.getText().toString();
        String schoolGet = school.getText().toString();

        if (nameGet.isEmpty()) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
        } else if (birthdayGet.isEmpty() || birthdayGet.equals(getResources().getString(R.string.user_resume_infoactivity_birthdayhint))) {
            Toast.makeText(this, "请选择出生年月", Toast.LENGTH_SHORT).show();
        } else if (telephoneGet.isEmpty()) {
            Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
        } else if (provinceGet == null || provinceGet.isEmpty()) {
            Toast.makeText(this, "请选择居住地", Toast.LENGTH_SHORT).show();
        }else if (schoolGet.isEmpty()) {
            Toast.makeText(this, "请输入学校名字", Toast.LENGTH_SHORT).show();
        }else {
            MultipartBody body = new MultipartBody.Builder()
                    .addFormDataPart("account", accountGet)
                    .addFormDataPart("password", passwordGet)
                    .build();

            //稍等进度条
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("请稍等");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Link.getClient().newCall(
                    Link.getRequestAddress("/loginstudent").post(body).build()
            ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            new AlertDialog.Builder(LoginActivity.this)
                                    .setMessage(e.getMessage())
                                    .setTitle("请求失败")
                                    .setNegativeButton("好", null).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    //从服务器接受到数据
                    final String responseString = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    //将数据存储在User类中
                    StudentUser user = mapper.readValue(responseString, StudentUser.class);

                    sharedPreferences = LoginActivity.this.getSharedPreferences("studentuser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("sex", user.getSex());
                    editor.putString("name", user.getName());
                    editor.putString("area", user.getArea());
                    editor.putString("school", user.getSchool());
                    editor.putString("log", user.getLog());
                    editor.putString("avatar", user.getAvatar());
                    editor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            try {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("请求成功")
                                        .setMessage(responseString)
                                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            } catch (Exception e) {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setMessage(e.getMessage())
                                        .setTitle("回应失败")
                                        .setNegativeButton("好", null).show();
                            }
                        }
                    });
                }
            });
        }
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
                provinceGet = data.getStringExtra("province");
                cityGet = data.getStringExtra("city");
                townGet = data.getStringExtra("town");
                if (cityGet != null) {
                    if (townGet != null) {
                        address.setText(provinceGet + " " + cityGet + " " + townGet);
                    } else {
                        address.setText(provinceGet + " " + cityGet);
                    }
                } else {
                    address.setText(provinceGet);
                }
            }
        }
    }
}
