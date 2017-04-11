package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class EmployPresonJobInfoFragment extends Fragment {

    private View view;

    private EditText jobNameView;
    private EditText momeyView;
    private EditText describe;
    private TextView personNumberView;
    private TextView educationView;
    private TextView jobTypeView;

    interface OnShowChooseFragmentListener {
        void onShowChooseFragment(View view);
    }

    private OnShowChooseFragmentListener onShowChooseFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_info, container, false);

            //兼职名
            jobNameView = (EditText) view.findViewById(R.id.employperson_info_jobname);

            //兼职薪酬
            momeyView = (EditText) view.findViewById(R.id.employperson_info_momey);

            //兼职描述
            describe = (EditText) view.findViewById(R.id.employperson_info_describe);

            //兼职类型
            jobTypeView = (TextView) view.findViewById(R.id.employperson_info_jobtype);
            jobTypeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChooseFragment(v);
                }
            });

            //兼职招聘人数
            personNumberView = (TextView) view.findViewById(R.id.employperson_info_personnumber);
            personNumberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChooseFragment(v);
                }
            });

            //兼职要求学历
            educationView = (TextView) view.findViewById(R.id.employperson_info_education);
            educationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChooseFragment(v);
                }
            });

            if (savedInstanceState != null) {
                jobNameView.setText(savedInstanceState.getString("jobNameView"));
                jobTypeView.setText(savedInstanceState.getString("jobTypeView"));
                educationView.setText(savedInstanceState.getString("educationView"));
                personNumberView.setText(savedInstanceState.getString("personNumberView"));
                describe.setText(savedInstanceState.getString("describe"));
                momeyView.setText(savedInstanceState.getString("momeyView"));
            }

        }

        return view;
    }

    public void setOnShowChooseFragmentListener(OnShowChooseFragmentListener onShowChooseFragmentListener) {
        this.onShowChooseFragmentListener = onShowChooseFragmentListener;
    }

    public void setJobTypeViewText(String text) {
        jobTypeView.setText(text);
    }

    public void setPersonNumberViewText(String text) {
        personNumberView.setText(text);
    }

    public void setEducationViewText(String text) {
        educationView.setText(text);
    }

    /**
     * 显示选择模块
     *
     * @param v 点击事件监听的view
     */
    private void showChooseFragment(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        if (onShowChooseFragmentListener != null) {
            onShowChooseFragmentListener.onShowChooseFragment(v);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("jobNameView", jobNameView.getText().toString());
        outState.putString("momeyView", momeyView.getText().toString());
        outState.putString("describe", describe.getText().toString());
        outState.putString("personNumberView", personNumberView.getText().toString());
        outState.putString("educationView", educationView.getText().toString());
        outState.putString("jobTypeView", jobTypeView.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
