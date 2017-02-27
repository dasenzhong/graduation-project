package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import bishe.dgut.edu.cn.reallygoodapp.home.HomeFragment;
import bishe.dgut.edu.cn.reallygoodapp.news.NewsFragment;
import bishe.dgut.edu.cn.reallygoodapp.share.ShareFragment;
import bishe.dgut.edu.cn.reallygoodapp.user.UserFragment;

/**
 * Created by Administrator on 2017/2/25.
 */

public class MainActivity extends Activity implements BottomNavigationBar.OnTabSelectedListener {

    private HomeFragment homeFragment;
    private ShareFragment shareFragment;
    private NewsFragment newsFragment;
    private UserFragment userFragment;

//    private BottomNavigationBar bar;
    private int selectPostion = 0;      //记录位置，被kill时使用
    private final String[] barName = {"home", "chat", "news", "user"};      //tag标识

//    private List<Fragment> barfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] barName = {"home", "chat", "news", "user"};


        if (savedInstanceState != null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            selectPostion = savedInstanceState.getInt("selectPostion");
            homeFragment = (HomeFragment) fm.findFragmentByTag(barName[0]);
            shareFragment = (ShareFragment) fm.findFragmentByTag(barName[1]);
            newsFragment = (NewsFragment) fm.findFragmentByTag(barName[2]);
            userFragment = (UserFragment) fm.findFragmentByTag(barName[3]);

            for (int i = 0 ; i < barName.length; i++) {
                onTabUnselected(i);
            }
        }
//        else {
//            homeFragment = new HomeFragment();
//            shareFragment = new ShareFragment();
//            newsFragment = new NewsFragment();
//            userFragment = new UserFragment();
//        }

//        barfragment = new ArrayList<>();
//        barfragment.add(new HomeFragment());
//        barfragment.add(new ShareFragment());
//        barfragment.add(new NewsFragment());
//        barfragment.add(new UserFragment());

        BottomNavigationBar bar = (BottomNavigationBar) findViewById(R.id.main_bar);
        bar.setMode(BottomNavigationBar.MODE_SHIFTING);                         //设置导航栏模式、2个模式，shifting为选择页面icon变大，另一种模式为选择时有波浪特效
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);    //设置导航栏风格、2个模式，static为icon颜色，另一种模式为背景颜色
        bar.setBarBackgroundColor(R.color.mainbar_background)
                .setInActiveColor(R.color.mainbar_inActive);                                   //shifting模式为不被选择时颜色,fixed模式时为选择时的颜色
        bar.addItem(new BottomNavigationItem(R.drawable.mainicon_home, "首页").setActiveColorResource(R.color.mainbar_home))
                .addItem(new BottomNavigationItem(R.drawable.mainicon_share, "社区").setActiveColorResource(R.color.mainbar_share))
                .addItem(new BottomNavigationItem(R.drawable.mainicon_news, "消息").setActiveColorResource(R.color.mainbar_news))
                .addItem(new BottomNavigationItem(R.drawable.mainicon_user, "我").setActiveColorResource(R.color.mainbar_user))
                .setFirstSelectedPosition(selectPostion)
                .initialise();

        bar.setTabSelectedListener(this);               //设置监听器
        onTabSelected(selectPostion);


//        for (int i = 0; i < barName.length; i++) {
//            getFragmentManager().beginTransaction().add(R.id.main_fragment, barfragment.get(i), barName[i]).commit();
//        }
    }

    @Override
    public void onTabSelected(int position) {       //选择时

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (position < barName.length) {
            selectPostion = position;
        } else {
            selectPostion = 0;
        }

        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.main_fragment, homeFragment, barName[0]);
                } else {
                    ft.show(homeFragment);
                }
//                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                break;
            case 1:
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    ft.add(R.id.main_fragment, shareFragment, barName[1]);
                } else {
                    ft.show(shareFragment);
                }
                break;
            case 2:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    ft.add(R.id.main_fragment, newsFragment, barName[2]);
                } else {
                    ft.show(newsFragment);
                }
                break;
            case 3:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                    ft.add(R.id.main_fragment, userFragment, barName[3]);
                } else {
                    ft.show(userFragment);
                }
//                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.gray_90));
                break;
            default:
                break;
        }
        ft.commit();


//        if (barfragment != null) {
//            if (position < barfragment.size()) {
//                Fragment fragment = barfragment.get(position);
//                if (fragment.isAdded()) {
//                    getFragmentManager().beginTransaction().show(fragment).commit();
//                } else {
//                    getFragmentManager().beginTransaction().add(R.id.main_fragment, fragment,barName[position]).commit();
//                }
//                selectpostion = position;
//            }
//        }
    }

    @Override
    public void onTabUnselected(int position) {     //未选择时
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentByTag(barName[position]);
        if (fragment == null) {
            return;
        } else {
            ft.hide(fragment).commit();
        }

//        if (barfragment != null) {
//            if (position < barfragment.size()) {
//                Fragment fragment = barfragment.get(position);
//                if (fragment.isAdded()) {
//                    getFragmentManager().beginTransaction().hide(fragment).commit();
//                } else {
//                    getFragmentManager().beginTransaction().add(R.id.main_fragment, fragment, barName[position]).hide(fragment).commit();
//                }
//            }
//        }
    }

    @Override
    public void onTabReselected(int position) {     //已选再选一次，通常用于刷新

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectPostion", selectPostion);
        super.onSaveInstanceState(outState);
    }
}
