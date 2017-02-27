package bishe.dgut.edu.cn.reallygoodapp.news;

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

/**
 * Created by Administrator on 2017/2/27.
 */

public class NewsListFragment extends Fragment {

    private View view;

    private List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        stringList = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++) {
            stringList.add("你收到一条信息");
        }

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news_fragment_newslist, null);

            ListView newsList = (ListView) view.findViewById(R.id.news_newslist);
            newsList.setAdapter(newsListAdapter);
        }

        return view;
    }

    private BaseAdapter newsListAdapter = new BaseAdapter() {
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_fragment_newslistitem, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.news_newslist_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };
}
