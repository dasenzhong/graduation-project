package bishe.dgut.edu.cn.reallygoodapp.module.applyclickview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/20.
 */

public class ApplyClickView extends View implements Checkable {

    private Context context;
    private boolean isCkeck;
    private Paint paint;
    private Bitmap bmp;
    private float bmpWidth;
    private float bmpHeight;

    public ApplyClickView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ApplyClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ApplyClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * 初始化画笔
     */
    private void init() {
        isCkeck = false;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.check);
        bmpWidth = (float) bmp.getWidth();
        bmpHeight = (float) bmp.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float viewWidth = getWidth();
        float viewHeight = getHeight();

        if (isCkeck) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.save();
            canvas.scale(viewWidth / bmpWidth, viewHeight / bmpHeight);

            canvas.drawCircle(bmpWidth / 2, bmpHeight / 2, Math.min(bmpWidth, bmpHeight) / 2, paint);

            canvas.restore();
        } else {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(ContextCompat.getColor(context, R.color.gray_70));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, paint);
        }

    }

    @Override
    public void setChecked(boolean checked) {
        this.isCkeck = checked;
        invalidate();
    }

    @Override
    public boolean isChecked() {
        return isCkeck;
    }

    @Override
    public void toggle() {
        setChecked(!isCkeck);
    }
}
