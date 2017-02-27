package bishe.dgut.edu.cn.reallygoodapp.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;

/**
 * Created by Administrator on 2017/2/25.
 */

public class HomeFragment extends Fragment {

    private View homeview;
//    private View listhead;
//    private ListView listView;

    //测试例子
    private List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        stringList = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            stringList.add("测试，待服务器连接\n" + "这是放最新讯息的地方");
        }

        if (homeview == null) {
            homeview = inflater.inflate(R.layout.fragment_home, null);
            View listHead = inflater.inflate(R.layout.fragment_home_listhead, null);

            ListView listView = (ListView) homeview.findViewById(R.id.home_listview);
            listView.addHeaderView(listHead);
            listView.setAdapter(listViewAdapter);

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
            textView.setText("" + stringList.get(position) + position);

            return convertView;
        }
    };
}
