package bishe.dgut.edu.cn.reallygoodapp.cell;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserSettingCellFragment extends Fragment {

    private View view;

    private TextView inputText, text;
    private ImageView image,foreward;

    public interface OnCellClickListener{
        void onClick();
    }

    private OnCellClickListener onCellClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_setting_cell_item, null);

            inputText = (TextView) view.findViewById(R.id.usersetting_cell_inputtext);
            text = (TextView) view.findViewById(R.id.usersetting_cell_text);
            image = (ImageView) view.findViewById(R.id.usersetting_cell_image);
            foreward = (ImageView) view.findViewById(R.id.usersetting_cell_forward);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.usersetting_cell_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deal();
                }
            });
        }

        return view;

    }

    private void deal() {
        if (onCellClickListener != null) {
            onCellClickListener.onClick();
        }
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
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
    public void setImage(Bitmap bmp) {
        image.setImageBitmap(bmp);
    }

    //设置图片显隐
    public void setAvatarGone(boolean b) {
        if (b) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
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
