package com.sid.citypicker.picker;


import com.sid.citypicker.bean.FilterBean;

import java.util.List;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public interface CityViewInterface {

    void showProvinceData(List<FilterBean> data);

    void showCityData(List<FilterBean> data);

    void showAreaData(List<FilterBean> data);
}
