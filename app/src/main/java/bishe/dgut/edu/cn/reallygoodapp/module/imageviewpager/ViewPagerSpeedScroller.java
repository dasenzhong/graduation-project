package bishe.dgut.edu.cn.reallygoodapp.module.imageviewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/16.
 */

public class ViewPagerSpeedScroller extends Scroller {

    private int vpDuration = 1000;

    public ViewPagerSpeedScroller(Context context) {
        super(context);
    }

    public ViewPagerSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setVpDuration(int duration) {
        this.vpDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, vpDuration);
    }
}
