package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class EmployPresonJobInfoFragment extends Fragment {

    private View view;

//    private ScrollView scrollView;

    private EditText jobNameView;
    private EditText moneyView;
    private Spinner moneyUnit;
    private EditText describe;
    private TextView personNumberView;
    private TextView educationView;
    private TextView jobTypeView;

    private int moneyUnitPosition;

    interface OnShowChooseFragmentListener {
        void onShowChooseFragment(View view);
    }

    private OnShowChooseFragmentListener onShowChooseFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_info, container, false);

            final ScrollView scrollView = (ScrollView) view.findViewById(R.id.employperson_info_scrollview);
            final LinearLayout moneyLayout = (LinearLayout) view.findViewById(R.id.employperson_info_moneylayout);
            final LinearLayout educationLayout = (LinearLayout) view.findViewById(R.id.employperson_info_educationlayout);

            //兼职名
            jobNameView = (EditText) view.findViewById(R.id.employperson_info_jobname);
            jobNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        scrollView.scrollTo(0, 0);
                    }
                }
            });
            jobNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return false;
                }
            });

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

            //兼职薪酬
            moneyView = (EditText) view.findViewById(R.id.employperson_info_money);
            moneyView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        scrollView.scrollTo(educationLayout.getLeft(), educationLayout.getBottom());
                    }
                }
            });
            //监听软键盘回车键
            moneyView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return false;
                }
            });
            moneyUnit = (Spinner) view.findViewById(R.id.employperson_info_moneyunit);
            //点击事件
            moneyUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    moneyUnitPosition = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //回到顶部按钮
            final ImageView toTop = (ImageView) view.findViewById(R.id.employperson_info_totop);
            toTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollView.scrollTo(0, 0);
                    v.setVisibility(View.GONE);
                }
            });

            //兼职描述
            describe = (EditText) view.findViewById(R.id.employperson_info_describe);
            describe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        toTop.setVisibility(View.VISIBLE);
                    } else {
                        toTop.setVisibility(View.GONE);
                    }
                }
            });
            describe.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    scrollView.scrollTo(moneyLayout.getLeft(), moneyLayout.getBottom());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            if (savedInstanceState != null) {
                jobNameView.setText(savedInstanceState.getString("jobNameView"));
                jobTypeView.setText(savedInstanceState.getString("jobTypeView"));
                educationView.setText(savedInstanceState.getString("educationView"));
                personNumberView.setText(savedInstanceState.getString("personNumberView"));
                describe.setText(savedInstanceState.getString("describe"));
                moneyView.setText(savedInstanceState.getString("momeyView"));

                moneyUnitPosition = savedInstanceState.getInt("moneyUnitPosition", 0);
                moneyUnit.setSelection(moneyUnitPosition);
            } else {
                moneyUnitPosition = 0;
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

    public String getJobTypeViewText() {
        return jobTypeView.getText().toString().trim();
    }

    public String getJobNameViewText() {
        return jobNameView.getText().toString().trim();
    }

    public String getEducationViewText() {
        return educationView.getText().toString().trim();
    }

    public String getPersonNumberViewText() {
        return personNumberView.getText().toString().trim();
    }

    public String getMoneyViewText() {
        return moneyView.getText().toString().trim() + moneyUnit.getSelectedItem().toString().trim();
    }

    public String getMoneyText() {
        return moneyView.getText().toString().trim();
    }

    public String getDescribeText() {
        return describe.getText().toString().trim();
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
        outState.putString("momeyView", moneyView.getText().toString());
        outState.putString("describe", describe.getText().toString());
        outState.putString("personNumberView", personNumberView.getText().toString());
        outState.putString("educationView", educationView.getText().toString());
        outState.putString("jobTypeView", jobTypeView.getText().toString());
        outState.putInt("moneyUnitPosition", moneyUnitPosition);
        super.onSaveInstanceState(outState);
    }
}
