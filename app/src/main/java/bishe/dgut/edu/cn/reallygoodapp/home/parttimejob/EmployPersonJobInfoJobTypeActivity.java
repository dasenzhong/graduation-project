package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/10.
 */

public class EmployPersonJobInfoJobTypeActivity extends Activity {

    private TextView titleView;
    private List<Integer> titlePositionList;                //记录list的标题位置
    private List<String> industryStringList;

    private EmployPersonJobInfoJobTypeDetailFragment detailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parttimejob_employperson_info_jobtypechoose);

        //标题
        titleView = (TextView) findViewById(R.id.employperson_jobtypeactvity_title);

        //意外中断的处理
        if (savedInstanceState != null) {
            detailFragment = (EmployPersonJobInfoJobTypeDetailFragment) getFragmentManager().findFragmentByTag("detail");
            titleView.setText(savedInstanceState.getString("titleView"));
        }

        if (detailFragment == null) {
            detailFragment = new EmployPersonJobInfoJobTypeDetailFragment();
        }


        industryStringList = initListData();

        //行业列表
        final ListView industryList = (ListView) findViewById(R.id.employperson_jobtypeactvity_list);
        industryList.setAdapter(industryListAdapter);
        industryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!titlePositionList.contains(position)) {
                    detailFragment.setDetailStringList(industryStringList.get(position));
                    showDetailFragment();
                    titleView.setText(industryStringList.get(position));
                }
            }
        });

        //返回键
        ImageView back = (ImageView) findViewById(R.id.employperson_jobtypeactvity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailFragment.isAdded()) {
                    titleView.setText(getResources().getString(R.string.employperson_infofragment_jobtypeactivity_actionbar_title));
                    getFragmentManager().beginTransaction().remove(detailFragment).commit();
                } else {
                    finish();
                }
            }
        });

        //确认键
        Button ok = (Button) findViewById(R.id.employperson_jobtypeactvity_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailFragment.isAdded()) {
                    String chooseText = detailFragment.getChooseText();
                    Intent intent = new Intent();
                    intent.putExtra("choosetext", chooseText);
                    setResult(getResources().getInteger(R.integer.JOBTYPE_RESULTCODE), intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(EmployPersonJobInfoJobTypeActivity.this, "请选择你要的选项", Toast.LENGTH_SHORT);
//                    toast.setText("请选择你要的选项");
//                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void showDetailFragment() {
        if (detailFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(detailFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.employperson_jobtypeactvity_container, detailFragment, "detail").commit();
        }
    }

    /**
     * 初始化行业列表，并且记录标题的位置
     *
     * @return 行业信息列表
     */
    private List<String> initListData() {
        if (titlePositionList == null) {
            titlePositionList = new ArrayList<>();
        } else {
            titlePositionList.clear();
        }

        List<String> dataList = new ArrayList<>();                                                              //输出总表
        List<String> industryList = Arrays.asList(getResources().getStringArray(R.array.jobtype_industry));     //行业名列表
        List<String> itemList;                                                                                  //对应行业子项列表

        //子项资源id
        int[] itemResId = new int[]{
                R.array.jobtype_industry_computer,
                R.array.jobtype_industry_sale,
                R.array.jobtype_industry_finance,
                R.array.jobtype_industry_production,
                R.array.jobtype_industry_treatment,
                R.array.jobtype_industry_advertisement,
                R.array.jobtype_industry_build,
                R.array.jobtype_industry_administration,
                R.array.jobtype_industry_law,
                R.array.jobtype_industry_service,
                R.array.jobtype_industry_translate,
                R.array.jobtype_industry_environment,
                R.array.jobtype_industry_parttimejob,
                R.array.jobtype_industry_animal,
                R.array.jobtype_industry_other
        };

        for (int i = 0; i < industryList.size(); i++) {

            //记录标题的位置
            if (titlePositionList.isEmpty()) {
                titlePositionList.add(0);
            } else {
                titlePositionList.add(dataList.size());
            }

            dataList.add(industryList.get(i));                                          //添加行业名

            itemList = Arrays.asList(getResources().getStringArray(itemResId[i]));      //设置对应行业名的子项
            dataList.addAll(itemList);                                                  //添加对应子项
        }

        return dataList;
    }

    private BaseAdapter industryListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return industryStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return industryStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (titlePositionList.contains(position)) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parttimejob_employperson_info_jobtypechoose_list_title, parent, false);
                TextView title = (TextView) convertView.findViewById(R.id.employperson_jobtypeactvity_list_title_industryname);
                title.setText(getItem(position).toString());
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_parttimejob_employperson_info_jobtypechoose_list_item, parent, false);
                TextView item = (TextView) convertView.findViewById(R.id.employperson_jobtypeactvity_list_item_industryname);
                item.setText(getItem(position).toString());
            }

            return convertView;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("titleView", titleView.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
