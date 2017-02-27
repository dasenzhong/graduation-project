package bishe.dgut.edu.cn.reallygoodapp.share;

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
 * Created by Administrator on 2017/2/25.
 */

public class ShareFragment extends Fragment {

    private View shareview;

    private List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        stringList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++) {
            stringList.add("你收到了一条来自别人的推送");
        }

        if (shareview == null) {
            shareview = inflater.inflate(R.layout.fragment_share, null);

            ListView shareList = (ListView) shareview.findViewById(R.id.share_list);
            shareList.setAdapter(shareListAdapter);
        }

        return shareview;
    }

    private BaseAdapter shareListAdapter = new BaseAdapter() {
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share_listitem, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.share_listitem_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };
}
