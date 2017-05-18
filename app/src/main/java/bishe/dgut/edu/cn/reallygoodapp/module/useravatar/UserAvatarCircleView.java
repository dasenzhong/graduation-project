package bishe.dgut.edu.cn.reallygoodapp.module.useravatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.CompanyUser;
import bishe.dgut.edu.cn.reallygoodapp.bean.StudentUser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/18.
 */

public class UserAvatarCircleView extends View {

    public UserAvatarCircleView(Context context) {
        super(context);
    }

    public UserAvatarCircleView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    public UserAvatarCircleView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context,attributeSet,defStyleAttr);
    }

    Paint paint;
    float srcWidth,srcHeight;
    Handler mainThreadHandler = new Handler();

    public void setBitmap(Bitmap bmp) {
        if (bmp == null)  {
            paint = new Paint();
//            paint.setColor(Color.GRAY);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(1);
//            //5长度直线，10长度空白，15长度支线，20长度空白
//            paint.setPathEffect(new DashPathEffect(new float[]{5, 10, 15, 20}, 0));

            paint.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.failure_gray), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            //抗锯齿
            paint.setAntiAlias(true);
        }else {
            paint = new Paint();
            paint.setShader(new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            paint.setAntiAlias(true);

            srcHeight = bmp.getHeight();
            srcWidth = bmp.getWidth();
        }

//        paint = new Paint();
//        //设置笔刷
//        paint.setShader(new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
//        radius = Math.min(bmp.getWidth(), bmp.getHeight()) / 2;
        invalidate();
    }

    public void loadCompany(CompanyUser companyUser) {
        //getAvatar得到的是字符串，服务器保存的地址
        load(companyUser.getAvatar());
    }

    public void loadStudent(StudentUser studentUser) {
        load(studentUser.getAvatar());
    }

    public void load(String url) {
        Link.getClient().newCall(Link.getAvatarAddress(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setBitmap(null);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    byte[] bytes = response.body().bytes();
                    final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setBitmap(bmp);
                        }
                    });
                } catch (Exception ex) {
                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setBitmap(null);
                        }
                    });
                }
            }
        });
    }

    //画圆形头像框,调用AvatarView是自动加载draw函数
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (paint != null) {
//            canvas.drawCircle(getWidth() / 2,getHeight() / 2,radius,paint);
            canvas.save();

            float dstWidth = getWidth();
            float dstHeight = getHeight();

            //计算缩放比例
            float scaleX = srcWidth / dstWidth;
            float scaleY = srcHeight / dstHeight;

            //设置缩放
            canvas.scale(1/scaleX,1/scaleY);

            //画圆，第1，2个参数设置圆心，第三个为半径，第四个为采用的画笔
            canvas.drawCircle(srcWidth/2,srcHeight/2,Math.min(srcWidth,srcHeight),paint);

            canvas.restore();
        }
    }

}
