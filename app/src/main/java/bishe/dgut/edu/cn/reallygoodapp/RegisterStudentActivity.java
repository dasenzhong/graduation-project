package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.api.MD5;
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

public class RegisterStudentActivity extends Activity {

    private String sex;
    private EditText account;           //账号
    private EditText password;          //密码
    private EditText passwordRepeat;    //密码重复

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        //账号
        account = (EditText) findViewById(R.id.register_student_account);
        final ImageView accountClear = (ImageView) findViewById(R.id.register_student_accountclear);
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

        //密码
        password = (EditText) findViewById(R.id.register_student_password);
        final ImageView passwordClear = (ImageView) findViewById(R.id.register_student_passwordclear);
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
                    passwordClear.setVisibility(View.VISIBLE);
                } else {
                    passwordClear.setVisibility(View.GONE);
                }
            }
        });
        passwordClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("");
            }
        });

        //密码重复
        passwordRepeat = (EditText) findViewById(R.id.register_student_passwordrepeat);
        final ImageView passwordRepeatClear = (ImageView) findViewById(R.id.register_student_passwordrepeatclear);
        passwordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    passwordRepeatClear.setVisibility(View.VISIBLE);
                } else {
                    passwordRepeatClear.setVisibility(View.GONE);
                }
            }
        });
        passwordRepeatClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordRepeat.setText("");
            }
        });

        //可视
        ImageView visibility = (ImageView) findViewById(R.id.register_student_visibility);
        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                    v.setSelected(false);
                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password.setSelection(password.length());
                    v.setSelected(true);
                }
            }
        });

        //重复可视
        ImageView repeatVisibility = (ImageView) findViewById(R.id.register_student_repeatvisibility);
        repeatVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    passwordRepeat.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordRepeat.setSelection(passwordRepeat.length());
                    v.setSelected(false);
                } else {
                    passwordRepeat.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordRepeat.setSelection(passwordRepeat.length());
                    v.setSelected(true);
                }
            }
        });

        //男性
        final ImageView maleImage = (ImageView) findViewById(R.id.register_student_maleimage);
        final TextView maleText = (TextView) findViewById(R.id.register_student_maletext);

        //女性
        final ImageView femaleImage = (ImageView) findViewById(R.id.register_student_femaleimage);
        final TextView femaleText = (TextView) findViewById(R.id.register_student_femaletext);

        //男性选择
        LinearLayout maleLayout = (LinearLayout) findViewById(R.id.register_student_malelayout);
        maleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToggle(maleImage, maleText, femaleImage, femaleText);
                sex = maleText.getText().toString();
            }
        });

        //女性选择
        LinearLayout femaleLayout = (LinearLayout) findViewById(R.id.register_student_femalelayout);
        femaleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToggle(femaleImage, femaleText, maleImage, maleText);
                sex = femaleText.getText().toString();
            }
        });

        //完成按钮
        TextView ok = (TextView) findViewById(R.id.register_student_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.register_student_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setToggle(ImageView selectImage, TextView selectText, ImageView unSelectImage, TextView unSelectText) {
        selectImage.setSelected(true);
        selectText.setSelected(true);
        unSelectImage.setSelected(false);
        unSelectText.setSelected(false);
    }

    private void submit() {
        String passwordGet = password.getText().toString();
        String passwordRepeatGet = passwordRepeat.getText().toString();
        String accountGet = account.getText().toString();

        if (accountGet.isEmpty()) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
        }else if (passwordGet.isEmpty() || passwordRepeatGet.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }else if (!passwordGet.equals(passwordRepeatGet)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        } else if (sex == null || sex.isEmpty()) {
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
        } else {
            passwordGet = MD5.getMD5(passwordGet);
            if (passwordGet == null) {
                Toast.makeText(this, "系统出错", Toast.LENGTH_SHORT).show();
            } else {
                MultipartBody body = new MultipartBody.Builder()
                        .addFormDataPart("account", accountGet)
                        .addFormDataPart("password", passwordGet)
                        .addFormDataPart("sex", sex)
                        .build();

                //稍等进度条
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("请稍等");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Link.getClient().newCall(
                        Link.getRequestAddress("/registerstudent").post(body).build()
                ).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();

                                new AlertDialog.Builder(RegisterStudentActivity.this)
                                        .setMessage(e.getMessage())
                                        .setTitle("请求失败")
                                        .setNegativeButton("好", null).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseString = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();

                                try {
                                    new AlertDialog.Builder(RegisterStudentActivity.this)
                                            .setTitle("请求成功")
                                            .setMessage(responseString)
                                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            }).show();
                                } catch (Exception e) {
                                    new AlertDialog.Builder(RegisterStudentActivity.this)
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
    }

}
