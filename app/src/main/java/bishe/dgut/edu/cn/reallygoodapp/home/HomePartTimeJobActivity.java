package bishe.dgut.edu.cn.reallygoodapp.home;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/20.
 */

public class HomePartTimeJobActivity extends Activity {

    private List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_parttimejob);

        //测试
        stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("这是一条兼职消息");
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_parttimejob_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PartTimeJobAdapter());
        recyclerView.addItemDecoration(new PartTimeJobItemDecoration(15));
    }


    /**
     * RecyclerView适配器
     */
    private class PartTimeJobAdapter extends RecyclerView.Adapter<PartTimeJobViewHolder> {

        @Override
        public PartTimeJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            PartTimeJobViewHolder partTimeJobViewHolder = new PartTimeJobViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_parttimejob_listitem, parent, false)
            );
            return partTimeJobViewHolder;
        }

        @Override
        public void onBindViewHolder(PartTimeJobViewHolder holder, int position) {
            holder.textView.setText("" + stringList.get(position) +position);
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }

    }

    /**
     * RecyclerView的item容器
     */
    private static class PartTimeJobViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public PartTimeJobViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_text);
        }
    }

    /**
     * RecyclerView分割线类
     */
    private class PartTimeJobItemDecoration extends RecyclerView.ItemDecoration {

        private int divider = 10;               //分割线间距默认为10

        private boolean isLastRow = false;

        public PartTimeJobItemDecoration() {

        }

        /**
         * 构造函数
         * @param divider item间的间距
         */
        public PartTimeJobItemDecoration(int divider) {
            this.divider = divider;
        }


        /**
         * @param c         画布对象
         * @param parent    父容器
         * @param state     状态
         */
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c,parent,state);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(parent.getContext(), R.color.transparent));


            for (int i = 0; i < parent.getChildCount(); i++) {

                View view = parent.getChildAt(i);

//                Log.d("position---", "" + parent.getChildAdapterPosition(view));

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

                int left = view.getLeft();
                int right = view.getRight();
                int top = view.getBottom() + params.bottomMargin;
                int bottom = top + divider;

                c.drawRect(left, top, right, bottom, paint);
            }
        }


        /**
         * 间距的宽度，在这个间距内描绘分割线
         * @param outRect   top，left，right，bottom四个方向的间距
         * @param view      当前item
         * @param parent    父容器
         * @param state     状态
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                return;
            }
            outRect.set(0, 0, 0, divider);
        }
    }

}
