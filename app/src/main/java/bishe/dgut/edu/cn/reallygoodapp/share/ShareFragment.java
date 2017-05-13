package bishe.dgut.edu.cn.reallygoodapp.share;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;

/**
 * Created by Administrator on 2017/2/25.
 */

public class ShareFragment extends Fragment implements AbsListView.OnScrollListener, NavigationView.OnNavigationItemSelectedListener {

    private View shareview;
    private View sharelistHead;
    private DrawerLayout drawerLayout;

    private ImageView toTop;

    private LinearLayout actionbar;
    private int actionbarheight;
    private int alpha;
    private Drawable actionbarDrawable;         //actionbar变透明背景drawable

    private MenuItem preMenuItem;
    private int itemPosition;

    private List<String> stringList, allList, externalList, schoolList, friendList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //测试例子
        allList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            allList.add("你收到了一条来自全部人的推送");
        }

        externalList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            externalList.add("你收到了一条来自外校的推送");
        }

        schoolList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            schoolList.add("你收到了一条来自本校的推送");
        }

        friendList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            friendList.add("你收到了一条来自朋友的推送");
        }

        //view创建
        if (shareview == null) {
            shareview = inflater.inflate(R.layout.fragment_share, null);
            sharelistHead = inflater.inflate(R.layout.fragment_share_listhead, null);

            //侧边栏
            drawerLayout = (DrawerLayout) shareview.findViewById(R.id.share_drawer);
            NavigationView nvMenu = (NavigationView) shareview.findViewById(R.id.share_nvmenu);
            nvMenu.setItemIconTintList(ContextCompat.getColorStateList(getActivity(), R.color.share_nvmenu_itemiconcolor));
            nvMenu.setItemTextColor(ContextCompat.getColorStateList(getActivity(), R.color.share_nvmenu_itemtextcolor));
            nvMenu.setNavigationItemSelectedListener(this);

            if (savedInstanceState != null) {               //被意外结束时
                alpha = savedInstanceState.getInt("alpha", 0);
                updateListOfMenuItem(savedInstanceState.getString("item"));
                itemPosition = savedInstanceState.getInt("itemPosition");
                preMenuItem = nvMenu.getMenu().getItem(0).getSubMenu().getItem(itemPosition);
            } else {                                        //初始化
                itemPosition = 0;
                preMenuItem = nvMenu.getMenu().getItem(0).getSubMenu().getItem(0);      //默认选择第一项
                preMenuItem.setChecked(true);
                stringList = allList;
            }

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

            //菜单导航栏按钮
            ImageView showNvMenu = (ImageView) shareview.findViewById(R.id.share_show_nvmenu);
            showNvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
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
        } else {
            if (actionbar.getVisibility() == View.VISIBLE) {
                actionbar.setVisibility(View.GONE);
            }
        }

        if (firstVisibleItem > 5) {
            toTop.setVisibility(View.VISIBLE);
        } else {
            if (toTop != null && toTop.getVisibility() == View.VISIBLE) {
                toTop.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (preMenuItem != null) {
            preMenuItem.setChecked(false);
        }
        item.setChecked(true);
        preMenuItem = item;
        updateListOfMenuItem(item.getTitle().toString().trim());
        shareListAdapter.notifyDataSetChanged();
        drawerLayout.closeDrawer(GravityCompat.START, true);
        return true;
    }

    private void updateListOfMenuItem(String itemText) {
        switch (itemText) {
            case "全部":
                stringList = allList;
                itemPosition = 0;
                break;
            case "外校":
                stringList = externalList;
                itemPosition = 1;
                break;
            case "本校":
                stringList = schoolList;
                itemPosition = 2;
                break;
            case "朋友":
                stringList = friendList;
                itemPosition = 3;
                break;
            default:
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("alpha", alpha);
        outState.putString("item", preMenuItem.getTitle().toString().trim());
        outState.putInt("itemPosition", itemPosition);
        super.onSaveInstanceState(outState);
    }
}
