package bishe.dgut.edu.cn.reallygoodapp.user.info;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/23.
 */

public class AvatarMoreFragment extends Fragment {

    private View view;

    interface OnLayoutClickListener {
        void onLayoutClick(int id);
    }

    private OnLayoutClickListener onLayoutClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_info_avatar_more, container, false);

            FrameLayout shotLayout = (FrameLayout) view.findViewById(R.id.userinfo_avatar_more_shotlayout);
            shotLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick(v.getId());
                    }
                }
            });

            FrameLayout albumLayout = (FrameLayout) view.findViewById(R.id.userinfo_avatar_more_albumlayout);
            albumLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick(v.getId());
                    }
                }
            });

            FrameLayout saveLayout = (FrameLayout) view.findViewById(R.id.userinfo_avatar_more_savelayout);
            saveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick(v.getId());
                    }
                }
            });

            FrameLayout cancelLayout = (FrameLayout) view.findViewById(R.id.userinfo_avatar_more_cancellayout);
            cancelLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick(v.getId());
                    }
                }
            });

            FrameLayout mainLayout = (FrameLayout) view.findViewById(R.id.userinfo_avatar_more_mainlayout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLayoutClickListener != null) {
                        onLayoutClickListener.onLayoutClick(v.getId());
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
