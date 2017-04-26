package bishe.dgut.edu.cn.reallygoodapp.module.chooseplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;

/**
 * ZDX
 * Created by Administrator.
 * Created on 2017/4/12.
 */

public class ChoosePlaceActivity extends Activity {

    private ProvinceFragment provinceFragment;
    private CityFragment cityFragment;
    private TownFragment townFragment;

    private String provinceNameGet;
    private String cityNameGet;
    private String townNameGet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_chooseplace);

        //意外中断处理
        if (savedInstanceState != null) {
            provinceFragment = (ProvinceFragment) getFragmentManager().findFragmentByTag("province");
            cityFragment = (CityFragment) getFragmentManager().findFragmentByTag("city");
            townFragment = (TownFragment) getFragmentManager().findFragmentByTag("town");
            provinceNameGet = savedInstanceState.getString("provinceNameGet");
            cityNameGet = savedInstanceState.getString("cityNameGet");
            townNameGet = savedInstanceState.getString("townNameGet");
        } else {
            provinceFragment = new ProvinceFragment();
            cityFragment = new CityFragment();
            townFragment = new TownFragment();
            provinceNameGet = "";
            cityNameGet = "";
            townNameGet = "";
        }

        //返回键
        ImageView back = (ImageView) findViewById(R.id.chooseplaceactivity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (townFragment.isAdded()) {
                    getFragmentManager().beginTransaction().remove(townFragment).commit();
                } else {
                    if (cityFragment.isAdded()) {
                        getFragmentManager().beginTransaction().remove(cityFragment).commit();
                    } else {
                        finish();
                    }
                }
            }
        });

        //标题
        final TextView title = (TextView) findViewById(R.id.chooseplaceactivity_title);


        //设置省份fragment的点击事件回调
        provinceFragment.setOnListItemClickListener(new ProvinceFragment.OnListItemClickListener() {

            @Override
            public void onListItemClick(String provinceName) {
                provinceNameGet = provinceName;
                int id = getCityResIdByProvinceName(provinceName);
                if (id == -1) {
                    sendResult();
                } else {
                    cityFragment.setResId(getCityResIdByProvinceName(provinceName));
                    showCityFragment();
                    title.setText(provinceName);
                }
            }
        });

        //设置城区fragment的点击事件回调
        cityFragment.setOnListItemClickListener(new CityFragment.OnListItemClickListener() {
            @Override
            public void onListItemClick(String cityName) {
                cityNameGet = cityName;
                int id = getTownResIdByCityName(cityName);
                if (id == -1) {
                    sendResult();
//                if (cityName.equals(getResources().getStringArray(R.array.parttimejob_unable)[0])) {
////                    Toast toast = Toast.makeText(ChoosePlaceActivity.this, "暂不支持该区", Toast.LENGTH_SHORT);
////                    toast.setGravity(Gravity.CENTER, 0, 0);
////                    toast.show();
                } else {
                    townFragment.setResId(id);
                    showTownFragment();
                    title.setText(provinceNameGet + "  " + cityName);
                }
            }
        });

        //设置城镇fragmen的点击事件回调
        townFragment.setOnListItemClickListener(new TownFragment.OnListItemClickListener()

        {
            @Override
            public void onListItemClick(String townName) {
                townNameGet = townName;
//                Intent intent = new Intent();
//                intent.putExtra("province", provinceNameGet);
//                intent.putExtra("city", cityNameGet);
//                intent.putExtra("town", townNameGet);
//                setResult(getResources().getInteger(R.integer.CHOOSEPLACE_RESULTCODE), intent);
                sendResult();
            }
        });

        showProvinceFragment();
    }

    /**
     * 返回对应省份的城市资源
     *
     * @param provinceName 省份名
     * @return 城市资源
     */

    private int getCityResIdByProvinceName(String provinceName) {
        int resId;
        switch (provinceName) {
            case "广东省":
                resId = R.array.citylist_guangdong;
                break;
            default:
//                resId = R.array.parttimejob_unable;
                resId = -1;
        }
        return resId;
    }

    /**
     * 返回对应城市的城镇名
     *
     * @param cityName 城市名
     * @return 城镇资源
     */
    private int getTownResIdByCityName(String cityName) {
        int resId;
        switch (provinceNameGet) {
            case "广东省":
                switch (cityName) {
                    case "东莞":
                        resId = R.array.townlist_dongguan;
                        break;
                    case "广州":
                        resId = R.array.townlist_guangzhou;
                        break;
                    case "深圳":
                        resId = R.array.townlist_shenzhen;
                        break;
                    case "珠海":
                        resId = R.array.townlist_zhuhai;
                        break;
                    case "佛山":
                        resId = R.array.townlist_foshan;
                        break;
                    default:
//                        resId = R.array.parttimejob_all;
                        resId = -1;
                }
                break;
            default:
//                resId = R.array.parttimejob_unable;
                resId = -1;
        }
        return resId;
    }

    private void sendResult() {
        Intent intent = new Intent();
        intent.putExtra("province", provinceNameGet);
        if (cityNameGet != null && !cityNameGet.isEmpty()) {
            intent.putExtra("city", cityNameGet);
        }
        if (townNameGet != null && !townNameGet.isEmpty()) {
            intent.putExtra("town", townNameGet);
        }
        setResult(getResources().getInteger(R.integer.CHOOSEPLACE_RESULTCODE), intent);
        finish();
    }

    /**
     * 显示省份fragment
     */
    private void showProvinceFragment() {
        if (provinceFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(provinceFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.chooseplaceactivity_container, provinceFragment, "province").commit();
        }
    }

    /**
     * 显示城市fragment
     */
    private void showCityFragment() {
        if (cityFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(cityFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.chooseplaceactivity_container, cityFragment, "city").commit();
        }
    }

    /**
     * 显示镇区fragment
     */
    private void showTownFragment() {
        if (townFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(townFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.chooseplaceactivity_container, townFragment, "town").commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("provinceNameGet", provinceNameGet);
        outState.putString("cityNameGet", cityNameGet);
        outState.putString("townNameGet", townNameGet);
        super.onSaveInstanceState(outState);
    }
}
