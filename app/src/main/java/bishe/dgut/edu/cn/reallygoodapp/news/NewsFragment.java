package bishe.dgut.edu.cn.reallygoodapp.news;

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

public class NewsFragment extends Fragment {

    private View newsview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (newsview == null) {
            newsview = inflater.inflate(R.layout.fragment_news, null);
        }

        return  newsview;
    }
}
