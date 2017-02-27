package bishe.dgut.edu.cn.reallygoodapp.share;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/2/25.
 */

public class ShareFragment extends Fragment {

    private View shareview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (shareview == null) {
            shareview = inflater.inflate(R.layout.fragment_share, null);
        }

        return shareview;
    }
}
