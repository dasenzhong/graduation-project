package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.api.MD5;
import bishe.dgut.edu.cn.reallygoodapp.module.chooseplace.ChoosePlaceActivity;
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

public class RegisterCompanyActivity extends Activity {

    private EditText account;
    private EditText password;
    private EditText passwordRepeat;
    private EditText companyName;

    private TextView area;

    private String provinceGet;
    private String cityGet;
    private String townGet;

    private Spinner companyType;
    private Spinner companyNumber;
    private Spinner companyIndustry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);

        //账号
        account = (EditText) findViewById(R.id.register_company_account);
        final ImageView accountClear = (ImageView) findViewById(R.id.register_company_accountclear);
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
        password = (EditText) findViewById(R.id.register_company_password);
        final ImageView passwordClear = (ImageView) findViewById(R.id.register_company_passwordclear);
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
                    passwordClear.setVisibility(View.VISIBLE);
                } else {
                    passwordClear.setVisibility(View.GONE);
                }
            }
        });

        //可视
        ImageView visibility = (ImageView) findViewById(R.id.register_company_visibility);
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

        //重复密码
        passwordRepeat = (EditText) findViewById(R.id.register_company_passwordrepeat);
        final ImageView passwordRepeatClear = (ImageView) findViewById(R.id.register_company_passwordrepeatclear);
        passwordRepeatClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordRepeat.setText("");
            }
        });
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

        //重复可视
        ImageView repeatVisibility = (ImageView) findViewById(R.id.register_company_repeatvisibility);
        repeatVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    passwordRepeat.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordRepeat.setSelection(password.length());
                    v.setSelected(false);
                } else {
                    passwordRepeat.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordRepeat.setSelection(password.length());
                    v.setSelected(true);
                }
            }
        });

        //地区
        area = (TextView) findViewById(R.id.register_company_area);
        RelativeLayout areaLayout = (RelativeLayout) findViewById(R.id.register_company_arealayout);
        areaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RegisterCompanyActivity.this, ChoosePlaceActivity.class), getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_REGISTER));
            }
        });

        //公司名
        companyName = (EditText) findViewById(R.id.register_company_name);
        final ImageView companyNameClear = (ImageView) findViewById(R.id.register_company_nameclear);
        companyNameClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyName.setText("");
            }
        });
        companyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    companyNameClear.setVisibility(View.VISIBLE);
                } else {
                    companyNameClear.setVisibility(View.GONE);
                }
            }
        });

        companyType = (Spinner) findViewById(R.id.register_company_companytype);
        companyNumber = (Spinner) findViewById(R.id.register_company_companynumber);
        companyIndustry = (Spinner) findViewById(R.id.register_company_companyindustry);

        //提交
        TextView ok = (TextView) findViewById(R.id.register_company_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.register_company_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void submit() {
        String passwordGet = password.getText().toString();
        String passwordRepeatGet = passwordRepeat.getText().toString();
        String companyNameGet = companyName.getText().toString();
        String accountGet = account.getText().toString();

        if (accountGet.isEmpty()) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
        }else if (passwordGet.isEmpty() || passwordRepeatGet.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }else if (!passwordGet.equals(passwordRepeatGet)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        } else if (companyNameGet.isEmpty()) {
            Toast.makeText(this, "请输入公司名称", Toast.LENGTH_SHORT).show();
        } else if (provinceGet == null || provinceGet.isEmpty()) {
            Toast.makeText(this, "请选择公司所属地", Toast.LENGTH_SHORT).show();
        } else {
            passwordGet = MD5.getMD5(passwordGet);
            if (passwordGet == null) {
                Toast.makeText(this, "系统出错", Toast.LENGTH_SHORT).show();
            } else {
                MultipartBody body = new MultipartBody.Builder()
                        .addFormDataPart("account", accountGet)
                        .addFormDataPart("password", passwordGet)
                        .addFormDataPart("province", provinceGet)
                        .addFormDataPart("city", cityGet)
                        .addFormDataPart("town", townGet)
                        .addFormDataPart("companyName", companyNameGet)
                        .addFormDataPart("comapnyType", companyType.getSelectedItem().toString())
                        .addFormDataPart("companyNumber", companyNumber.getSelectedItem().toString())
                        .addFormDataPart("companyIndustry", companyIndustry.getSelectedItem().toString())
                        .build();

                //稍等进度条
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("请稍等");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Link.getClient().newCall(
                        Link.getRequestAddress("/registercompany").post(body).build()
                ).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();

                                new AlertDialog.Builder(RegisterCompanyActivity.this)
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
                                    new AlertDialog.Builder(RegisterCompanyActivity.this)
                                            .setTitle("请求成功")
                                            .setMessage(responseString)
                                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            }).show();
                                } catch (Exception e) {
                                    new AlertDialog.Builder(RegisterCompanyActivity.this)
                                            .setMessage(e.getMessage())
                                            .setTitle("请求失败")
                                            .setNegativeButton("好", null).show();
                                }
                            }
                        });
                    }
                });

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getResources().getInteger(R.integer.CHOOSEPLACE_RESULTCODE)) {
            if (requestCode == getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_REGISTER)) {
                provinceGet = data.getStringExtra("province");
                cityGet = data.getStringExtra("city");
                townGet = data.getStringExtra("town");
                if (cityGet != null) {
                    if (townGet != null) {
                        area.setText(data.getStringExtra("province") + " " + data.getStringExtra("city") + " " + data.getStringExtra("town"));
                    } else {
                        area.setText(data.getStringExtra("province") + " " + data.getStringExtra("city"));
                    }
                } else {
                    area.setText(data.getStringExtra("province"));
                }
            }
        }
    }
}
