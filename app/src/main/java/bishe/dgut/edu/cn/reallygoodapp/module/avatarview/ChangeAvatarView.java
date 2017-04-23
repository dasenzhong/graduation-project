package bishe.dgut.edu.cn.reallygoodapp.module.avatarview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/23.
 */

public class ChangeAvatarView extends View {

    private Context context;
    private Paint paint;
    private Bitmap bmp;
    private int widthSize;
    private float bmpWidth, bmpHeight;

    public ChangeAvatarView(Context context) {
        super(context);
        this.context = context;
        paint = new Paint();
    }

    public ChangeAvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
    }

    public ChangeAvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//        heightSize = widthSize;
        setMeasuredDimension(widthSize, widthSize);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));

        canvas.save();

        canvas.scale(getWidth() / bmpWidth, getHeight() / bmpHeight);

        canvas.drawRect(0, 0, bmpWidth, bmpHeight, paint);

        canvas.restore();
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
        bmpWidth = bmp.getWidth();
        bmpHeight = bmp.getHeight();
        invalidate();
    }
}
