package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/9.
 */

public class EmployPersonJobInfoEducationFragment extends Fragment {

    private View view;

    private List<String> educationStringList;
    private int clickPosition;                      //记录选择的位置

    interface OnLayoutClickListener {
        void onLayoutClick();
    }

    interface OnOkClickListener {
        void onOkClick(String educationText);
    }

    private OnLayoutClickListener onLayoutClickListener;
    private OnOkClickListener onOkClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_info_educationchoose, container, false);

            if (savedInstanceState != null) {
                clickPosition = savedInstanceState.getInt("clickPosition", 0);
            } else {
                clickPosition = 0;
            }

            educationStringList = Arrays.asList(getResources().getStringArray(R.array.educationlist));


            //空白地方点击事件
            FrameLayout layout = (FrameLayout) view.findViewById(R.id.employperson_educationfragment_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick();
                    }
                }
            });

            //listView
            final ListView educationList = (ListView) view.findViewById(R.id.employperson_educationfragment_list);
            educationList.setAdapter(educationListAdapter);
            educationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickPosition = position;
                    educationListAdapter.notifyDataSetChanged();
                }
            });

            //取消按钮
            Button cancel = (Button) view.findViewById(R.id.employperson_educationfragment_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick();
                    }
                }
            });

            //确定按钮
            Button ok = (Button) view.findViewById(R.id.employperson_educationfragment_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOkClickListener != null) {
                        onOkClickListener.onOkClick(educationStringList.get(clickPosition));
                    }
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick();
                    }
                }
            });
        }

        return view;
    }

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener) {
        this.onLayoutClickListener = onLayoutClickListener;
    }

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    /**
     * listView适配器
     */
    private BaseAdapter educationListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return educationStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return educationStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            EducationViewHolder educationViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_employperson_info_chooselist_listitem, parent, false);
                educationViewHolder = new EducationViewHolder();
                educationViewHolder.textView = (TextView) convertView.findViewById(R.id.employperson_choose_text);
                convertView.setTag(educationViewHolder);
            } else {
                educationViewHolder = (EducationViewHolder) convertView.getTag();
            }

            educationViewHolder.textView.setText(getItem(position).toString());

            if (position == clickPosition) {
                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.gray_30));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.gray_10));
            }

            return convertView;
        }
    };

    /**
     * listView容器
     */
    private class EducationViewHolder {
        private TextView textView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("clickPosition", clickPosition);
        super.onSaveInstanceState(outState);
    }
}
