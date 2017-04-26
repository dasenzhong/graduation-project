package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserResumeActivity extends Activity {

    private TextView name;          //姓名
    private TextView sex;           //性别
    private TextView age;           //年龄
    private TextView address;       //居住地
    private TextView school;        //学校
    private TextView telephone;     //电话

    private List<String> workExperienceDataList;
    private List<String> schoolDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume);

        //基本信息栏
        RelativeLayout infoLayout = (RelativeLayout) findViewById(R.id.userresume_infoclicklayout);
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserResumeActivity.this, UserResumeInfoActivity.class));
            }
        });

        //工作经验栏
        RelativeLayout workExperienceLayout = (RelativeLayout) findViewById(R.id.userresume_workexperienceclicklayout);

        //在校情况栏
        RelativeLayout schoolLayout = (RelativeLayout) findViewById(R.id.userresume_schoolclicklayout);

        name = (TextView) findViewById(R.id.userresume_info_name);
        sex = (TextView) findViewById(R.id.userresume_info_sex);
        age = (TextView) findViewById(R.id.userresume_info_age);
        address = (TextView) findViewById(R.id.userresume_info_address);
        school = (TextView) findViewById(R.id.userresume_info_school);
        telephone = (TextView) findViewById(R.id.userresume_info_telephone);

        ListView workExperienceList = (ListView) findViewById(R.id.userresume_workexperiencelist);
        workExperienceList.setAdapter(workExperienceAdaptar);
        getListViewHeight(workExperienceList);

        ListView schoolList = (ListView) findViewById(R.id.userresume_schoollist);
        schoolList.setAdapter(schoolAdaptar);
        getListViewHeight(schoolList);

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 工作经验适配器
     */
    private BaseAdapter workExperienceAdaptar = new BaseAdapter() {
        @Override
        public int getCount() {
            return (workExperienceDataList == null ? 1 : workExperienceDataList.size());
        }

        @Override
        public Object getItem(int position) {
            return workExperienceDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (workExperienceDataList == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_workperiencelist_nullitem, parent, false);
            }

            return convertView;
        }
    };

    /**
     * 在校情况适配器
     */
    private BaseAdapter schoolAdaptar = new BaseAdapter() {
        @Override
        public int getCount() {
            return (schoolDataList == null ? 1 : schoolDataList.size());
        }

        @Override
        public Object getItem(int position) {
            return schoolDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (schoolDataList == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_school_nullitem, parent, false);
            }

            return convertView;
        }
    };

    /**
     * 重新计量listview的高度
     * @param listView  要测量的listview
     */
    private void getListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();        //获取测量listview的适配器

        if (listAdapter == null) {
            return;
        }
        int listViewHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View view = listAdapter.getView(i, null, listView);
            view.measure(0, 0);
            listViewHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = listViewHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
