package bishe.dgut.edu.cn.reallygoodapp.module.imageviewpager;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/12.
 */

public class ViewPagerForShowingImageFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private View view;
    private ViewPager viewPager;
    private LinearLayout dotLayout;

    private List<Bitmap> bitmapList;        //图片列表
    private List<View> viewList;            //viewpager列表
    private List<DotView> dotViewList;      //指示器列表

    private int bitmapNumber;
    private int viewListSize;
    private int currentposition = 1;            //viewpager坐标
    private int dotposition = 0;                //dotview坐标

    private int dotUnSelectColor;
    private int dotSelectColor;

//    private ScheduledExecutorService autoService;
    private boolean isShowDotlayout;            //是否显示指示器
    private boolean isAuto;                     //是否自动播放
    private int autoTime;                       //自动播放间隔时间
    private boolean isHeadle;                   //是否手播
    private int VpDuration;                     //滚动速度，时间越长，速度越慢

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.module_fragment_viewpagerforshowingimage, container, false);
        }

        viewPager = (ViewPager) view.findViewById(R.id.module_fragment_showImage_viewpager);
        dotLayout = (LinearLayout) view.findViewById(R.id.module_fragment_showImage_dotlinearlayout);

        bitmapNumber = 5;
        viewListSize = bitmapNumber + 2;
        dotSelectColor = 0;
        dotUnSelectColor = 0;


        isShowDotlayout = true;
        isAuto = false;
        isHeadle = false;

        return view;
    }

    //初始化配置
    public void init() {
        dotLayoutAddDotView();          //设置指示器
        viewPagerAddView();

        if (isShowDotlayout) {
            dotLayout.setVisibility(View.VISIBLE);
        } else {
            dotLayout.setVisibility(View.GONE);
        }

        viewPager.setCurrentItem(currentposition);
        dotViewList.get(dotposition).setChecked(true);

        if (isAuto) {
            AutoService();
        }
    }

    //轮播服务
    private void AutoService() {
        ScheduledExecutorService autoService = Executors.newSingleThreadScheduledExecutor();        //创建一个系统的单线程池
        autoService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                synchronized (viewPager) {
                    viewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isHeadle) {
                                currentposition = (currentposition % (bitmapNumber + 1)) + 1;
                                viewPager.setCurrentItem(currentposition);
                            }
                        }
                    });
                }
            }
        }, autoTime, autoTime, TimeUnit.MILLISECONDS);

        //图片轮播速度
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerSpeedScroller viewPagerSpeedScroller = new ViewPagerSpeedScroller(getActivity(), new DecelerateInterpolator());
            viewPagerSpeedScroller.setVpDuration(VpDuration);
            mScroller.set(viewPager,viewPagerSpeedScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    //为viewpager添加view
    private void viewPagerAddView() {
        if (viewList == null) {
            viewList = new ArrayList<>();
        }

        if (bitmapNumber > 0) {
            //能提供图片时
            if (bitmapList != null) {
                for (int i = 0; i < bitmapNumber; i++) {
                    ImageView advertisement = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.module_fragment_viewpagerforshowingimage_layout_image, null);
                    if (bitmapList.get(i) != null) {
                        advertisement.setImageBitmap(bitmapList.get(i));
                    } else {
                        advertisement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.failure_gray));
                    }
                    viewList.add(advertisement);

                    //实现无限循环的另外两个bitmap
                    if (i == bitmapNumber - 1) {
                        ImageView advertisementhead = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.module_fragment_viewpagerforshowingimage_layout_image, null);
                        ImageView advertisementfoot = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.module_fragment_viewpagerforshowingimage_layout_image, null);

                        //无限循环表头
                        if (bitmapList.get(bitmapNumber - 1) != null) {
                            advertisementhead.setImageBitmap(bitmapList.get(bitmapNumber - 1));
                        } else {
                            advertisementhead.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.failure_gray));
                        }

                        //无限循环表尾
                        if (bitmapList.get(0) != null) {
                            advertisementfoot.setImageBitmap(bitmapList.get(0));
                        } else {
                            advertisementfoot.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.failure_gray));
                        }

                        viewList.add(0, advertisementhead);
                        viewList.add(advertisementfoot);
                    }
                }
            } else {
                //没有图片提供时
                for (int i = 0; i < bitmapNumber + 2; i++) {
                    ImageView advertisement = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.module_fragment_viewpagerforshowingimage_layout_image, null);
                    advertisement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.failure_gray));
                    viewList.add(advertisement);
                }
            }
        }

        viewListSize = viewList.size();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    //为指示器添加view
    private void dotLayoutAddDotView() {

        if (dotViewList == null) {
            dotViewList = new ArrayList<>();
        }

        if (bitmapNumber > 0) {
            //设置指示器数目
            if (!dotViewList.isEmpty()) {
                dotViewList.clear();            //已经设置时清空
            }
            for (int i = 0; i < bitmapNumber; i++) {
                DotView dotView = new DotView(getActivity());
                if (dotSelectColor != 0) {
                    dotView.setDotSelectColor(dotSelectColor);          //设置选择时的颜色
                }
                if (dotUnSelectColor != 0) {
                    dotView.setDotUnSelectColor(dotUnSelectColor);      //设置为选择时的颜色
                }
                dotViewList.add(dotView);
            }

            //设置指示器
            if (dotLayout.getVisibility() == View.VISIBLE) {
                dotLayout.removeAllViews();     //清空
                for (DotView dotView :
                        dotViewList) {
                    dotLayout.addView(dotView);
                }
            }
        }

    }

    private PagerAdapter viewPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == viewList.get((int) object));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
//            dotViewList.get(position).setChecked(true);           //viewpager会预加载前后一幅bitmap
            return position;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
//            dotViewList.get(position).setChecked(false);
        }
    };

    public void setDotUnSelectColor(int dotUnSelectColor) {
        this.dotUnSelectColor = dotUnSelectColor;
    }


    public void setDotSelectColor(int dotSelectColor) {
        this.dotSelectColor = dotSelectColor;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
        this.bitmapNumber = bitmapList.size();
    }

    public void setShowDot(boolean isShowDotlayout) {
        this.isShowDotlayout = isShowDotlayout;
    }

    public void setAuto(boolean isAuto, int autoTime, int vpDuration) {
        this.isAuto = isAuto;
        this.autoTime = autoTime;
        this.VpDuration = vpDuration;
    }

    //正在滚动时的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //滚动完毕
    @Override
    public void onPageSelected(int position) {
        int preDotPosition = dotposition;
        currentposition = position;
        if (position == 0 || position == viewListSize - 2) {
            dotposition = dotViewList.size() - 1;
        } else if (position == 1 || position == viewListSize - 1) {
            dotposition = 0;
        } else {
            dotposition = position - 1;
        }
        dotViewList.get(preDotPosition).setChecked(false);
        dotViewList.get(dotposition).setChecked(true);
//        Log.d("dotposition-------", "" + dotposition);
//        Log.d("viewposition---------", "" + currentposition);
    }

    //状态改变时
    @Override
    public void onPageScrollStateChanged(int state) {

        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:           //静止时
                if (currentposition == 0) {
                    viewPager.setCurrentItem(viewListSize - 2, false);
                }
                if (currentposition == viewListSize - 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:       //正在滚动时
                isHeadle = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:       //滚动刚结束时
                isHeadle = false;
                break;
        }
    }
}
