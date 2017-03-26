package bishe.dgut.edu.cn.reallygoodapp.home;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;
import bishe.dgut.edu.cn.reallygoodapp.home.experience.HomeExperienceActivity;
import bishe.dgut.edu.cn.reallygoodapp.home.organization.HomeOrganizationActivity;
import bishe.dgut.edu.cn.reallygoodapp.home.parttimejob.HomePartTimeJobActivity;
import bishe.dgut.edu.cn.reallygoodapp.home.recruit.HomeRecruitActivity;
import bishe.dgut.edu.cn.reallygoodapp.module.imageviewpager.ViewPagerForShowingImageFragment;

/**
 * Created by Administrator on 2017/2/25.
 */

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener {

    private View homeview;
    private ImageView toTop;

//    private LinearLayout actionbar;         //actionbar
    private Drawable actionbarDrawable;     //获取actionbar背景资源
    private View listHead_advertisement;    //广告


    //测试例子
    private List<String> stringList;
    private List<String> hotJobStringList;
    private List<String> hotCompanyStringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("测试，待服务器连接\n" + "这是放最新讯息的地方");
        }

        hotJobStringList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            hotJobStringList.add("热门兼职");
        }

        hotCompanyStringList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            hotCompanyStringList.add("人气公司");
        }

        //轮播测试例子
        List<Bitmap> bitmapsList = new ArrayList<>();
        bitmapsList.add(BitmapFactory.decodeResource(getResources(),R.drawable.testone));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(),R.drawable.testtwo));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(),R.drawable.testthree));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(),R.drawable.testfour));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(),R.drawable.testfive));

        if (homeview == null) {
            homeview = inflater.inflate(R.layout.fragment_home, null);
            LinearLayout actionbar = (LinearLayout) homeview.findViewById(R.id.home_actionbar);
            actionbarDrawable = actionbar.getBackground().mutate();
            actionbarDrawable.setAlpha(0);

            //listhead的广告轮播位置
            listHead_advertisement = inflater.inflate(R.layout.fragment_home_listhead_advertisement, null);
            ViewPagerForShowingImageFragment advertisement = (ViewPagerForShowingImageFragment) getChildFragmentManager().findFragmentById(R.id.home_listhead_advertisement);
            advertisement.setDotUnSelectColor(ContextCompat.getColor(getActivity(),R.color.orange));           //设置未选时的颜色
//            advertisement.setShowDot(false);                              //是否显示指示器，默认为显示
            advertisement.setAuto(true, 5000, 1500);                        //第一个参数为是否开启轮播服务，第二个参数为轮播时间间隔，第三个参数为动画滑动速度
            advertisement.setBitmapList(bitmapsList);
            advertisement.init();


            //listhead的导航栏配置
            View listHead_navigation = inflater.inflate(R.layout.fragment_home_listhead_navigation, null);

            //兼职
            LinearLayout parttimejobLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_parttimejoblayout);
            parttimejobLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),HomePartTimeJobActivity.class));
                }
            });

            //招聘会
            LinearLayout recruitLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_recruitlayout);
            recruitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),HomeRecruitActivity.class));
                }
            });

            //经验
            LinearLayout exprienceLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_expriencelayout);
            exprienceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),HomeExperienceActivity.class));
                }
            });

            //社团
            LinearLayout organizationLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_organizationlayout);
            organizationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),HomeOrganizationActivity.class));
                }
            });

            //热门兼职
            ListView hotJobList = (ListView) listHead_navigation.findViewById(R.id.home_listhead_hot_joblist);
            hotJobList.setAdapter(hotJobListAdapter);
            getListViewHeight(hotJobList);                  //listview嵌套listview，要重新计算listview的高度,item子项父布局必须为linearlayout

            //人气公司
            ListView hotCompanyList = (ListView) listHead_navigation.findViewById(R.id.home_listhead_hot_companylist);
            hotCompanyList.setAdapter(hotCompanyListAdapter);
            getListViewHeight(hotCompanyList);               //listview嵌套listview，要重新计算listview的高度

            //listview的配置
            final ListView listView = (ListView) homeview.findViewById(R.id.home_listview);
            listView.addHeaderView(listHead_advertisement);
            listView.addHeaderView(listHead_navigation);
            listView.setAdapter(listViewAdapter);
            listView.setOnScrollListener(this);

            //回到顶部
            toTop = (ImageView) homeview.findViewById(R.id.home_totop);
            toTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listView.smoothScrollToPosition(0);
                    v.setVisibility(View.GONE);
                }
            });

            //沉浸式状态栏
            View view = homeview.findViewById(R.id.home_status);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = GetAppSize.statusBarHeight(getResources());
            view.setLayoutParams(params);
        }

        return homeview;
    }


    BaseAdapter listViewAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_listitem, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.home_listview_item);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };

    BaseAdapter hotJobListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return hotJobStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return hotJobStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_listhead_navigation_hot_joblist_item, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.home_listhead_hot_joblist_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };

    BaseAdapter hotCompanyListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return hotCompanyStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return hotCompanyStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_listhead_navigation_hot_companylist_item, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.home_listhead_hot_companylist_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        if (firstVisibleItem == 0) {
            int alpha = (int) ((((float) -listHead_advertisement.getTop()) / listHead_advertisement.getMeasuredHeight()) * 255);
            if (alpha > 200) {
                actionbarDrawable.setAlpha(255);
            } else {
                actionbarDrawable.setAlpha(alpha);
            }
        }

        if (firstVisibleItem > 2) {
            toTop.setVisibility(View.VISIBLE);
        } else {
            if (toTop !=null && toTop.getVisibility() == View.VISIBLE ) {
                toTop.setVisibility(View.GONE);
            }
        }

    }

    private void getListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();        //获取测量listview的适配器

        if (listAdapter == null) {
            return;
        }
        int listViewHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View view = listAdapter.getView(i, null, listView);
            view.measure(0, 0);
            listViewHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = listViewHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
