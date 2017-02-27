package bishe.dgut.edu.cn.reallygoodapp.news;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;

/**
 * Created by Administrator on 2017/2/25.
 */

public class NewsFragment extends Fragment {

    private View newsview;
    private NewsListFragment newsListFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (newsview == null) {
            newsview = inflater.inflate(R.layout.fragment_news, null);

            showNewsList(true);

            //沉浸式状态栏
            View view = newsview.findViewById(R.id.news_status);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = GetAppSize.statusBarHeight(getResources());
            view.setLayoutParams(params);
        }

        return  newsview;
    }

    private void showNewsList(boolean isshow) {
        if (newsListFragment == null) {
            newsListFragment = new NewsListFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (isshow) {
            if (newsListFragment.isAdded()) {
                ft.show(newsListFragment);
            } else {
                ft.add(R.id.news_container, newsListFragment);
            }
        } else {
            if (newsListFragment.isAdded()) {
                ft.hide(newsListFragment);
            } else {
                return;
            }
        }
        ft.commit();
    }
}
