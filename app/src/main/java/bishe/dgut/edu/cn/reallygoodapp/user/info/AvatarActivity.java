package bishe.dgut.edu.cn.reallygoodapp.user.info;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.module.avatarview.ChangeAvatarView;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/22.
 */

public class AvatarActivity extends Activity {

    private AvatarMoreFragment avatarMoreFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_avatar);

        if (savedInstanceState != null) {
            avatarMoreFragment = (AvatarMoreFragment) getFragmentManager().findFragmentByTag("more");
            if (avatarMoreFragment != null) {
                setMoreFragmentListener();
            }
        }

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userinfo_avatar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //图像显示
        ChangeAvatarView changeAvatarView = (ChangeAvatarView) findViewById(R.id.userinfo_avatar_image);
        changeAvatarView.setBmp(BitmapFactory.decodeResource(getResources(), R.drawable.test));

        //更多操作
        ImageView more = (ImageView) findViewById(R.id.userinfo_avatar_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreFragment();
            }
        });

    }

    /**
     * 显示更多fragment
     */
    private void showMoreFragment() {
        if (avatarMoreFragment == null) {
            avatarMoreFragment = new AvatarMoreFragment();
            setMoreFragmentListener();
        }

        if (avatarMoreFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(avatarMoreFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.userinfo_avatar_mainlayout, avatarMoreFragment, "more").commit();
        }
    }

    /**
     * 移除更多fragment
     */
    private void removeMoreFragment() {
        if (avatarMoreFragment.isAdded()) {
            getFragmentManager().beginTransaction().remove(avatarMoreFragment).commit();
        }
    }

    /**
     * 设置更多fragment的点击回调
     */
    private void setMoreFragmentListener() {
        avatarMoreFragment.setOnLayoutClickListener(new AvatarMoreFragment.OnLayoutClickListener() {
            @Override
            public void onLayoutClick(int id) {
                switch (id) {
                    case R.id.userinfo_avatar_more_shotlayout:
                        shot();
                        break;
                    case R.id.userinfo_avatar_more_albumlayout:
                        album();
                        break;
                    case R.id.userinfo_avatar_more_savelayout:
                        save();
                        break;
                    case R.id.userinfo_avatar_more_cancellayout:
                        removeMoreFragment();
                        break;
                    case R.id.userinfo_avatar_more_mainlayout:
                        removeMoreFragment();
                        break;
                    default:
                        removeMoreFragment();
                }
            }
        });
    }

    /**
     * 保存照片
     */
    private void save() {
        Toast.makeText(this, "保存图片", Toast.LENGTH_SHORT).show();
    }

    /**
     * 从相册选择图片
     */
    private void album() {
        Toast.makeText(this, "相册选择", Toast.LENGTH_SHORT).show();
    }

    /**
     * 拍摄
     */
    private void shot() {
        Toast.makeText(this, "拍摄", Toast.LENGTH_SHORT).show();
    }
}
