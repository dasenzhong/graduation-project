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
 * Created by Administrator on 2017/3/9.
 */

public class NoticeListFragment extends Fragment {

    private View view;

    private List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试
        stringList = new ArrayList<>();
        for (int i = 0 ; i <10; i++) {
            stringList.add("你收到了一条通知");
        }


        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news_fragment_noticelist, null);
        }

        ListView noticeList = (ListView) view.findViewById(R.id.news_noticelist);
        noticeList.setAdapter(noticeListAdapter);

        return view;
    }

    private BaseAdapter noticeListAdapter = new BaseAdapter() {
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_fragment_noticelistitem, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.news_noticelist_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };
}
