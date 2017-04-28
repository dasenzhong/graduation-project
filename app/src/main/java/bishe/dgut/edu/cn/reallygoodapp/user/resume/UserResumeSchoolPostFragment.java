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

public class UserResumeSchoolPostFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_resume_school_post, container, false);

            LinearLayout postAddLayout = (LinearLayout) view.findViewById(R.id.userresume_schoolactivity_postfragment_addlayout);
            postAddLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), UserResumeSchoolPostAddActivity.class));
                }
            });

            ListView postList = (ListView) view.findViewById(R.id.userresume_schoolactivity_postfragment_list);

        }

        return view;
    }
}
