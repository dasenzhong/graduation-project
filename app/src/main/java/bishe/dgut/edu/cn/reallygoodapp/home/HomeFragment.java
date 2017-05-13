package bishe.dgut.edu.cn.reallygoodapp.home;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.JobInfoActivity;
import bishe.dgut.edu.cn.reallygoodapp.LoginActivity;
import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.Job;
import bishe.dgut.edu.cn.reallygoodapp.bean.Page;
import bishe.dgut.edu.cn.reallygoodapp.home.experience.HomeExperienceActivity;
import bishe.dgut.edu.cn.reallygoodapp.home.organization.HomeOrganizationActivity;
import bishe.dgut.edu.cn.reallygoodapp.home.parttimejob.HomePartTimeJobActivity;
import bishe.dgut.edu.cn.reallygoodapp.home.recruit.HomeRecruitActivity;
import bishe.dgut.edu.cn.reallygoodapp.module.imageviewpager.ViewPagerForShowingImageFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/25.
 */

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener {

    private View homeview;
    private ImageView toTop;

    //    private LinearLayout actionbar;         //actionbar
    private Drawable actionbarDrawable;     //获取actionbar背景资源
    private View listHead_advertisement;    //广告

    private int page;

    private List<Job> newJobList;
    private List<String> hotJobStringList;
    private List<String> hotCompanyStringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
        bitmapsList.add(BitmapFactory.decodeResource(getResources(), R.drawable.testone));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(), R.drawable.testtwo));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(), R.drawable.testthree));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(), R.drawable.testfour));
        bitmapsList.add(BitmapFactory.decodeResource(getResources(), R.drawable.testfive));

        if (homeview == null) {
            homeview = inflater.inflate(R.layout.fragment_home, container, false);

            //actionbar
            LinearLayout actionbar = (LinearLayout) homeview.findViewById(R.id.home_actionbar);
            actionbarDrawable = actionbar.getBackground().mutate();
            actionbarDrawable.setAlpha(0);
            ImageView actionbar_user = (ImageView) homeview.findViewById(R.id.home_actionbar_user);
            actionbar_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });

            //listhead的广告轮播位置
            listHead_advertisement = inflater.inflate(R.layout.fragment_home_listhead_advertisement, null);
            ViewPagerForShowingImageFragment advertisement = (ViewPagerForShowingImageFragment) getChildFragmentManager().findFragmentById(R.id.home_listhead_advertisement);
            advertisement.setDotUnSelectColor(ContextCompat.getColor(getActivity(), R.color.orange));           //设置未选时的颜色
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
                    startActivity(new Intent(getActivity(), HomePartTimeJobActivity.class));
                }
            });

            //招聘会
            LinearLayout recruitLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_recruitlayout);
            recruitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), HomeRecruitActivity.class));
                }
            });

            //经验
            LinearLayout exprienceLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_expriencelayout);
            exprienceLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), HomeExperienceActivity.class));
                }
            });

            //社团
            LinearLayout organizationLayout = (LinearLayout) listHead_navigation.findViewById(R.id.home_listhead_navigation_organizationlayout);
            organizationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), HomeOrganizationActivity.class));
                }
            });

            //热门兼职
            ListView hotJobList = (ListView) listHead_navigation.findViewById(R.id.home_listhead_hot_joblist);
            hotJobList.setAdapter(hotJobListAdapter);
            getListViewHeight(hotJobList);                  //listview嵌套listview，要重新计算listview的高度,item子项父布局必须为linearlayout
            hotJobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(getActivity(), JobInfoActivity.class));
                }
            });

            //人气公司
            ListView hotCompanyList = (ListView) listHead_navigation.findViewById(R.id.home_listhead_hot_companylist);
            hotCompanyList.setAdapter(hotCompanyListAdapter);
            getListViewHeight(hotCompanyList);               //listview嵌套listview，要重新计算listview的高度

            View listFoot = inflater.inflate(R.layout.fragment_home_listfoot, null);

            //listview的配置
            final ListView listView = (ListView) homeview.findViewById(R.id.home_listview);
            listView.addHeaderView(listHead_advertisement);
            listView.addHeaderView(listHead_navigation);
            listView.addFooterView(listFoot);
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

            newJobList = new ArrayList<>();
            page = 0;
        }

        return homeview;
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoad();
    }

    private void reLoad() {

        Link.getClient().newCall(
                Link.getRequestAddress("/getnewjob/" + page).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("getnewjob--failure", e.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final Page<Job> jobPage = new ObjectMapper().readValue(response.body().string(), new TypeReference<Page<Job>>() {
                    });

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jobPage.getNumber() > page) {
                                if (newJobList == null) {
                                    newJobList = jobPage.getContent();
                                } else {
                                    newJobList.addAll(jobPage.getContent());
                                }
                                page = jobPage.getNumber() + 1;

                                listViewAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d("addnewjob--failure", e.getMessage());
                }
            }
        });

    }

    /**
     * 新的工作消息列表适配器
     */
    private BaseAdapter listViewAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return newJobList == null ? 0 : newJobList.size();
        }

        @Override
        public Object getItem(int position) {
            return newJobList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            NewJobListViewHolder newJobListViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_listitem, parent, false);
                newJobListViewHolder = new NewJobListViewHolder();
                newJobListViewHolder.jobName = (TextView) convertView.findViewById(R.id.home_listview_jobname);
                newJobListViewHolder.money = (TextView) convertView.findViewById(R.id.home_listview_money);
                newJobListViewHolder.company = (TextView) convertView.findViewById(R.id.home_listview_company);
                newJobListViewHolder.workPlace = (TextView) convertView.findViewById(R.id.home_listview_workplace);
                convertView.setTag(newJobListViewHolder);
            } else {
                newJobListViewHolder = (NewJobListViewHolder) convertView.getTag();
            }

            Job job = (Job) getItem(position);

            newJobListViewHolder.jobName.setText(job.getJobName());
            newJobListViewHolder.money.setText(job.getMoney());
            newJobListViewHolder.company.setText(job.getCompanyUser().getCompanyName());
            if (job.getWorkTown() != null) {
                newJobListViewHolder.workPlace.setText(job.getWorkTown());
            } else {
                if (job.getWorkCity() != null) {
                    newJobListViewHolder.workPlace.setText(job.getWorkCity());
                } else {
                    newJobListViewHolder.workPlace.setText(job.getWorkProvince());
                }
            }

            return convertView;
        }
    };

    /**
     * 新的工作列表容器
     */
    private class NewJobListViewHolder {
        private TextView jobName;
        private TextView money;
        private TextView company;
        private TextView workPlace;
    }

    /**
     * 热门兼职列表
     */
    private BaseAdapter hotJobListAdapter = new BaseAdapter() {
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

            TextView jobName = (TextView) convertView.findViewById(R.id.home_listhead_hot_joblist_jobname);
            TextView money = (TextView) convertView.findViewById(R.id.home_listhead_hot_joblist_money);

            return convertView;
        }
    };

    private BaseAdapter hotCompanyListAdapter = new BaseAdapter() {
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


    /**
     * 滚动监听
     *
     * @param view             监听的父容器
     * @param firstVisibleItem 第一个可见item项
     * @param visibleItemCount 可见item的总数
     * @param totalItemCount   全部item的数量
     */
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
            if (toTop != null && toTop.getVisibility() == View.VISIBLE) {
                toTop.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 重新计量listview的高度
     *
     * @param listView 要测量的listview
     */
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
