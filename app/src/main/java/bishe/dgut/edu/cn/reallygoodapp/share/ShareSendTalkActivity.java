package bishe.dgut.edu.cn.reallygoodapp.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.LoginActivity;
import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/16.
 */

public class ShareSendTalkActivity extends Activity {

    private EditText talkEdit;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_send);

        talkEdit = (EditText) findViewById(R.id.share_sendactivity_edit);


        //发送
        TextView send = (TextView) findViewById(R.id.share_sendactivity_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.share_sendactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedPreferences = this.getSharedPreferences("usertype", Context.MODE_PRIVATE);
    }

    private void sendToServer() {
        String talkGet = talkEdit.getText().toString();

        if (talkGet.isEmpty()) {
            Toast.makeText(this, "请输入随想内容", Toast.LENGTH_SHORT).show();
        } else {
            MultipartBody body = new MultipartBody.Builder()
                    .addFormDataPart("talkString", talkGet)
                    .build();

            //稍等进度条
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("请稍等");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            int checkFlag = sharedPreferences.getInt("usertype", -1);

            switch (checkFlag) {
                //学生
                case 0:
                    Link.getClient().newCall(
                            Link.getRequestAddress("/addtalkstudent").post(body).build()
                    ).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();

                                    new AlertDialog.Builder(ShareSendTalkActivity.this)
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();

                                    try {
                                        new AlertDialog.Builder(ShareSendTalkActivity.this)
                                                .setTitle("请求成功")
                                                .setMessage(responseString)
                                                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                }).show();
                                    } catch (Exception e) {
                                        new AlertDialog.Builder(ShareSendTalkActivity.this)
                                                .setMessage(e.getMessage())
                                                .setTitle("回应失败")
                                                .setNegativeButton("好", null).show();
                                    }
                                }
                            });
                        }
                    });
                    break;

                //公司
                case 1:
                    Link.getClient().newCall(
                            Link.getRequestAddress("/addtalkcompany").post(body).build()
                    ).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();

                                    new AlertDialog.Builder(ShareSendTalkActivity.this)
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();

                                    try {
                                        new AlertDialog.Builder(ShareSendTalkActivity.this)
                                                .setTitle("请求成功")
                                                .setMessage(responseString)
                                                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                }).show();
                                    } catch (Exception e) {
                                        new AlertDialog.Builder(ShareSendTalkActivity.this)
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
                    Toast.makeText(this, "登录已过期", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ShareSendTalkActivity.this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    }
}
