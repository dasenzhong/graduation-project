package bishe.dgut.edu.cn.reallygoodapp.user.info;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.module.avatarview.ChangeAvatarView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/22.
 */

public class AvatarActivity extends Activity {

    private AvatarMoreFragment avatarMoreFragment;

    private ChangeAvatarView changeAvatarView;

    private Bitmap bmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_avatar);

        if (savedInstanceState != null) {
            avatarMoreFragment = (AvatarMoreFragment) getFragmentManager().findFragmentByTag("more");
            if (avatarMoreFragment != null) {
                setMoreFragmentListener();
            }
        }

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userinfo_avatar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //图像显示
        changeAvatarView = (ChangeAvatarView) findViewById(R.id.userinfo_avatar_image);
        //测试图片
        changeAvatarView.setBmp(BitmapFactory.decodeResource(getResources(), R.drawable.test));

        //更多操作
        ImageView more = (ImageView) findViewById(R.id.userinfo_avatar_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreFragment();
            }
        });

    }

    /**
     * 显示更多fragment
     */
    private void showMoreFragment() {
        if (avatarMoreFragment == null) {
            avatarMoreFragment = new AvatarMoreFragment();
            setMoreFragmentListener();
        }

        if (avatarMoreFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(avatarMoreFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.userinfo_avatar_mainlayout, avatarMoreFragment, "more").commit();
        }
    }

    /**
     * 移除更多fragment
     */
    private void removeMoreFragment() {
        if (avatarMoreFragment.isAdded()) {
            getFragmentManager().beginTransaction().remove(avatarMoreFragment).commit();
        }
    }

    /**
     * 设置更多fragment的点击回调
     */
    private void setMoreFragmentListener() {
        avatarMoreFragment.setOnLayoutClickListener(new AvatarMoreFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick(int id) {
                switch (id) {
                    case R.id.userinfo_avatar_more_shotlayout:
                        shot();
                        break;
                    case R.id.userinfo_avatar_more_albumlayout:
                        album();
                        break;
                    case R.id.userinfo_avatar_more_savelayout:
                        save();
                        break;
                    case R.id.userinfo_avatar_more_cancellayout:
                        removeMoreFragment();
                        break;
                    case R.id.userinfo_avatar_more_mainlayout:
                        removeMoreFragment();
                        break;
                    default:
                        removeMoreFragment();
                }
            }
        });
    }

    /**
     * 保存照片
     */
    private void save() {
        File appDir = new File(Environment.getExternalStorageDirectory(), "ReallygoodApp");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory().toString() + "/ReallygoodApp")));
        Toast.makeText(this, "已保存图片", Toast.LENGTH_SHORT).show();
        removeMoreFragment();
    }

    /**
     * 从相册选择图片
     */
    private void album() {
//        Toast.makeText(this, "相册选择", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), getResources().getInteger(R.integer.AVATAR_REQUESTCODE_ABLUM));
    }

    /**
     * 拍摄
     */
    private void shot() {
//        Toast.makeText(this, "拍摄", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), getResources().getInteger(R.integer.AVATAR_REQUESTCODE_SHOT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != Activity.RESULT_CANCELED) {
            if (requestCode == getResources().getInteger(R.integer.AVATAR_REQUESTCODE_SHOT)) {
                if (data != null) {
                    bmp = (Bitmap) data.getExtras().get("data");
                    changeAvatarView.setBmp(bmp);
                }
                removeMoreFragment();
            }
            if (requestCode == getResources().getInteger(R.integer.AVATAR_REQUESTCODE_ABLUM)) {
//                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                if (data != null) {
                    bmp = BitmapFactory.decodeFile(convertUri(data.getData()).getPath());
                    changeAvatarView.setBmp(bmp);
                }
                removeMoreFragment();
            }
            uploadAvatar();
        }
    }

    private void uploadAvatar() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] pngData = baos.toByteArray();

        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("avatar", "avatar", RequestBody.create(MediaType.parse("image/png"), pngData))
                .build();

        //稍等进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍等");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        SharedPreferences sharedPreferences = this.getSharedPreferences("usertype", Context.MODE_PRIVATE);
        int loginType = sharedPreferences.getInt("usertype", -1);
        String linkAddress;

        switch (loginType) {
            case 0:
                linkAddress = "/saveavatarstudent";
                break;
            case 1:
                linkAddress = "/saveavatarcompany";
                break;
            default:
                linkAddress = "";
        }

        if (!linkAddress.isEmpty()) {
            Link.getClient().newCall(
                    Link.getRequestAddress(linkAddress).post(body).build()
            ).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            new AlertDialog.Builder(AvatarActivity.this)
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
                                new AlertDialog.Builder(AvatarActivity.this)
                                        .setTitle("请求成功")
                                        .setMessage(responseString)
                                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            } catch (Exception e) {
                                new AlertDialog.Builder(AvatarActivity.this)
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

    //计算路径名
    private Uri convertUri(Uri data) {
        if (data.toString().substring(0, 7).equals("content")) {
            String[] colName = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(data, colName, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                data = Uri.parse("file://" + cursor.getString(0));
                cursor.close();
            }
        }
        return data;
    }
}
