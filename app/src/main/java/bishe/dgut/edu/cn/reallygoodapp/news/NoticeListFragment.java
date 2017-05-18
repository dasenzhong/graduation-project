package bishe.dgut.edu.cn.reallygoodapp.news;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.ApplyDealAgentActivity;
import bishe.dgut.edu.cn.reallygoodapp.ApplyDealJobActivity;
import bishe.dgut.edu.cn.reallygoodapp.JobInfoActivity;
import bishe.dgut.edu.cn.reallygoodapp.PassApplyJobActivity;
import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.NewsCompany;
import bishe.dgut.edu.cn.reallygoodapp.bean.NewsStudent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/9.
 */

public class NoticeListFragment extends Fragment {

    private View view;

    private List<NewsCompany> newsCompanyList;
    private List<NewsStudent> newsStudentList;

    private int usertype;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("usertype", Context.MODE_PRIVATE);
        usertype = sharedPreferences.getInt("usertype", -1);

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news_fragment_noticelist, null);
        }

        ListView noticeList = (ListView) view.findViewById(R.id.news_noticelist);
        switch (usertype) {
            case 0:             //学生
                noticeList.setAdapter(studentListAdapter);
                noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        studentListItemClick(position);
                    }
                });
                break;

            case 1:             //公司
                noticeList.setAdapter(companyListAdapter);
                noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        companyListItemClick(position);
                    }
                });
                break;

            default:
                break;
        }

        return view;
    }

    /**
     * 学生点击处理
     *
     * @param position 获取消息位置
     */
    private void studentListItemClick(int position) {
        NewsStudent newsStudent = newsStudentList.get(position);
        int newsId = newsStudent.getId();
        Intent intent;
            switch (newsStudent.getDeal()) {
                case 1:         //工作
                    if (newsStudent.isPass()) {
                        intent = new Intent(getActivity(), PassApplyJobActivity.class);
                        intent.putExtra("newsId", newsId);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), JobInfoActivity.class);
                        intent.putExtra("jobid", newsStudent.getJob().getId());
                        startActivity(intent);
                    }
                    break;
                case 2:         //代理人
                    intent = new Intent(getActivity(), JobInfoActivity.class);
                    intent.putExtra("jobid", newsStudent.getJob().getId());
                    startActivity(intent);
                    break;
                case 3:         //评论

                    break;
                default:

            }
    }

    /**
     * 公司点击处理
     *
     * @param position 消息的位置
     */
    private void companyListItemClick(int position) {
        NewsCompany newsCompany = newsCompanyList.get(position);
        int newsId = newsCompany.getId();
        Intent intent;
        switch (newsCompany.getDeal()) {
            case 1:         //工作
                intent = new Intent(getActivity(), ApplyDealJobActivity.class);
                intent.putExtra("newsid", newsId);
                startActivity(intent);
                break;
            case 2:         //代理人
                intent = new Intent(getActivity(), ApplyDealAgentActivity.class);
                intent.putExtra("newsid", newsId);
                startActivity(intent);
                break;
            case 3:         //评论

                break;
            default:

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (usertype) {
            case 0:             //学生
                loadNewsStudentList();
                break;

            case 1:             //公司
                loadNewsCompanyList();
                break;

            default:
                break;
        }
    }

    /**
     * 学生用户获取消息
     */
    private void loadNewsStudentList() {

        Link.getClient().newCall(
                Link.getRequestAddress("/getstudentnews").get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("getstudentnews--failure", e.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final List<NewsStudent> newsStudentsGet = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<NewsStudent>>() {
                    });

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            newsStudentList = newsStudentsGet;

                            studentListAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addcompanynews--failure", e.getMessage());
                }
            }
        });

    }

    /**
     * 公司用户获取消息
     */
    private void loadNewsCompanyList() {

        Link.getClient().newCall(
                Link.getRequestAddress("/getcompanynews").get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("getcompanynews--failure", e.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final List<NewsCompany> newsCompanyGet = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<NewsCompany>>() {
                    });

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            newsCompanyList = newsCompanyGet;

                            companyListAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addcompanynews--failure", e.getMessage());
                }
            }
        });

    }

    /**
     * 公司消息列表适配器
     */
    private BaseAdapter companyListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return newsCompanyList == null ? 1 : newsCompanyList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsCompanyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (newsCompanyList == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_fragment_noticelist_nullitem, parent, false);
            } else {
                NewsCompanyListViewHolder newsCompanyListViewHolder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_fragment_noticelistitem, parent, false);
                    newsCompanyListViewHolder = new NewsCompanyListViewHolder();
                    newsCompanyListViewHolder.image = (ImageView) convertView.findViewById(R.id.news_notice_listitem_image);
                    newsCompanyListViewHolder.dot = (ImageView) convertView.findViewById(R.id.news_notice_listitem_dot);
                    newsCompanyListViewHolder.title = (TextView) convertView.findViewById(R.id.news_notice_listitem_title);
                    newsCompanyListViewHolder.newsText = (TextView) convertView.findViewById(R.id.news_notice_listitem_newstext);
                    newsCompanyListViewHolder.time = (TextView) convertView.findViewById(R.id.news_notice_listitem_time);
                    convertView.setTag(newsCompanyListViewHolder);
                } else {
                    newsCompanyListViewHolder = (NewsCompanyListViewHolder) convertView.getTag();
                }

                NewsCompany newsCompany = (NewsCompany) getItem(position);

                switch (newsCompany.getDeal()) {
                    case 1:
                        newsCompanyListViewHolder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_jobandagent));
                        newsCompanyListViewHolder.title.setText("应聘提示");
                        break;
                    case 2:
                        newsCompanyListViewHolder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_jobandagent));
                        newsCompanyListViewHolder.title.setText("代理人申请");
                        break;
                    case 3:
                        newsCompanyListViewHolder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_evaluation));
                        newsCompanyListViewHolder.title.setText("评价提示");
                        break;
                }

                if (newsCompany.isRead()) {
                    newsCompanyListViewHolder.dot.setVisibility(View.GONE);
                } else {
                    newsCompanyListViewHolder.dot.setVisibility(View.VISIBLE);
                }

                newsCompanyListViewHolder.time.setText(newsCompany.getCreateDate().toString());
                newsCompanyListViewHolder.newsText.setText(newsCompany.getNewsText());
            }

            return convertView;
        }
    };

    private class NewsCompanyListViewHolder {
        ImageView image;
        ImageView dot;
        TextView title;
        TextView newsText;
        TextView time;
    }

    private BaseAdapter studentListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return newsStudentList == null ? 1 : newsStudentList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsStudentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (newsStudentList == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_fragment_noticelist_nullitem, parent, false);
            } else {
                NewsStudentListViewHolder newsStudentListViewHolder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_fragment_noticelistitem, parent, false);
                    newsStudentListViewHolder = new NewsStudentListViewHolder();
                    newsStudentListViewHolder.image = (ImageView) convertView.findViewById(R.id.news_notice_listitem_image);
                    newsStudentListViewHolder.dot = (ImageView) convertView.findViewById(R.id.news_notice_listitem_dot);
                    newsStudentListViewHolder.title = (TextView) convertView.findViewById(R.id.news_notice_listitem_title);
                    newsStudentListViewHolder.newsText = (TextView) convertView.findViewById(R.id.news_notice_listitem_newstext);
                    newsStudentListViewHolder.time = (TextView) convertView.findViewById(R.id.news_notice_listitem_time);
                    convertView.setTag(newsStudentListViewHolder);
                } else {
                    newsStudentListViewHolder = (NewsStudentListViewHolder) convertView.getTag();
                }

                NewsStudent newsStudent = (NewsStudent) getItem(position);

                switch (newsStudent.getDeal()) {
                    case 1:
                        newsStudentListViewHolder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_jobandagent));
                        newsStudentListViewHolder.title.setText("应聘提示");
                        break;
                    case 2:
                        newsStudentListViewHolder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_jobandagent));
                        newsStudentListViewHolder.title.setText("代理人申请");
                        break;
                    case 3:
                        newsStudentListViewHolder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_evaluation));
                        newsStudentListViewHolder.title.setText("评价提示");
                        break;
                }

                if (newsStudent.isRead()) {
                    newsStudentListViewHolder.dot.setVisibility(View.GONE);
                } else {
                    newsStudentListViewHolder.dot.setVisibility(View.VISIBLE);
                }

                newsStudentListViewHolder.time.setText(newsStudent.getCreateDate().toString());
                newsStudentListViewHolder.newsText.setText(newsStudent.getNewsText());
            }

            return convertView;
        }
    };

    private class NewsStudentListViewHolder {
        ImageView image;
        ImageView dot;
        TextView title;
        TextView newsText;
        TextView time;
    }

}
