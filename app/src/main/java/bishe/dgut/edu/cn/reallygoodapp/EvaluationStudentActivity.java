package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;

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
 * Created on 2017/5/18.
 */

public class EvaluationStudentActivity extends Activity {

    private RatingBar score;
    private EditText text;

    private int companyuserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_student);

        if (getIntent() != null) {
            companyuserId = getIntent().getIntExtra("companyuserId", -1);
        }

        score = (RatingBar) findViewById(R.id.evaluation_student_starsBar);

        text = (EditText) findViewById(R.id.evaluation_student_edittext);

        //提交
        TextView submit = (TextView) findViewById(R.id.evaluation_student_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
            }
        });

        //返回
        ImageView back = (ImageView) findViewById(R.id.evaluation_student_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void sendToServer() {
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("companyuserId", String.valueOf(companyuserId))
                .addFormDataPart("score", String.valueOf(score.getRating()))
                .addFormDataPart("comment", text.getText().toString())
                .build();

        //稍等进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍等");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Link.getClient().newCall(
                Link.getRequestAddress("/addcommentcompany").post(body).build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        new AlertDialog.Builder(EvaluationStudentActivity.this)
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
                            new AlertDialog.Builder(EvaluationStudentActivity.this)
                                    .setTitle("请求成功")
                                    .setMessage(responseString)
                                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        } catch (Exception e) {
                            new AlertDialog.Builder(EvaluationStudentActivity.this)
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
