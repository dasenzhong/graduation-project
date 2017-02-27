package bishe.dgut.edu.cn.reallygoodapp.cell;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/2/27.
 */

//设置的个人介绍模型fragment
public class UserInfoCellFragment extends Fragment {

    private View view;
    private TextView name,introduce;
    private ImageView avatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_cell_info, null);

            name = (TextView) view.findViewById(R.id.user_infocell_name);
            introduce = (TextView) view.findViewById(R.id.user_infocell_introduce);
            avatar = (ImageView) view.findViewById(R.id.user_infocell_avatar);

        }
        return view;
    }

    //设置昵称
    public void setName(String string) {
        name.setText(string);
    }

    //设置个签
    public void setIntroduce(String string) {
        introduce.setText(string);
    }

    //设置头像
    public void setAvatar(Bitmap bmp) {
        avatar.setImageBitmap(bmp);
    }
}
