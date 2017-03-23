package bishe.dgut.edu.cn.reallygoodapp.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * Created by Administrator on 2017/3/22.
 */

public class PartTimeJobSwitchPlaceFragment extends Fragment {

    private View view;
//    private ListView provinceListView;
//    private ListView cityListView;
//    private ListView townListView;

    private List<String> provinceList;      //省份列表
    private List<String> cityList;          //城市列表
    private List<String> townList;          //城区列表

//    private View preProvinceSelectView;     //记录前一次选择的省份的listitem
//    private View preCitySelectView;         //记录前一次选择的城市的listitem
//    private View preTownSelectView;         //记录前一次选择的城区的listitem

    private String provinceName = "";            //记录前一次选择的省份
    private String cityName = "";                //记录前一次选择的城市
    private String townName = "";                //记录前一次选择的城区
    private boolean isService = true;

    private int provincePosition;
    private int cityPosition;
    private int townPosition;

    private int selectColor;
    private int unSelectColor;

    interface OnCloseSwitchFragmentListener {
        void onClose();
    }

    private OnCloseSwitchFragmentListener onCloseSwitchFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_paertimrjob_switch_place, container, false);

            final ListView provinceListView = (ListView) view.findViewById(R.id.parttimejob_switchfragment_province);
            final ListView cityListView = (ListView) view.findViewById(R.id.parttimejob_switchfragment_city);
            final ListView townListView = (ListView) view.findViewById(R.id.parttimejob_switchfragment_town);

            selectColor = ContextCompat.getColor(getActivity(), R.color.gray_30);       //选择颜色
            unSelectColor = ContextCompat.getColor(getActivity(), R.color.gray_10);     //为选择颜色

            provinceList = Arrays.asList(getResources().getStringArray(R.array.provincelist));  //初始化省份列表，默认广东省
            provinceName = provinceList.get(0);                 //默认第一项
            provincePosition = 0;

            //省份列表的点击事件
            provinceListView.setAdapter(provinceListViewAdapter);
            provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    provincePosition = position;
                    provinceListViewAdapter.notifyDataSetChanged();
                    if (!view.isSelected()) {
                        view.setSelected(true);
                        initCityList(provinceList.get(position));
                        cityListViewAdapter.notifyDataSetChanged();
                        townListViewAdapter.notifyDataSetChanged();
                        if (isService) {
                            provinceName = provinceList.get(position);
                        }
//                        Toast.makeText(getActivity(), "省份:" + provinceName + "\n城市:" + cityName + "\n城区:" + townName, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            initCityList(provinceName);
            cityName = cityList.get(0);                         //默认第一项
            cityPosition = 0;

            //城市列表点击事件
            cityListView.setAdapter(cityListViewAdapter);
            cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!view.isSelected() && isService) {
                        cityPosition = position;
                        cityListViewAdapter.notifyDataSetChanged();
                        view.setSelected(true);
                        cityName = cityList.get(position);
                        initTownList(cityName);
                        townListViewAdapter.notifyDataSetChanged();
//                        Toast.makeText(getActivity(), "省份:" + provinceName + "\n城市:" + cityName + "\n城区:" + townName, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            initTownList(cityName);
            townName = townList.get(0);
            townPosition = 0;

            //城区列表点击事件
            townListView.setAdapter(townListViewAdapter);
            townListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!view.isSelected() && isService) {
                        townPosition = position;
                        townListViewAdapter.notifyDataSetChanged();
                        view.setSelected(true);
                        townName = townList.get(position);
//                        Toast.makeText(getActivity(), "省份:" + provinceName + "\n城市:" + cityName + "\n城区:" + townName, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            LinearLayout closeLayout = (LinearLayout) view.findViewById(R.id.parttimejob_switchfragment_layout);
            closeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeLayout();
                }
            });

        }

        return view;
    }


    /**
     * 根据省份名更新城市列表
     *
     * @param provinceName 省份名
     */
    private void initCityList(String provinceName) {
        isService = true;                   //重置该区可以服务
        cityPosition = 0;
        townPosition = 0;
        switch (provinceName) {
            case "广东省":
                cityList = Arrays.asList(getResources().getStringArray(R.array.citylist_guangdong));
                townList = Arrays.asList(getResources().getStringArray(R.array.parttimejob_all));
                break;
            default:
                isService = false;          //不支持该省服务标志
                cityList = Arrays.asList(getResources().getStringArray(R.array.parttimejob_unable));
                townList = Arrays.asList(getResources().getStringArray(R.array.parttimejob_unable));
//                townListViewAdapter.notifyDataSetChanged();
        }
//        cityListViewAdapter.notifyDataSetChanged();
//        townListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 根据省份名和城市名，更新城区列表
     *
     * @param cityName 城市名
     */
    private void initTownList(String cityName) {
        townPosition = 0;
        switch (provinceName) {
            case "广东省":
//                cityList = Arrays.asList(getResources().getStringArray(R.array.citylist_guangdong));
                switch (cityName) {
                    case "东莞":
                        townList = Arrays.asList(getResources().getStringArray(R.array.townlist_dongguan));
                        break;
                    case "广州":
                        townList = Arrays.asList(getResources().getStringArray(R.array.townlist_guangzhou));
                        break;
                    case "深圳":
                        townList = Arrays.asList(getResources().getStringArray(R.array.townlist_shenzhen));
                        break;
                    case "珠海":
                        townList = Arrays.asList(getResources().getStringArray(R.array.townlist_zhuhai));
                        break;
                    case "佛山":
                        townList = Arrays.asList(getResources().getStringArray(R.array.townlist_foshan));
                        break;
                    default:
                        townList = Arrays.asList(getResources().getStringArray(R.array.parttimejob_all));
                }
                break;

            default:
//                townList = Arrays.asList(getResources().getStringArray(R.array.parttimejob_unable));
        }
//        townListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 点击空白地方关闭fragment，回调方法
     */
    private void closeLayout() {
        if (onCloseSwitchFragmentListener != null) {
            onCloseSwitchFragmentListener.onClose();
        }
    }

    /**
     * 实例化接口
     *
     * @param onCloseSwitchFragmentListener 自定义接口，监听空白处点击事件
     */
    public void setOnCloseSwitchFragmentListener(OnCloseSwitchFragmentListener onCloseSwitchFragmentListener) {
        this.onCloseSwitchFragmentListener = onCloseSwitchFragmentListener;
    }

    private final class ProvinceViewHolder {
        private TextView provinceName;
    }

    private final class CityViewHolder {
        private TextView cityName;
    }

    private final class TownViewHolder {
        private TextView townName;
    }


    private BaseAdapter provinceListViewAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return (provinceList == null ? 0 : provinceList.size());
        }

        @Override
        public Object getItem(int position) {
            return provinceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProvinceViewHolder provinceViewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_parttimejob_switch_place_listitem_province, parent, false);
                provinceViewHolder = new ProvinceViewHolder();
                provinceViewHolder.provinceName = (TextView) convertView.findViewById(R.id.parttimejob_switchfragment_province_text);
                convertView.setTag(provinceViewHolder);
            } else {
                provinceViewHolder = (ProvinceViewHolder) convertView.getTag();
            }

            provinceViewHolder.provinceName.setText(getItem(position).toString());

            if (position == provincePosition) {
                convertView.setBackgroundColor(selectColor);
            } else {
                convertView.setBackgroundColor(unSelectColor);
            }

            return convertView;
        }


    };

    private BaseAdapter cityListViewAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (cityList == null ? 0 : cityList.size());
        }

        @Override
        public Object getItem(int position) {
            return cityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CityViewHolder cityViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_parttimejob_switch_place_listitem_city, parent, false);
                cityViewHolder = new CityViewHolder();
                cityViewHolder.cityName = (TextView) convertView.findViewById(R.id.parttimejob_switchfragment_city_text);
                convertView.setTag(cityViewHolder);
            } else {
                cityViewHolder = (CityViewHolder) convertView.getTag();
            }

            cityViewHolder.cityName.setText(getItem(position).toString());

            if (position == cityPosition && isService) {
                convertView.setBackgroundColor(selectColor);
            } else {
                convertView.setBackgroundColor(unSelectColor);
            }

            return convertView;
        }
    };

    private BaseAdapter townListViewAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (townList == null ? 0 : townList.size());
        }

        @Override
        public Object getItem(int position) {
            return townList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TownViewHolder townViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_parttimejob_switch_place_listitem_town, parent, false);
                townViewHolder = new TownViewHolder();
                townViewHolder.townName = (TextView) convertView.findViewById(R.id.parttimejob_switchfragment_town_text);
                convertView.setTag(townViewHolder);
            } else {
                townViewHolder = (TownViewHolder) convertView.getTag();
            }

            townViewHolder.townName.setText(getItem(position).toString());

            if (position == townPosition && isService) {
                convertView.setBackgroundColor(selectColor);
            } else {
                convertView.setBackgroundColor(unSelectColor);
            }

            return convertView;
        }
    };
}
