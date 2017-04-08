package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/4/8.
 */

public class EmployPersonJobInfoJobTypeFragment extends Fragment {

    private View view;

    interface OnLayoutClickListener {
        void onLayoutClick();
    }

    private OnLayoutClickListener onLayoutClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_info_jobtypechoose, container, false);
            FrameLayout layout = (FrameLayout) view.findViewById(R.id.employperson_jobtypefragment_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
}
