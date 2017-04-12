package bishe.dgut.edu.cn.reallygoodapp.module.chooseplace;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/12.
 */

public class TownFragment extends Fragment {

    private View view;

    private List<String> townStringList;

//    private int clickPosition;                  //记录点击位置

    private int resId = 0;

    interface OnListItemClickListener {
        void onListItemClick(String townName);
    }

    private OnListItemClickListener onListItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.module_activity_chooseplace_townfragment, container, false);

            if (savedInstanceState != null) {
                resId = savedInstanceState.getInt("resId", R.array.parttimejob_all);
//                clickPosition = savedInstanceState.getInt("clickPosition", 0);
            } else {
                if (resId == 0) {
                    resId = R.array.parttimejob_unable;                         //初始化城镇资源
                }
            }

//            townStringList = Arrays.asList(getResources().getStringArray(resId));

            ListView townList = (ListView) view.findViewById(R.id.chooseplaceactivity_townfragment_list);
            townList.setAdapter(townListAdapter);
            townList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    clickPosition = position;
//                    townListAdapter.notifyDataSetChanged();
                    if (onListItemClickListener != null) {
                        onListItemClickListener.onListItemClick(townStringList.get(position));
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        townStringList = Arrays.asList(getResources().getStringArray(resId));
        townListAdapter.notifyDataSetChanged();
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

//    /**
//     * @return 选择的item
//     */
//    public String getSelectItem() {
//        return townStringList.get(clickPosition);
//    }

    /**
     * 城镇列表适配器
     */
    private BaseAdapter townListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (townStringList == null ? 0 : townStringList.size());
        }

        @Override
        public Object getItem(int position) {
            return townStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TownViewHolder townViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_activity_chooseplace_listitem, parent, false);
                townViewHolder = new TownViewHolder();
                townViewHolder.townName = (TextView) convertView.findViewById(R.id.chooseplaceactivity_listitem_text);
                convertView.setTag(townViewHolder);
            } else {
                townViewHolder = (TownViewHolder) convertView.getTag();
            }

            townViewHolder.townName.setText(getItem(position).toString());

//            if (position == clickPosition) {
//                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.gray_30));
//            } else {
//                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.white));
//            }

            return convertView;
        }
    };

    /**
     * 城镇列表容器
     */
    private class TownViewHolder {
        private TextView townName;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("resId", resId);
//        outState.putInt("clickPosition", clickPosition);
        super.onSaveInstanceState(outState);
    }
}
