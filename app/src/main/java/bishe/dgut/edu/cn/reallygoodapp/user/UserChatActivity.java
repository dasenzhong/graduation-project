package bishe.dgut.edu.cn.reallygoodapp.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserChatActivity extends Activity implements ViewPager.OnPageChangeListener{

    private List<View> viewList;

    private FrameLayout chatLayout;
    private FrameLayout articleLayout;
    private TextView chatText;
    private TextView articleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        viewList = new ArrayList<>();

        chatLayout = (FrameLayout) findViewById(R.id.userchat_chatlayout);
        articleLayout = (FrameLayout) findViewById(R.id.userchat_articlelayout);

        chatText = (TextView) findViewById(R.id.userchat_chattext);
        articleText = (TextView) findViewById(R.id.userchat_articletext);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.userchat_viewpager);

        View chatView = LayoutInflater.from(this).inflate(R.layout.activity_user_chat_chatview, viewPager, false);
        viewList.add(chatView);
        ListView chatListView = (ListView) chatView.findViewById(R.id.userchat_chatlistview);

        View articleView = LayoutInflater.from(this).inflate(R.layout.activity_user_chat_articleview, viewPager, false);
        viewList.add(articleView);
        ListView articleListView = (ListView) articleView.findViewById(R.id.userchat_articlelistview);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("chatLayout")) {
                setChatSelect();
                viewPager.setCurrentItem(0);
            } else {
                setArticleSelect();
                viewPager.setCurrentItem(1);
            }
        } else {
            setChatSelect();
        }

        //吐槽栏点击事件
        chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    setChatSelect();
                    viewPager.setCurrentItem(0, true);
                }
            }
        });

        //文章栏点击事件
        articleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    setArticleSelect();
                    viewPager.setCurrentItem(1, true);
                }
            }
        });

        //返回我的
        LinearLayout back = (LinearLayout) findViewById(R.id.userchat_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * viewpager适配器
     */
    private PagerAdapter viewPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == viewList.get((int) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return position;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
                container.removeView(viewList.get(position));
            }
        };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                setChatSelect();
                break;
            case 1:
                setArticleSelect();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 选择吐槽页
     */
    private void setChatSelect() {
        chatLayout.setSelected(true);
        chatText.setSelected(true);
        articleLayout.setSelected(false);
        articleText.setSelected(false);
    }

    /**
     * 选择文章页
     */
    private void setArticleSelect() {
        chatLayout.setSelected(false);
        chatText.setSelected(false);
        articleLayout.setSelected(true);
        articleText.setSelected(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("chatLayout",chatLayout.isSelected());
//        outState.putBoolean("chatText", chatText.isSelected());
//        outState.putBoolean("articleLayout", articleLayout.isSelected());
//        outState.putBoolean("articleText", articleText.isSelected());
        super.onSaveInstanceState(outState);
    }
}
