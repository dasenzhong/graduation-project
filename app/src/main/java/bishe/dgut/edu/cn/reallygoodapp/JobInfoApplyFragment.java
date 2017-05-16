package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/22.
 */

public class JobInfoApplyFragment extends Fragment {

    private View view;

    interface OnBlankClickListener {
        void onBlankClick();
    }

    interface OnAgentLayoutClickListener {
        void onAgentLayoutClick();
    }

    interface OnJobLayoutClickListener {
        void onJobLayoutClick();
    }

    private OnBlankClickListener onBlankClickListener;
    private OnAgentLayoutClickListener onAgentLayoutClickListener;
    private OnJobLayoutClickListener onJobLayoutClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_jobinfo_apply, container, false);

            //点击空白
            LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.jobinfo_apply_mainlayout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBlankClickListener != null) {
                        onBlankClickListener.onBlankClick();
                    }
                }
            });

            //点击成为代理人
            FrameLayout agentLayout = (FrameLayout) view.findViewById(R.id.jobinfo_apply_agentlayout);
            agentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAgentLayoutClickListener != null) {
                        onAgentLayoutClickListener.onAgentLayoutClick();
                    }
                }
            });

            //点击申请职位
            FrameLayout jobLayout = (FrameLayout) view.findViewById(R.id.jobinfo_apply_joblayout);
            jobLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onJobLayoutClickListener != null) {
                        onJobLayoutClickListener.onJobLayoutClick();
                    }
                }
            });
        }

        return view;
    }

    public void setOnBlankClickListener(OnBlankClickListener onBlankClickListener) {
        this.onBlankClickListener = onBlankClickListener;
    }

    public void setOnAgentLayoutClickListener(OnAgentLayoutClickListener onAgentLayoutClickListener) {
        this.onAgentLayoutClickListener = onAgentLayoutClickListener;
    }

    public void setOnJobLayoutClickListener(OnJobLayoutClickListener onJobLayoutClickListener) {
        this.onJobLayoutClickListener = onJobLayoutClickListener;
    }
}
