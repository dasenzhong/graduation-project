package bishe.dgut.edu.cn.reallygoodapp.home.experience;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/20.
 */

public class HomeExperienceActivity extends Activity {

    List<String> experienceDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_experience);

        experienceDataList = new ArrayList<>();
        for (int i = 0 ; i<10;i++) {
            experienceDataList.add(getResources().getString(R.string.test2));
        }


        ListView experienceList = (ListView) findViewById(R.id.experience_list);
        experienceList.setAdapter(experienceListAdapter);

        LinearLayout back = (LinearLayout) findViewById(R.id.experience_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private BaseAdapter experienceListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return experienceDataList == null ? 0 : experienceDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return experienceDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ExperienceViewHolder experienceViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_experience_list_item, parent, false);
                experienceViewHolder = new ExperienceViewHolder();
                experienceViewHolder.avatar = (ImageView) convertView.findViewById(R.id.experience_list_avatar);
                experienceViewHolder.title = (TextView) convertView.findViewById(R.id.experience_list_title);
                experienceViewHolder.look = (TextView) convertView.findViewById(R.id.experience_list_look);
                experienceViewHolder.time = (TextView) convertView.findViewById(R.id.experience_list_time);
                convertView.setTag(experienceViewHolder);
            } else {
                experienceViewHolder = (ExperienceViewHolder) convertView.getTag();
            }

            return convertView;
        }
    };

    private class ExperienceViewHolder{
        ImageView avatar;
        TextView title;
        TextView look;
        TextView time;
    }

}
