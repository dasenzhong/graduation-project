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

public class CityFragment extends Fragment {

    private View view;

    private List<String> cityStringList;

    private int resId = 0;

    interface OnListItemClickListener {
        void onListItemClick(String cityName);
    }

    private OnListItemClickListener onListItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.module_activity_chooseplace_cityfragment, container, false);

            if (savedInstanceState != null) {
                resId = savedInstanceState.getInt("resId");
            } else {
                if (resId == 0) {
                    resId = R.array.parttimejob_unable;                     //初始化默认资源
                }
            }

//            cityStringList = Arrays.asList(getResources().getStringArray(resId));

            ListView cityList = (ListView) view.findViewById(R.id.chooseplaceactivity_cityfragment_list);
            cityList.setAdapter(cityListAdapter);
            cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onListItemClickListener != null) {
                        onListItemClickListener.onListItemClick(cityStringList.get(position));
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cityStringList = Arrays.asList(getResources().getStringArray(resId));
        cityListAdapter.notifyDataSetChanged();
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    /**
     * 城区适配器
     */
    private BaseAdapter cityListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (cityStringList == null ? 0 : cityStringList.size());
        }

        @Override
        public Object getItem(int position) {
            return cityStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CityViewHolder cityViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_activity_chooseplace_listitem, parent, false);
                cityViewHolder = new CityViewHolder();
                cityViewHolder.cityName = (TextView) convertView.findViewById(R.id.chooseplaceactivity_listitem_text);
                convertView.setTag(cityViewHolder);
            } else {
                cityViewHolder = (CityViewHolder) convertView.getTag();
            }

            cityViewHolder.cityName.setText(getItem(position).toString());

            return convertView;
        }
    };

    /**
     * 城区容器
     */
    private class CityViewHolder {
        private TextView cityName;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("resId", resId);
        super.onSaveInstanceState(outState);
    }
}
