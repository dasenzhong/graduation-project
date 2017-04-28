package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/4/28.
 */

public class UserResumeSchoolHonorFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_resume_school_honor, container, false);

            LinearLayout honorAddLayout = (LinearLayout) view.findViewById(R.id.userresume_schoolactivity_honorfragment_addlayout);
            honorAddLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), UserResumeSchoolHonorAddActivity.class));
                }
            });

            ListView honorList = (ListView) view.findViewById(R.id.userresume_schoolactivity_honorfragment_list);
        }

        return view;
    }
}
