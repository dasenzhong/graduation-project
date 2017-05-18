package bishe.dgut.edu.cn.reallygoodapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.api.Link;
import bishe.dgut.edu.cn.reallygoodapp.bean.Experience;
import bishe.dgut.edu.cn.reallygoodapp.bean.Honor;
import bishe.dgut.edu.cn.reallygoodapp.bean.Job;
import bishe.dgut.edu.cn.reallygoodapp.bean.NewsCompany;
import bishe.dgut.edu.cn.reallygoodapp.bean.Post;
import bishe.dgut.edu.cn.reallygoodapp.bean.Resume;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/18.
 */

public class ApplyDealAgentActivity extends Activity {

    private TextView jobName;
    private TextView jobMoney;

    private TextView resumeInfoName;
    private TextView resumeInfoSex;
    private TextView resumeInfobrithday;
    private TextView resumeInfoAddress;
    private TextView resumeInfoSchool;
    private TextView resumeInfoTelephone;

    private LinearLayout dealLayout;

    private List<Experience> experienceList;
    private List<Honor> honorList;
    private List<Post> postList;

    private int newsId;
    private NewsCompany newsCompany;
    private Resume resume;
    private Job job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applydeal_agent);

        if (getIntent() != null) {
            newsId = getIntent().getIntExtra("newsid", -1);
        }

        jobName = (TextView) findViewById(R.id.applydeal_agent_jobname);
        jobMoney = (TextView) findViewById(R.id.applydeal_agent_jobmoney);

        resumeInfoName = (TextView) findViewById(R.id.applydeal_agent_resumeinfo_name);
        resumeInfoSex = (TextView) findViewById(R.id.applydeal_agent_resumeinfo_sex);
        resumeInfobrithday = (TextView) findViewById(R.id.applydeal_agent_resumeinfo_birthday);
        resumeInfoAddress = (TextView) findViewById(R.id.applydeal_agent_resumeinfo_address);
        resumeInfoSchool = (TextView) findViewById(R.id.applydeal_agent_resumeinfo_school);
        resumeInfoTelephone = (TextView) findViewById(R.id.applydeal_agent_resumeinfo_telephone);

        ListView resumeExperienceList = (ListView) findViewById(R.id.applydeal_agent_resumeexperiencelist);
        resumeExperienceList.setAdapter(resumeExperienceAdapter);
        getListViewHeight(resumeExperienceList);

        ListView resumeHonorList = (ListView) findViewById(R.id.applydeal_agent_resumehonorlist);
        resumeHonorList.setAdapter(resumeHonorAdapter);
        getListViewHeight(resumeHonorList);

        ListView resumePostList = (ListView) findViewById(R.id.applydeal_agent_resumepostlist);
        resumePostList.setAdapter(resumePostAdapter);
        getListViewHeight(resumePostList);

        //返回
        ImageView back = (ImageView) findViewById(R.id.applydeal_agent_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dealLayout = (LinearLayout) findViewById(R.id.applydeal_agent_dealLayout);

        //录用
        FrameLayout passLayout = (FrameLayout) findViewById(R.id.applydeal_agent_pass);
        passLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passSendToServer();
            }
        });

        FrameLayout unpassLayout = (FrameLayout) findViewById(R.id.applydeal_agent_unpass);
        unpassLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unpassSendToServer();
            }
        });

    }

    private void passSendToServer() {
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("studentUserId", String.valueOf(resume.getStudentUser().getId()))
                .addFormDataPart("jobId", String.valueOf(job.getId()))
                .addFormDataPart("newscompanyId", String.valueOf(newsCompany.getId()))
                .build();

        //稍等进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍等");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Link.getClient().newCall(
                Link.getRequestAddress("/agentapplypass").post(body).build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        new AlertDialog.Builder(ApplyDealAgentActivity.this)
                                .setMessage(e.getMessage())
                                .setTitle("请求失败")
                                .setNegativeButton("好", null).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //从服务器接受到数据
                final String responseString = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        try {
                            new AlertDialog.Builder(ApplyDealAgentActivity.this)
                                    .setTitle("请求成功")
                                    .setMessage(responseString)
                                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        } catch (Exception e) {
                            new AlertDialog.Builder(ApplyDealAgentActivity.this)
                                    .setMessage(e.getMessage())
                                    .setTitle("回应失败")
                                    .setNegativeButton("好", null).show();
                        }
                    }
                });
            }
        });
    }

    private void unpassSendToServer() {
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("studentUserId", String.valueOf(resume.getStudentUser().getId()))
                .addFormDataPart("jobId", String.valueOf(job.getId()))
                .addFormDataPart("newscompanyId", String.valueOf(newsCompany.getId()))
                .build();

        //稍等进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍等");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Link.getClient().newCall(
                Link.getRequestAddress("/agentapplyunpass").post(body).build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        new AlertDialog.Builder(ApplyDealAgentActivity.this)
                                .setMessage(e.getMessage())
                                .setTitle("请求失败")
                                .setNegativeButton("好", null).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //从服务器接受到数据
                final String responseString = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        try {
                            new AlertDialog.Builder(ApplyDealAgentActivity.this)
                                    .setTitle("请求成功")
                                    .setMessage(responseString)
                                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                        } catch (Exception e) {
                            new AlertDialog.Builder(ApplyDealAgentActivity.this)
                                    .setMessage(e.getMessage())
                                    .setTitle("回应失败")
                                    .setNegativeButton("好", null).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNews();
    }

    private void loadNews() {
        Link.getClient().newCall(
                Link.getRequestAddress("/getcompanynewsbyid/" + newsId).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("getnewsbyid--failure", e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    newsCompany = new ObjectMapper().readValue(response.body().string(), NewsCompany.class);
                    if (newsCompany != null) {
                        resume = newsCompany.getResume();
                        job = newsCompany.getJob();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                jobName.setText(job.getJobName());
                                jobMoney.setText(job.getMoney());

                                resumeInfoName.setText(resume.getName());
                                resumeInfoSex.setText(resume.getStudentUser().getSex());
                                resumeInfobrithday.setText(resume.getBirthday());
                                if (resume.getLiveTown() != null) {
                                    resumeInfoAddress.setText(resume.getLiveTown());
                                } else {
                                    if (resume.getLiveCity() != null) {
                                        resumeInfoAddress.setText(resume.getLiveCity());
                                    } else {
                                        resumeInfoAddress.setText(resume.getLiveProvince());
                                    }
                                }
                                resumeInfoSchool.setText(resume.getSchool());
                                resumeInfoTelephone.setText(resume.getTelephone());

                                if (newsCompany.isdeal()) {
                                    dealLayout.setVisibility(View.GONE);
                                } else {
                                    dealLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                loadExperience(resume.getId());
                            }
                        }).start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                loadHonor(resume.getId());
                            }
                        }).start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                loadPost(resume.getId());
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    Log.d("addnewsbyid--failure", e.getMessage());
                }
            }
        });
    }

    private void loadPost(int resumeId) {
        Link.getClient().newCall(
                Link.getRequestAddress("/getpostbyresumeid/"+ resumeId).get().build()
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
                    final List<Post> postListGet = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Post>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postList = postListGet;

                            resumePostAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addpost--failure", e.getMessage());
                }
            }
        });
    }

    private void loadHonor(int resumeId) {
        Link.getClient().newCall(
                Link.getRequestAddress("/gethonorbyresumeid/" + resumeId).get().build()
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
                    final List<Honor> honorListGet = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Honor>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            honorList = honorListGet;

                            resumeHonorAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.d("addhonor--failure", e.getMessage());
                }
            }
        });
    }

    private void loadExperience(int resumeId) {
        Link.getClient().newCall(
                Link.getRequestAddress("/getexperiencebyresumeid/" + resumeId).get().build()
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
                    final List<Experience> experienceListGet = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Experience>>() {
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            experienceList = experienceListGet;

                            resumeExperienceAdapter.notifyDataSetChanged();
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
    private BaseAdapter resumeExperienceAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (experienceList == null ? 0 : experienceList.size());
        }

        @Override
        public Object getItem(int position) {
            return experienceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (experienceList != null) {

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

                return convertView;
            } else {
                return null;
            }
        }
    };

    /**
     * 工作经验容器
     */
    private class ExperienceViewHolder {
        TextView time;
        TextView company;
        TextView post;
    }

    private BaseAdapter resumeHonorAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (honorList == null ? 0 : honorList.size());
        }

        @Override
        public Object getItem(int position) {
            return honorList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (honorList != null) {

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

                return convertView;
            } else {
                return null;
            }
        }
    };

    /**
     * 校内荣誉容器
     */
    private class HonorViewHolder {
        TextView time;
        TextView honorName;
    }

    private BaseAdapter resumePostAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (postList == null ? 0 : postList.size());
        }

        @Override
        public Object getItem(int position) {
            return postList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (postList != null) {
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


                return convertView;
            } else {
                return null;
            }
        }
    };

    /**
     * 校内职务容器
     */
    private class PostViewHolder {
        TextView time;
        TextView name;
    }

    /**
     * 重新计量listview的高度
     *
     * @param listView 要测量的listview
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
