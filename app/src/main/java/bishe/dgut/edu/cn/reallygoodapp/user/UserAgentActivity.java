package bishe.dgut.edu.cn.reallygoodapp.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserAgentActivity extends Activity {

    private List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agent);

        //测试
        stringList = new ArrayList<>();
        for (int i = 0 ; i < 5;i++) {
            stringList.add("代理过的兼职");
        }

        ListView agentlist = (ListView) findViewById(R.id.useragent_list);
        agentlist.setAdapter(agentListAdapter);

        LinearLayout back = (LinearLayout) findViewById(R.id.useragent_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private BaseAdapter agentListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_agent_listitem, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.useragent_list_text);
            textView.setText("" + getItem(position) + position);

            return convertView;
        }
    };
}
