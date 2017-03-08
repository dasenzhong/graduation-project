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
 * Created by Administrator on 2017/2/27.
 */

//设置的条目模型fragment
public class UserCellOfItemFragment extends Fragment {

    private View view;
    private ImageView image;
    private TextView text;

    public interface OnCellClickListener{
        void onCellClick();
    }

    private OnCellClickListener onCellClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_cell_item, null);

            image = (ImageView) view.findViewById(R.id.user_cell_image);
            text = (TextView) view.findViewById(R.id.user_cell_text);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.user_cell_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goActivity();
                }
            });
        }

        return view;
    }

    private void goActivity() {
        if (onCellClickListener != null) {
            onCellClickListener.onCellClick();
        }
    }

    //设置名称
    public void setTextName(String string) {
        text.setText(string);
    }

    //设置图标
    public void setImage(Bitmap bmp) {
        image.setImageBitmap(bmp);
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }
}
