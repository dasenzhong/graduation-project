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

public class EmployPersonJobInfoPersonNumberFragment extends Fragment {

    private View view;
    private List<String> personNumberStringList;

    private int clickPosition;                  //记录选择的位置

    interface OnLayoutClickListener {
        void onLayoutClick();
    }

    interface OnOkClickListener {
        void onOkClick(String personNumberText);
    }

    private OnLayoutClickListener onLayoutClickListener;
    private OnOkClickListener onOkClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_info_personnumberchoose, container, false);

            if (savedInstanceState != null) {
                clickPosition = savedInstanceState.getInt("clickPosition", 0);
            } else {
                clickPosition = 0;
            }

            personNumberStringList = Arrays.asList(getResources().getStringArray(R.array.personnumberlist));

            //空白地方
            FrameLayout layout = (FrameLayout) view.findViewById(R.id.employperson_personnumberfragment_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick();
                    }
                }
            });

            //ListView
            ListView personNumberList = (ListView) view.findViewById(R.id.employperson_personnumberfragment_list);
            personNumberList.setAdapter(personNumberListAdapter);
            personNumberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickPosition = position;
                    personNumberListAdapter.notifyDataSetChanged();
                }
            });

            //取消按钮
            Button cancel = (Button) view.findViewById(R.id.employperson_personnumberfragment_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick();
                    }
                }
            });

            //确定按钮
            Button ok = (Button) view.findViewById(R.id.employperson_personnumberfragment_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOkClickListener != null) {
                        onOkClickListener.onOkClick(personNumberStringList.get(clickPosition));
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

    public void setOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    /**
     * personNumberList适配器
     */
    private BaseAdapter personNumberListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return personNumberStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return personNumberStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            PersonNumberViewHolder personNumberViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_employperson_info_chooselist_listitem, parent, false);
                personNumberViewHolder = new PersonNumberViewHolder();
                personNumberViewHolder.textView = (TextView) convertView.findViewById(R.id.employperson_choose_text);
                convertView.setTag(personNumberViewHolder);
            } else {
                personNumberViewHolder = (PersonNumberViewHolder) convertView.getTag();
            }

            personNumberViewHolder.textView.setText(getItem(position).toString());      //设置text

            if (position == clickPosition) {
                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.gray_30));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.gray_10));
            }

            return convertView;
        }
    };

    /**
     * ListView容器
     */
    private final class PersonNumberViewHolder {
        private TextView textView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("clickPosition", clickPosition);
        super.onSaveInstanceState(outState);
    }
}
