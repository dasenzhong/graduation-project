package bishe.dgut.edu.cn.reallygoodapp.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;

/**
 * Created by Administrator on 2017/2/25.
 */

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener {

    private View homeview;

    private LinearLayout actionbar;         //actionbar
    private View listHead_advertisement;    //广告


    //测试例子
    private List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("测试，待服务器连接\n" + "这是放最新讯息的地方");
        }

        if (homeview == null) {
            homeview = inflater.inflate(R.layout.fragment_home, null);
            actionbar = (LinearLayout) homeview.findViewById(R.id.home_actionbar);
            actionbar.getBackground().setAlpha(0);

            //listhead的广告轮播位置
            listHead_advertisement = inflater.inflate(R.layout.fragment_home_listhead_advertisement, null);

            //listhead的导航栏配置
            View listHead_navigation = inflater.inflate(R.layout.fragment_home_listhead_navigation, null);

            //listview的配置
            ListView listView = (ListView) homeview.findViewById(R.id.home_listview);
            listView.addHeaderView(listHead_advertisement);
            listView.addHeaderView(listHead_navigation);
            listView.setAdapter(listViewAdapter);
            listView.setOnScrollListener(this);


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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        if (firstVisibleItem == 0) {
            int alpha = (int) ((((float) -listHead_advertisement.getTop()) / listHead_advertisement.getMeasuredHeight()) * 255);
            if (alpha > 200) {
                actionbar.getBackground().setAlpha(255);
            } else {
                actionbar.getBackground().setAlpha(alpha);
            }
        }

    }
}
