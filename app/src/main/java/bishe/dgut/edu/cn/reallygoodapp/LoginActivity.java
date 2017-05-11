package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.api.MD5;
import bishe.dgut.edu.cn.reallygoodapp.bean.CompanyUser;
import bishe.dgut.edu.cn.reallygoodapp.bean.StudentUser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/5.
 */

public class LoginActivity extends Activity {

    private AlertDialog.Builder registerChoose;
    private EditText account;
    private EditText password;
    private RadioButton student;
    private RadioButton company;

    private int checkFlag;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //账号输入
        account = (EditText) findViewById(R.id.login_account);
        final ImageView accountClear = (ImageView) findViewById(R.id.login_accountclear);
        accountClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setText("");
            }
        });
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    accountClear.setVisibility(View.VISIBLE);
                } else {
                    accountClear.setVisibility(View.GONE);
                }
            }
        });

        //密码输入
        password = (EditText) findViewById(R.id.login_password);
        ImageView passwordClear = (ImageView) findViewById(R.id.login_passwordclear);
        passwordClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("");
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    password.setVisibility(View.VISIBLE);
                } else {
                    password.setVisibility(View.GONE);
                }
            }
        });

        //可视
        ImageView visibility = (ImageView) findViewById(R.id.login_visibility);
        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                    v.setSelected(false);
                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password.setSelection(password.length());
                    v.setSelected(true);
                }
            }
        });

        //选择
        student = (RadioButton) findViewById(R.id.login_student);
        company = (RadioButton) findViewById(R.id.login_company);
        student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkFlag = getResources().getInteger(R.integer.STUDENT);
                    company.setChecked(false);
//                    Toast.makeText(LoginActivity.this, "学生", Toast.LENGTH_SHORT).show();
                }
            }
        });
        company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkFlag = getResources().getInteger(R.integer.COMPANY);
                    student.setChecked(false);
//                    Toast.makeText(LoginActivity.this, "公司", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //登录
        Button signIn = (Button) findViewById(R.id.login_signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //注册按钮
        TextView register = (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void login() {
        String accountGet = account.getText().toString();
        String passwordGet = password.getText().toString();

        if (accountGet.isEmpty()) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if (passwordGet.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else if (!student.isChecked() || !company.isChecked()) {
            Toast.makeText(this, "请选择类型", Toast.LENGTH_SHORT).show();
        } else {

            passwordGet = MD5.getMD5(passwordGet);
            if (passwordGet == null) {
                Toast.makeText(this, "系统出错", Toast.LENGTH_SHORT).show();
            } else {
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

                //储存用户类型
                sharedPreferences = this.getSharedPreferences("usertype", Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("usertype", checkFlag).apply();

                switch (checkFlag) {
                    //学生登陆
                    case 0:
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
                        break;

                    //公司登陆
                    case 1:
                        Link.getClient().newCall(
                                Link.getRequestAddress("/logincompany").post(body).build()
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
                                CompanyUser user = mapper.readValue(responseString, CompanyUser.class);

                                sharedPreferences = LoginActivity.this.getSharedPreferences("companyuser", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("province", user.getProvince());
                                editor.putString("city", user.getCity());
                                editor.putString("town", user.getTown());
                                editor.putString("companyName", user.getCompanyName());
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
                        break;
                    default:
                        break;
                }

            }
        }
    }

    private void register() {
        String[] items = {
                "我是学生",
                "我是公司代表"
        };

        if (registerChoose == null) {
            registerChoose = new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("请选择注册类型")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    startActivity(new Intent(LoginActivity.this, RegisterStudentActivity.class));
                                    break;

                                case 1:
                                    startActivity(new Intent(LoginActivity.this, RegisterCompanyActivity.class));
                                    break;

                                default:
                                    break;

                            }
                        }
                    }).setNegativeButton("取消", null);
        }

        registerChoose.show();
    }
}
