package bishe.dgut.edu.cn.reallygoodapp.home.parttimejob;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bishe.dgut.edu.cn.reallygoodapp.R;
import bishe.dgut.edu.cn.reallygoodapp.module.chooseplace.ChoosePlaceActivity;

/**
 * Created by Administrator on 2017/4/2.
 */

public class EmployPersonJobAddressFragment extends Fragment {

    private View view;

    private TextView area;
    private TextView workplace;
    private TextView detailworkplace;

    private String provinceNameArea;                //招聘省份
    private String cityNameArea;                    //招聘城市
    private String townNameArea;                    //招聘城镇

    private String provinceNameWorkPlace;           //工作省份
    private String cityNameWorkPlace;               //工作城市
    private String townNameWorkPlace;               //工作城镇

    private String detailWorkPlace;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_employperson_address, container, false);


            //招募地区
            area = (TextView) view.findViewById(R.id.employperson_address_area);
            area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getActivity(), ChoosePlaceActivity.class), getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_AREA));
                }
            });

            //工作地址
            workplace = (TextView) view.findViewById(R.id.employperson_address_workplace);
            workplace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getActivity(), ChoosePlaceActivity.class), getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_WORKPLACE));
                }
            });

            //详细工作地址
            detailworkplace = (TextView) view.findViewById(R.id.employperson_address_detailworkplace);

            RelativeLayout mainLayout = (RelativeLayout) view.findViewById(R.id.employperson_address_mainlayout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });

            if (savedInstanceState != null) {
                provinceNameArea = savedInstanceState.getString("provinceNameArea", "");
                cityNameArea = savedInstanceState.getString("cityNameArea", "");
                townNameArea = savedInstanceState.getString("townNameArea", "");
                provinceNameWorkPlace = savedInstanceState.getString("provinceNameWorkPlace", "");
                cityNameWorkPlace = savedInstanceState.getString("cityNameWorkPlace", "");
                townNameWorkPlace = savedInstanceState.getString("townNameWorkPlace", "");
                detailWorkPlace = savedInstanceState.getString("detailWorkPlace", "");

//                Log.d("s--pa:", provinceNameArea);
//                Log.d("s--ca:", cityNameArea);
//                Log.d("s--ta:", townNameArea);
//                Log.d("s--pw:", provinceNameWorkPlace);
//                Log.d("s--cw:", cityNameWorkPlace);
//                Log.d("s--tw:", townNameWorkPlace);

                if (!provinceNameArea.isEmpty() && !cityNameArea.isEmpty() && !townNameArea.isEmpty()) {
                    area.setText(provinceNameArea + "  " + cityNameArea + "  " + townNameArea);
                }
                if (!provinceNameWorkPlace.isEmpty() && !cityNameWorkPlace.isEmpty() && !townNameWorkPlace.isEmpty()) {
                    workplace.setText(provinceNameWorkPlace + "  " + cityNameWorkPlace + "  " + townNameWorkPlace);
                }
                if (!detailWorkPlace.isEmpty()) {
                    detailworkplace.setText(detailWorkPlace);
                }
            }

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!provinceNameArea.isEmpty() && !cityNameArea.isEmpty() && !townNameArea.isEmpty()) {
//            area.setText(provinceNameArea + "  " + cityNameArea + "  " + townNameArea);
//        }
//        if (!provinceNameWorkPlace.isEmpty() && !cityNameWorkPlace.isEmpty() && !townNameWorkPlace.isEmpty()) {
//            workplace.setText(provinceNameWorkPlace + "  " + cityNameWorkPlace + "  " + townNameWorkPlace);
//        }
//        if (!detailWorkPlace.isEmpty()) {
//            detailworkplace.setText(detailWorkPlace);
//        }
    }

    public String getProvinceNameArea() {
        return provinceNameArea;
    }

    public String getCityNameArea() {
        return cityNameArea;
    }

    public String getTownNameArea() {
        return townNameArea;
    }

    public String getProvinceNameWorkPlace() {
        return provinceNameWorkPlace;
    }

    public String getCityNameWorkPlace() {
        return cityNameWorkPlace;
    }

    public String getTownNameWorkPlace() {
        return townNameWorkPlace;
    }

    public String getDetailWorkPlaceText() {
        return detailworkplace.getText().toString().trim();
    }

    public String getAreaText() {
        return area.getText().toString().trim();
    }

    public String getWorkPlaceText() {
        return workplace.getText().toString().trim();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getResources().getInteger(R.integer.CHOOSEPLACE_RESULTCODE)) {
            if (requestCode == getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_AREA)) {
                provinceNameArea = data.getStringExtra("province");
                cityNameArea = data.getStringExtra("city");
                townNameArea = data.getStringExtra("town");
                area.setText(provinceNameArea + "  " + cityNameArea + "  " + townNameArea);
            }
            if (requestCode == getResources().getInteger(R.integer.CHOOSEPLACE_REQUESTCODE_WORKPLACE)) {
                provinceNameWorkPlace = data.getStringExtra("province");
                cityNameWorkPlace = data.getStringExtra("city");
                townNameWorkPlace = data.getStringExtra("town");
                workplace.setText(provinceNameWorkPlace + "  " + cityNameWorkPlace + "  " + townNameWorkPlace);
            }

//            Log.d("pa:", provinceNameArea);
//            Log.d("ca:", cityNameArea);
//            Log.d("ta:", townNameArea);
//            Log.d("pw:", provinceNameWorkPlace);
//            Log.d("cw:", cityNameWorkPlace);
//            Log.d("tw:", townNameWorkPlace);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("provinceNameArea", provinceNameArea);
        outState.putString("cityNameArea", cityNameArea);
        outState.putString("townNameArea", townNameArea);
        outState.putString("provinceNameWorkPlace", provinceNameWorkPlace);
        outState.putString("cityNameWorkPlace", cityNameWorkPlace);
        outState.putString("townNameWorkPlace", townNameWorkPlace);
        outState.putString("detailWorkPlace", detailworkplace.getText().toString().trim());
        super.onSaveInstanceState(outState);
    }
}
