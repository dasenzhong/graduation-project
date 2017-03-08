package bishe.dgut.edu.cn.reallygoodapp.share;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;

/**
 * Created by Administrator on 2017/2/25.
 */

public class ShareFragment extends Fragment implements AbsListView.OnScrollListener{

    private View shareview;
    private View sharelistHead;
    private ImageView toTop;

    private LinearLayout actionbar;
    private int actionbarheight;
    private int alpha;
    private Drawable actionbarDrawable;

    private List<String> stringList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        stringList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++) {
            stringList.add("你收到了一条来自别人的推送");
        }


        if (shareview == null) {
            shareview = inflater.inflate(R.layout.fragment_share, null);
            sharelistHead = inflater.inflate(R.layout.fragment_share_listhead, null);

            //沉浸式状态栏
            View view = shareview.findViewById(R.id.share_status);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = GetAppSize.statusBarHeight(getResources());
            view.setLayoutParams(params);

            //actionbar
            actionbar = (LinearLayout) shareview.findViewById(R.id.share_actionbar);
            actionbarDrawable = actionbar.getBackground().mutate();
//            actionbar.setVisibility(View.GONE);

            //信息列表
            final ListView shareList = (ListView) shareview.findViewById(R.id.share_list);
//            Log.d("padding---------------" , "" +shareList.getPaddingTop());
            actionbarheight = shareList.getPaddingTop() + params.height;
            shareList.setPadding(0, actionbarheight, 0, 0);

            shareList.addHeaderView(sharelistHead);
            shareList.setAdapter(shareListAdapter);
            shareList.setOnScrollListener(this);

            //回到顶部按钮
            toTop = (ImageView) shareview.findViewById(R.id.share_totop);
            toTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareList.smoothScrollToPosition(0);
                    v.setVisibility(View.GONE);
                }
            });
        }

        return shareview;
    }

    private BaseAdapter shareListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.share_or_chat_listitem, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.share_listitem_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        if (firstVisibleItem < 2) {

            alpha = (int) (((float) sharelistHead.getTop() / actionbarheight) * 255);
            //排除开始getTop()为0的情况
            if (alpha == 0 && actionbar.getVisibility() == View.VISIBLE) {
                return;
            }
            if (alpha < 50) {
                actionbar.setVisibility(View.GONE);             //透明度小于50直接消失
            } else {
                if (actionbar.getVisibility() != View.VISIBLE) {
                    actionbar.setVisibility(View.VISIBLE);
                }
                actionbarDrawable.setAlpha(alpha);
            }

//            Log.d("2--------------", "" + firstVisibleItem);
//            Log.d("height--------------", "" + actionbarheight);
//            Log.d("TOP--------------", "" + sharelistHead.getTop());
//            Log.d("alpha--------------", "" + alpha);
        }

        if (firstVisibleItem > 5) {
            toTop.setVisibility(View.VISIBLE);
        } else {
            if (toTop !=null && toTop.getVisibility() == View.VISIBLE ) {
                toTop.setVisibility(View.GONE);
            }
        }


    }
}
