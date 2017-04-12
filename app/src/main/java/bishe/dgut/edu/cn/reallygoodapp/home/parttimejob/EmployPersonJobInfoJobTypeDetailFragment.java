package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/11.
 */

public class EmployPersonJobInfoJobTypeDetailFragment extends Fragment {

    private View view;
    private List<String> detailStringList;

    private int resId;

    private int clickPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_info_jobtypechoose_detail, container, false);

            if (savedInstanceState != null) {
                resId = savedInstanceState.getInt("resId");
                clickPosition = savedInstanceState.getInt("clickPosition");
            } else {
                clickPosition = 0;
            }


            ListView detailList = (ListView) view.findViewById(R.id.employperson_jobtypeactvity_choosefragment_detaillist);
            detailList.setAdapter(detailListAdapter);
            detailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickPosition = position;
//                    Log.d("clicktext----", detailStringList.get(position));
//                    Log.d("position----", "" + clickPosition);
                    detailListAdapter.notifyDataSetChanged();
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        detailStringList = Arrays.asList(getResources().getStringArray(resId));
        detailListAdapter.notifyDataSetChanged();
    }

    /**
     * detailList列表适配器
     */
    private BaseAdapter detailListAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (detailStringList == null ? 0 : detailStringList.size());
        }

        @Override
        public Object getItem(int position) {
            return detailStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DetailViewHolder detailViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_employperson_info_jobtypechoose_detail_listitem, parent, false);
                detailViewHolder = new DetailViewHolder();
                detailViewHolder.jobName = (TextView) convertView.findViewById(R.id.employperson_jobtypeactvity_choosefragment_detaillist_item_jobname);
                convertView.setTag(detailViewHolder);
            } else {
                detailViewHolder = (DetailViewHolder) convertView.getTag();
            }

            detailViewHolder.jobName.setText(getItem(position).toString());

            if (position == clickPosition) {
                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.gray_30));
//                Log.d("setblack---", "true");
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.white));
//                Log.d("setblack---", "false");
            }


            return convertView;
        }
    };

    /**
     * 子项容器
     */
    private class DetailViewHolder {
        private TextView jobName;
    }

    /**
     * @return 获取选择的字段
     */
    public String getChooseText() {
        return detailStringList.get(clickPosition);
    }

    /**
     * @param text 找到对应的列表
     */
    public void setDetailStringList(String text) {
        int resId;
        switch (text) {
            //计算机
            case "计算机硬件":
                resId = R.array.jobtype_industry_computer_yingjian;
                break;
            case "计算机软件":
                resId = R.array.jobtype_industry_computer_ruanjian;
                break;
            case "互联网/电子商务/网游":
                resId = R.array.jobtype_industry_computer_hulianwang;
                break;
            case "网店淘宝":
                resId = R.array.jobtype_industry_computer_taobao;
                break;
            case "IT-管理":
                resId = R.array.jobtype_industry_computer_itguanli;
                break;
            case "IT-产品管理/技术支持":
                resId = R.array.jobtype_industry_computer_itpinguan;
                break;
            case "通信技术开发及应用":
                resId = R.array.jobtype_industry_computer_tongxinjishu;
                break;
            case "电子/电器/半导体/仪器仪表":
                resId = R.array.jobtype_industry_computer_dianzibandaoti;
                break;
            //销售
            case "销售管理":
                resId = R.array.jobtype_industry_sale_guanli;
                break;
            case "销售人员":
                resId = R.array.jobtype_industry_sale_renyuan;
                break;
            case "销售行政及商务":
                resId = R.array.jobtype_industry_sale_xingzheng;
                break;
            case "客服及支持":
                resId = R.array.jobtype_industry_sale_kefu;
                break;
            //会计
            case "财务/审计/税务":
                resId = R.array.jobtype_industry_finance_caiwu;
                break;
            case "金融/证券/期货/投资":
                resId = R.array.jobtype_industry_finance_jinrong;
                break;
            case "银行":
                resId = R.array.jobtype_industry_finance_yinhang;
                break;
            case "保险":
                resId = R.array.jobtype_industry_finance_baoxian;
                break;
            //生产
            case "生产/营运":
                resId = R.array.jobtype_industry_production_shengchan;
                break;
            //公务员
            case "公务员":
                resId = R.array.jobtype_industry_translate_gongwuyuan;
                break;
            case "翻译":
                resId = R.array.jobtype_industry_translate_fanyi;
                break;
            //环保
            case "环保":
                resId = R.array.jobtype_industry_environment_huanbao;
                break;
            case "农/林/牧/渔":
                resId = R.array.jobtype_industry_environment_nonglinmuyu;
                break;
            //兼职
            case "兼职":
                resId = R.array.jobtype_industry_parttimejob_jianzhi;
                break;
            case "储备干部/培训生/实习生":
                resId = R.array.jobtype_industry_parttimejob_shixisheng;
                break;
            case "在校生":
                resId = R.array.jobtype_industry_parttimejob_zaixiaosheng;
                break;
            //宠物
            case "驯兽师/助理驯兽师":
                resId = R.array.jobtype_industry_animal_xunshoushi;
                break;
            case "志愿者/社会工作者":
                resId = R.array.jobtype_industry_animal_zhiyuanzhe;
                break;
            //其他
            case "其他":
                resId = R.array.jobtype_industry_other_qita;
                break;
            default:
                resId = R.array.jobtype_industry_other_qita;
        }
        this.resId = resId;
        clickPosition = 0;
//        Log.d("text---------", text);
//        Log.d("resId--------", "" + this.resId);
//        if (detailStringList != null) {
//            for (int i = 0; i < detailStringList.size(); i++) {
//                Log.d("" + i + "-----", detailStringList.get(i));
//            }
//        }
//        this.detailStringList = Arrays.asList(getResources().getStringArray(resId));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("clickPosition", clickPosition);
        outState.putInt("resId", resId);
        super.onSaveInstanceState(outState);
    }

    //    public void notifyDataChanged() {
//        detailListAdapter.notifyDataSetChanged();
//    }
}
