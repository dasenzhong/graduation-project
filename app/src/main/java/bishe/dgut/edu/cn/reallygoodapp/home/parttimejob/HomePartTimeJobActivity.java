package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.JobInfoActivity;
import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.Job;
import bishe.dgut.edu.cn.reallygoodapp.bean.Page;
import bishe.dgut.edu.cn.reallygoodapp.module.applyclickview.ApplyClickView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/20.
 */

public class HomePartTimeJobActivity extends Activity implements PartTimeJobSwitchPlaceFragment.OnCloseSwitchFragmentListener {

    private List<Job> jobList;
    private PartTimeJobSwitchPlaceFragment switchPlaceFragment;

    private boolean isFindWork = true;              //记录title选择的类型，找兼职/做代理
    private boolean isdetail = false;
    private PartTimeJobAdapter partTimeJobAdapter;

    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_parttimejob);

        if (savedInstanceState != null) {               //意外结束时
            switchPlaceFragment = (PartTimeJobSwitchPlaceFragment) getFragmentManager().findFragmentByTag("switchplace");
            if (switchPlaceFragment != null) {
                switchPlaceFragment.setOnCloseSwitchFragmentListener(this);
                if (savedInstanceState.getBoolean("isSwitchFragmentShow", false)) {
                    getFragmentManager().beginTransaction().show(switchPlaceFragment).commit();
                } else {
                    getFragmentManager().beginTransaction().hide(switchPlaceFragment).commit();
                }
            }
        }

        //兼职列表
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.parttimejob_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        partTimeJobAdapter = new PartTimeJobAdapter();
        recyclerView.setAdapter(partTimeJobAdapter);
        recyclerView.addItemDecoration(new PartTimeJobItemDecoration(15));

        //actionbar
        final FrameLayout findWorkLayout = (FrameLayout) findViewById(R.id.parttimejob_actionbar_title_findworklayout);
        final FrameLayout doAgentLayout = (FrameLayout) findViewById(R.id.parttimejob_actionbar_title_doagentlayout);
        final TextView findWorkTitle = (TextView) findViewById(R.id.parttimejob_actionbar_title_findworktext);
        final TextView doAgentTitle = (TextView) findViewById(R.id.parttimejob_actionbar_title_doagenttext);
        findWorkLayout.setSelected(true);
        findWorkTitle.setSelected(true);
        findWorkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    v.setSelected(true);
                    findWorkTitle.setSelected(true);
                    doAgentLayout.setSelected(false);
                    doAgentTitle.setSelected(false);
                    isFindWork = true;
//                    Toast.makeText(v.getContext(), "找工作", Toast.LENGTH_SHORT).show();
                }
            }
        });
        doAgentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    v.setSelected(true);
                    doAgentTitle.setSelected(true);
                    findWorkLayout.setSelected(false);
                    findWorkTitle.setSelected(false);
                    isFindWork = false;
//                    Toast.makeText(v.getContext(), "做代理", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //选择栏
        LinearLayout provinceLayout = (LinearLayout) findViewById(R.id.parttimejob_switch_layout_province);
        provinceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePlace();
            }
        });

        LinearLayout cityLayout = (LinearLayout) findViewById(R.id.parttimejob_switch_layout_city);
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePlace();
            }
        });

        LinearLayout townLayout = (LinearLayout) findViewById(R.id.parttimejob_switch_layout_town);
        townLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePlace();
            }
        });

        //简洁/详细
        final TextView detailText = (TextView) findViewById(R.id.parttimejob_switch_text_detail);
        FrameLayout detailLayout = (FrameLayout) findViewById(R.id.parttimejob_switch_layout_detail);
        detailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isdetail) {
                    isdetail = false;
                    detailText.setText(R.string.parttimejob_switch_detail_simple);
                } else {
                    isdetail = true;
                    detailText.setText(R.string.parttimejob_switch_detail_complex);
                }
                partTimeJobAdapter.notifyDataSetChanged();
            }
        });


        //返回键
        ImageView back = (ImageView) findViewById(R.id.parttimejob_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //雇用按钮
        TextView employText = (TextView) findViewById(R.id.parttimejob_employ);
        employText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePartTimeJobActivity.this, EmployPersonActivity.class));
            }
        });

        jobList = new ArrayList<>();
        page = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJob();
    }

    private void loadJob() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getnewjob/" + page).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getnewjob--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final Page<Job> jobPage = new ObjectMapper().readValue(response.body().string(), new TypeReference<Page<Job>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jobPage.getNumber() > page) {
                                if (jobList == null) {
                                    jobList = jobPage.getContent();
                                } else {
                                    jobList.addAll(jobPage.getContent());
                                }
                                page = jobPage.getNumber() + 1;

                                partTimeJobAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d("addnewjob--failure", e.getMessage());
                }
            }
        });
    }

    /**
     * 选择地址栏
     */
    private void choosePlace() {
        if (switchPlaceFragment == null) {
            switchPlaceFragment = new PartTimeJobSwitchPlaceFragment();
            switchPlaceFragment.setOnCloseSwitchFragmentListener(this);
        }


        if (switchPlaceFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(switchPlaceFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.parttimejob_container, switchPlaceFragment, "switchplace").commit();       //筛选fragment添加tag
        }
    }

    @Override
    public void onClose() {
        if (switchPlaceFragment.isAdded()) {
            getFragmentManager().beginTransaction().hide(switchPlaceFragment).commit();
        }
    }

    /**
     * RecyclerView适配器
     * 3
     */
    private class PartTimeJobAdapter extends RecyclerView.Adapter<PartTimeJobViewHolder> {

        @Override
        public PartTimeJobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PartTimeJobViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parttimejob_listitem, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(PartTimeJobViewHolder holder, final int position) {

            final Job job = jobList.get(position);

            //点击item处理
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomePartTimeJobActivity.this, JobInfoActivity.class);
                    intent.putExtra("jobid", job.getId());
                    startActivity(intent);
                }
            });

            //点击选择框处理
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApplyClickView view = (ApplyClickView) v;
                        view.toggle();
//                    if (view.isChecked()) {
//                        Log.d("选择：", "" + position);
//                    } else {
//                        Log.d("取消选择：", "" + position);
//                    }
                }
            });

            holder.jobName.setText(job.getJobName());
            holder.company.setText(job.getCompanyUser().getCompanyName());
            holder.companyType.setText(job.getCompanyUser().getCompanyType());
            holder.createTime.setText(job.getCreateDate().toString());
            holder.describe.setText(job.getDecribe());
            holder.education.setText(job.getEducation());
            holder.money.setText(job.getMoney());
            if (job.getWorkTown() != null) {
                holder.workPlace.setText(job.getWorkTown());
            } else {
                if (job.getWorkCity() != null) {
                    holder.workPlace.setText(job.getWorkCity());
                } else {
                    holder.workPlace.setText(job.getWorkProvince());
                }
            }

            if (isdetail) {
                holder.detailLayout.setVisibility(View.VISIBLE);
            } else {
                holder.detailLayout.setVisibility(View.GONE);
            }
        }

        /**
         * @return 返回总数
         */
        @Override
        public int getItemCount() {
            return jobList == null ? 0 : jobList.size();
        }

    }

    /**
     * RecyclerView的item容器
     */
    private static class PartTimeJobViewHolder extends RecyclerView.ViewHolder {

        private TextView jobName;
        private TextView money;
        private TextView company;
        private TextView workPlace;
        private TextView education;
        private TextView companyType;
        private TextView createTime;
        private TextView describe;

        private LinearLayout itemLayout;
        private LinearLayout detailLayout;

        private ApplyClickView checkbox;

        PartTimeJobViewHolder(View itemView) {
            super(itemView);
            jobName = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_jobname);
            money = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_money);
            company = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_company);
            workPlace = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_workplace);
            education = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_education);
            companyType = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_companytype);
            createTime = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_time);
            describe = (TextView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_describe);

            itemLayout = (LinearLayout) itemView.findViewById(R.id.home_parttimejob_recyclerview_itemlayout);
            detailLayout = (LinearLayout) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_detaillayout);

            checkbox = (ApplyClickView) itemView.findViewById(R.id.home_parttimejob_recyclerview_item_checkbox);
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
         *
         * @param divider item间的间距
         */
        PartTimeJobItemDecoration(int divider) {
            this.divider = divider;
        }


        /**
         * @param c      画布对象
         * @param parent 父容器
         * @param state  状态
         */
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

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
         *
         * @param outRect top，left，right，bottom四个方向的间距
         * @param view    当前item
         * @param parent  父容器
         * @param state   状态
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (!(parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1)) {
                outRect.set(0, 0, 0, divider);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isSwitchFragmentShow", switchPlaceFragment != null && switchPlaceFragment.isVisible());
        super.onSaveInstanceState(outState);
    }
}
