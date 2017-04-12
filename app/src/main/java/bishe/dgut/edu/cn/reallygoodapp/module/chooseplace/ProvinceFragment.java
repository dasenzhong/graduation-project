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

public class ProvinceFragment extends Fragment {

    private View view;

    private List<String> provinceStringList;

    interface OnListItemClickListener {
        void onListItemClick(String provinceName);
    }

    private OnListItemClickListener onListItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.module_activity_chooseplace_provincefragment, container, false);

            provinceStringList = Arrays.asList(getResources().getStringArray(R.array.provincelist));

            ListView provinceList = (ListView) view.findViewById(R.id.chooseplaceactivity_provincefragment_list);
            provinceList.setAdapter(provinceListAdapter);
            provinceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onListItemClickListener != null) {
                        onListItemClickListener.onListItemClick(provinceStringList.get(position));
                    }
                }
            });
        }

        return view;
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    /**
     * 省份适配器
     */
    private BaseAdapter provinceListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (provinceStringList == null ? 0 : provinceStringList.size());
        }

        @Override
        public Object getItem(int position) {
            return provinceStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProvinceViewHolder provinceViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_activity_chooseplace_listitem, parent, false);
                provinceViewHolder = new ProvinceViewHolder();
                provinceViewHolder.provinceName = (TextView) convertView.findViewById(R.id.chooseplaceactivity_listitem_text);
                convertView.setTag(provinceViewHolder);
            } else {
                provinceViewHolder = (ProvinceViewHolder) convertView.getTag();
            }

            provinceViewHolder.provinceName.setText(getItem(position).toString());

            return convertView;
        }
    };

    /**
     * 省份容器
     */
    private class ProvinceViewHolder {
        private TextView provinceName;
    }

}
