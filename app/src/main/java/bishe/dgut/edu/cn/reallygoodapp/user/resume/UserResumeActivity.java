package bishe.dgut.edu.cn.reallygoodapp.user.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.Experience;
import bishe.dgut.edu.cn.reallygoodapp.bean.Honor;
import bishe.dgut.edu.cn.reallygoodapp.bean.Post;
import bishe.dgut.edu.cn.reallygoodapp.bean.Resume;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserResumeActivity extends Activity {

    private TextView name;          //姓名
    private TextView sex;           //性别
    private TextView birthday;           //年龄
    private TextView address;       //居住地
    private TextView school;        //学校
    private TextView telephone;     //电话

    private List<Experience> workExperienceDataList;
    private List<Honor> honorDataList;
    private List<Post> postDataList;

    LinearLayout infoPrefectLayout;         //完善信息栏
    LinearLayout infoNotPrefectLayout;      //未完善提示栏

//    private boolean isPrefect;              //是否完善信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resume);

//        isPrefect = false;

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
        workExperienceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserResumeActivity.this, UserResumeWorkExperienceActivity.class));
            }
        });

        //在校情况栏
        RelativeLayout schoolLayout = (RelativeLayout) findViewById(R.id.userresume_schoolclicklayout);
        schoolLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserResumeActivity.this, UserResumeSchoolActivity.class));
            }
        });

        infoPrefectLayout = (LinearLayout) findViewById(R.id.userresume_infolayout);
        infoNotPrefectLayout = (LinearLayout) findViewById(R.id.userresume_infonulllayout);

        name = (TextView) findViewById(R.id.userresume_info_name);
        sex = (TextView) findViewById(R.id.userresume_info_sex);
        birthday = (TextView) findViewById(R.id.userresume_info_birthday);
        address = (TextView) findViewById(R.id.userresume_info_address);
        school = (TextView) findViewById(R.id.userresume_info_school);
        telephone = (TextView) findViewById(R.id.userresume_info_telephone);

        //工作经验列表
        ListView workExperienceList = (ListView) findViewById(R.id.userresume_workexperiencelist);
        workExperienceList.setAdapter(workExperienceAdapter);
        getListViewHeight(workExperienceList);

        //校内荣誉列表
        ListView honorList = (ListView) findViewById(R.id.userresume_honorlist);
        honorList.setAdapter(honorAdapter);
        getListViewHeight(honorList);

        //校内职务列表
        ListView postList = (ListView) findViewById(R.id.userresume_postlist);
        postList.setAdapter(postAdapter);
        getListViewHeight(postList);

        //返回键
        LinearLayout back = (LinearLayout) findViewById(R.id.userresume_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadInfo();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadExperience();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadHonor();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPost();
            }
        }).start();
    }

    private void loadInfo() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getinfo").get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getinfo--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    final Resume resume = new ObjectMapper().readValue(response.body().string(), Resume.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            name.setText(resume.getName());
                            sex.setText(resume.getStudentUser().getSex());
                            birthday.setText(resume.getBirthday());
                            if (resume.getLiveTown() != null) {
                                address.setText(resume.getLiveTown());
                            } else {
                                if (resume.getLiveCity() != null) {
                                    address.setText(resume.getLiveCity());
                                } else {
                                    address.setText(resume.getLiveProvince());
                                }
                            }
                            school.setText(resume.getSchool());
                            telephone.setText(resume.getTelephone());

                            if (resume.isPrefect()) {
                                infoPrefectLayout.setVisibility(View.VISIBLE);
                                infoNotPrefectLayout.setVisibility(View.GONE);
                            } else {
                                infoNotPrefectLayout.setVisibility(View.VISIBLE);
                                infoPrefectLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d("addinfo--failure", e.getMessage());
                }
            }
        });
    }

    private void loadPost() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getpost").get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getpost--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final List<Post> postList = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Post>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postDataList = postList;

                            postAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addpost--failure", e.getMessage());
                }
            }
        });
    }

    private void loadHonor() {
        Link.getClient().newCall(
                Link.getRequestAddress("/gethonor").get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gethonor--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final List<Honor> honorList = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Honor>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            honorDataList = honorList;

                            honorAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addhonor--failure", e.getMessage());
                }
            }
        });
    }

    private void loadExperience() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getexperience").get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getexperience--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final List<Experience> experienceList = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Experience>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            workExperienceDataList = experienceList;

                            workExperienceAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addexperience--failure", e.getMessage());
                }
            }
        });
    }

    /**
     * 工作经验适配器
     */
    private BaseAdapter workExperienceAdapter = new BaseAdapter() {
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
            } else {
                ExperienceViewHolder experienceViewHolder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_workperiencelist_item, parent, false);
                    experienceViewHolder = new ExperienceViewHolder();
                    experienceViewHolder.time = (TextView) convertView.findViewById(R.id.userresume_workexperiencelist_time);
                    experienceViewHolder.company = (TextView) convertView.findViewById(R.id.userresume_workexperiencelist_companyname);
                    experienceViewHolder.post = (TextView) convertView.findViewById(R.id.userresume_workexperiencelist_companypost);
                    convertView.setTag(experienceViewHolder);
                } else {
                    experienceViewHolder = (ExperienceViewHolder) convertView.getTag();
                }

                Experience experience = (Experience) getItem(position);

                experienceViewHolder.time.setText(experience.getStartTime() + " - " + experience.getEndTime());
                experienceViewHolder.company.setText(experience.getCompanyName());
                experienceViewHolder.post.setText(experience.getCompanyPost());

            }

            return convertView;
        }
    };

    /**
     * 工作经验容器
     */
    private class ExperienceViewHolder{
        TextView time;
        TextView company;
        TextView post;
    }

    /**
     * 在校荣誉适配器
     */
    private BaseAdapter honorAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (honorDataList == null ? 1 : honorDataList.size());
        }

        @Override
        public Object getItem(int position) {
            return honorDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (honorDataList == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_school_honor_nullitem, parent, false);
            } else {

                HonorViewHolder honorViewHolder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_school_honor_item, parent, false);
                    honorViewHolder = new HonorViewHolder();
                    honorViewHolder.time = (TextView) convertView.findViewById(R.id.userresume_honorlist_time);
                    honorViewHolder.honorName = (TextView) convertView.findViewById(R.id.userresume_honorlist_honorname);
                    convertView.setTag(honorViewHolder);
                } else {
                    honorViewHolder = (HonorViewHolder) convertView.getTag();
                }

                Honor honor = (Honor) getItem(position);

                honorViewHolder.time.setText(honor.getTime());
                honorViewHolder.honorName.setText(honor.getHonorName());

            }

            return convertView;
        }
    };

    /**
     * 校内荣誉容器
     */
    private class HonorViewHolder{
        TextView time;
        TextView honorName;
    }

    private BaseAdapter postAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (postDataList == null ? 1 : postDataList.size());
        }

        @Override
        public Object getItem(int position) {
            return postDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (postDataList == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_school_post_nullitem, parent, false);
            } else {

                PostViewHolder postViewHolder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_resume_school_post_item, parent, false);
                    postViewHolder = new PostViewHolder();
                    postViewHolder.time = (TextView) convertView.findViewById(R.id.userresume_postlist_time);
                    postViewHolder.name = (TextView) convertView.findViewById(R.id.userresume_postlist_postname);
                    convertView.setTag(postViewHolder);
                } else {
                    postViewHolder = (PostViewHolder) convertView.getTag();
                }

                Post post = (Post) getItem(position);

                postViewHolder.time.setText(post.getStartTime() + " - " + post.getEndTime());
                postViewHolder.name.setText(post.getPostName());

            }

            return convertView;
        }
    };

    /**
     * 校内职务容器
     */
    private class PostViewHolder{
        TextView time;
        TextView name;
    }

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
