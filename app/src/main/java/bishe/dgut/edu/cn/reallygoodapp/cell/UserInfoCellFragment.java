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
 * Created by Administrator on 2017/3/7.
 */

public class UserInfoCellFragment extends Fragment {

    private View view;

    private TextView inputText, text;
    private ImageView avatar,foreward;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_info_cell_item, null);

            inputText = (TextView) view.findViewById(R.id.userinfo_cell_inputtext);
            text = (TextView) view.findViewById(R.id.userinfo_cell_text);
            avatar = (ImageView) view.findViewById(R.id.userinfo_cell_avatar);
            foreward = (ImageView) view.findViewById(R.id.userinfo_cell_forward);
        }

        return view;
    }

    //设置cell标题
    public void setTextText(String string) {
        text.setText(string);
    }

    //设置信息text
    public void setInputTextText(String string) {
        inputText.setText(string);
    }

    //设置信息text显隐
    public void setInputTextGone(boolean b) {
        if (b) {
            inputText.setVisibility(View.GONE);
        } else {
            inputText.setVisibility(View.VISIBLE);
        }
    }

    //设置头像
    public void setAvatar(Bitmap bmp) {
        avatar.setImageBitmap(bmp);
    }

    //设置头像显隐
    public void setAvatarGone(boolean b) {
        if (b) {
            avatar.setVisibility(View.GONE);
        } else {
            avatar.setVisibility(View.VISIBLE);
        }
    }

    public void setForewardGone(boolean b) {
        if (b) {
            foreward.setVisibility(View.GONE);
        } else {
            foreward.setVisibility(View.VISIBLE);
        }
    }
}
