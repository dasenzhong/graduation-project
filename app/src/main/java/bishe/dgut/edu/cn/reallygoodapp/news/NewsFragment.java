package bishe.dgut.edu.cn.reallygoodapp.news;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;

/**
 * Created by Administrator on 2017/2/25.
 */

public class NewsFragment extends Fragment {

    private View newsview;
    private NewsListFragment newsListFragment;
    private NoticeListFragment noticeListFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (newsview == null) {
            newsview = inflater.inflate(R.layout.fragment_news, null);

            final FrameLayout newsbackground = (FrameLayout) newsview.findViewById(R.id.news_title_newsbackground);
            final TextView newstext = (TextView) newsview.findViewById(R.id.news_title_newstext);
            final FrameLayout noticebackground = (FrameLayout) newsview.findViewById(R.id.news_title_noticebackground);
            final TextView noticetext = (TextView) newsview.findViewById(R.id.news_title_noticetext);

            //初始化选择状态
            showNewsList(true);
            newsbackground.setSelected(true);
            newstext.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_blue));
            noticebackground.setSelected(false);
            noticetext.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));

            //沉浸式状态栏
            View view = newsview.findViewById(R.id.news_status);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = GetAppSize.statusBarHeight(getResources());
            view.setLayoutParams(params);


            newsbackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected()) {
                        v.setSelected(true);
                        newstext.setTextColor(ContextCompat.getColor(v.getContext(),R.color.black_blue));
                        noticebackground.setSelected(false);
                        noticetext.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
                        showNewsList(true);
                        showNoticeList(false);
                    }
                }
            });

            noticebackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected()) {
                        v.setSelected(true);
                        noticetext.setTextColor(ContextCompat.getColor(v.getContext(), R.color.black_blue));
                        newsbackground.setSelected(false);
                        newstext.setTextColor(ContextCompat.getColor(v.getContext(),R.color.white));
                        showNoticeList(true);
                        showNewsList(false);
                    }
                }
            });

        }

        return newsview;
    }

    private void showNewsList(boolean isshow) {
        if (newsListFragment == null) {
            newsListFragment = (NewsListFragment) getFragmentManager().findFragmentByTag("news_title_news");
            if (newsListFragment == null) {
                newsListFragment = new NewsListFragment();
            }
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (isshow) {
            if (newsListFragment.isAdded()) {
                ft.show(newsListFragment);
            } else {
                ft.add(R.id.news_container, newsListFragment, "news_title_news");
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

    private void showNoticeList(boolean isshow) {
        if (noticeListFragment == null) {
            noticeListFragment = (NoticeListFragment) getFragmentManager().findFragmentByTag("news_title_notice");
            if (noticeListFragment == null) {
                noticeListFragment = new NoticeListFragment();
            }
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (isshow) {
            if (noticeListFragment.isAdded()) {
                ft.show(noticeListFragment);
            } else {
                ft.add(R.id.news_container, noticeListFragment, "news_title_notice");
            }
        } else {
            if (noticeListFragment.isAdded()) {
                ft.hide(noticeListFragment);
            } else {
                return;
            }
        }
        ft.commit();
    }
}
