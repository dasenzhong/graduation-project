package bishe.dgut.edu.cn.reallygoodapp.module.imageviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/14.
 */

public class DotView extends View implements Checkable{

    private Context context;
    private Paint paint;
    private int dotUnSelectColor;
    private int dotSelectColor;

    private boolean ischeck;


    public DotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DotView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    //初始化
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        dotUnSelectColor = ContextCompat.getColor(context, R.color.white);      //设置未选择时的默认颜色
        dotSelectColor = ContextCompat.getColor(context, R.color.black_blue);   //设置选择时的默认颜色
        ischeck = false;                                                        //设置未选中

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);

        int width,height;

        if (widthmode == MeasureSpec.EXACTLY) {
            width = widthsize;
        } else {
            width = 13;
        }

        if (heightmode == MeasureSpec.EXACTLY) {
            height = heightsize;
        } else {
            height = 13;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (paint != null) {
            if (ischeck) {
                paint.setColor(dotSelectColor);
            } else {
                paint.setColor(dotUnSelectColor);
            }
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, paint);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        ischeck = checked;
//        if (ischeck) {
//            paint.setColor(dotSelectColor);
//        } else {
//            paint.setColor(dotUnSelectColor);
//        }

        invalidate();
    }

    @Override
    public boolean isChecked() {
        return ischeck;
    }

    @Override
    public void toggle() {
        setChecked(!ischeck);
    }

    public int getDotUnSelectColor() {
        return dotUnSelectColor;
    }

    public void setDotUnSelectColor(int dotUnSelectColor) {
        this.dotUnSelectColor = dotUnSelectColor;
    }

    public int getDotSelectColor() {
        return dotSelectColor;
    }

    public void setDotSelectColor(int dotSelectColor) {
        this.dotSelectColor = dotSelectColor;
    }
}
