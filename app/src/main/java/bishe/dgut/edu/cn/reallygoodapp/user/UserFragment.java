package bishe.dgut.edu.cn.reallygoodapp.user;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.GetAppSize;
import bishe.dgut.edu.cn.reallygoodapp.model.UserCellFragment;
import bishe.dgut.edu.cn.reallygoodapp.model.UserInfoCellFragment;

/**
 * Created by Administrator on 2017/2/25.
 */

public class UserFragment extends Fragment {

    private View userview;
    //吐槽，分享，简历，代理，能力，收藏，设置
    private UserCellFragment chatCell,experienceCell,resumeCell,agentCell,abilityCell,collectCell,settingCell;
    private UserInfoCellFragment infoCell;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (userview == null) {
            userview = inflater.inflate(R.layout.fragment_user, null);

            infoCell = (UserInfoCellFragment) getChildFragmentManager().findFragmentById(R.id.user_info);
            chatCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_chat);
            experienceCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_experience);
            resumeCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_resume);
            agentCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_agent);
            abilityCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_ability);
            collectCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_collect);
            settingCell = (UserCellFragment) getChildFragmentManager().findFragmentById(R.id.user_set);
            setCellAttribute();

            //沉浸式状态栏
            View view = userview.findViewById(R.id.user_status);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = GetAppSize.statusBarHeight(getResources());
            view.setLayoutParams(params);
        }

        return userview;
    }

    private void setCellAttribute() {
        chatCell.setTextName("我的吐槽");
        chatCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_chat));

        experienceCell.setTextName("我的文章");
        experienceCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_experience));

        resumeCell.setTextName("简历");
        resumeCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_resume));

        agentCell.setTextName("代理");
        agentCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_agent));

        abilityCell.setTextName("能力值");
        abilityCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_ability));

        collectCell.setTextName("收藏");
        collectCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_collect));

        settingCell.setTextName("设置");
        settingCell.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.user_cell_setting));
    }
}
