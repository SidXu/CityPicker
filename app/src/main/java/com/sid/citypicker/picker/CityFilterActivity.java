package com.sid.citypicker.picker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.sid.citypicker.R;
import com.sid.citypicker.base.BaseActivity;
import com.sid.citypicker.bean.CityFilterBean;
import com.sid.citypicker.bean.CitySelectBean;
import com.sid.citypicker.bean.FilterBean;
import com.sid.citypicker.config.AppConfig;
import com.sid.citypicker.databinding.ActivityCityFilterBinding;
import com.sid.citypicker.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public class CityFilterActivity extends BaseActivity implements CityViewInterface {

    private static final String TAG = "CityFilterActivity";
    public static final String DATA_KEY = "cityData";

    private static final String SELECT_PROVINCE = "选择省份";
    private static final String SELECT_CITY = "选择城市";
    private static final String SELECT_AREA = "选择区县";

    public ObservableField<String> provinceShow = new ObservableField<>(SELECT_PROVINCE);
    public ObservableField<String> cityShow = new ObservableField<>(SELECT_CITY);
    public ObservableField<String> areaShow = new ObservableField<>(SELECT_AREA);
    public ObservableBoolean isCityShow = new ObservableBoolean(false);
    public ObservableBoolean isAreaShow = new ObservableBoolean(false);
    private CityPresenter pointPresenter;
    private FilterBean provinceBean;
    private FilterBean cityBean;
    private FilterBean areaBean;
    private CitySelectBean.CityLevel currentLevel;

    private boolean isShowUnlimitedInProvince = true;
    private boolean isShowUnlimitedInCity = true;
    private boolean isShowUnlimitedInArea = true;
    private CitySelectBean.CityLevel currentCityLevel = CitySelectBean.CityLevel.TWO_LEVEL;
    private CityFilterBean cityFilterBean;
    private boolean isShowCity;
    private boolean isShowArea;
    private boolean isShowCurrent;

    private ViewPager viewPager;
    private List<CityFilterFragment> tabFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCityFilterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_city_filter);
        binding.setActivity(this);

        viewPager = binding.cityFilterPager;

        initData();
        initViewPage();

        pointPresenter = new CityPresenter(this, this);
        pointPresenter.getProvinceData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            CitySelectBean selectBean = null;
            Serializable serializable = bundle.getSerializable(DATA_KEY);
            if (serializable != null) {
                selectBean = (CitySelectBean) serializable;
            }
            if (selectBean == null) {
                selectBean = new CitySelectBean();
            }

            titleBean.titleString.set(selectBean.getTitle());
            isShowUnlimitedInProvince = selectBean.isShowUnlimitedInProvince();
            isShowUnlimitedInCity = selectBean.isShowUnlimitedInCity();
            isShowUnlimitedInArea = selectBean.isShowUnlimitedInArea();
            currentCityLevel = selectBean.getCityLevel();
            cityFilterBean = selectBean.getOldData();
            if (selectBean.isShowCurrentLevel()) {
                isShowCity = true;
                isShowArea = true;
                isShowCurrent = true;
            }
        }

        CityFilterFragment provinceFragment = new CityFilterFragment();
        provinceFragment.setShowUnlimited(isShowUnlimitedInProvince);
        provinceFragment.setOnDataSelectedListener(new CityFilterFragment.OnDataSelectedListener() {
            @Override
            public void onData(FilterBean bean) {
                onProvinceSelected(bean);
            }
        });
        tabFragments.add(provinceFragment);

        if (currentCityLevel.getValue() > CitySelectBean.CityLevel.ONE_LEVEL.getValue()) {
            CityFilterFragment cityFragment = new CityFilterFragment();
            cityFragment.setShowUnlimited(isShowUnlimitedInCity);
            cityFragment.setOnDataSelectedListener(new CityFilterFragment.OnDataSelectedListener() {
                @Override
                public void onData(FilterBean bean) {
                    onCitySelected(bean);
                }
            });
            tabFragments.add(cityFragment);
        }

        if (currentCityLevel.getValue() > CitySelectBean.CityLevel.TWO_LEVEL.getValue()) {
            CityFilterFragment areaFragment = new CityFilterFragment();
            areaFragment.setShowUnlimited(isShowUnlimitedInArea);
            areaFragment.setOnDataSelectedListener(new CityFilterFragment.OnDataSelectedListener() {
                @Override
                public void onData(FilterBean bean) {
                    onAreaSelected(bean);
                }
            });
            tabFragments.add(areaFragment);
        }
    }

    private void initViewPage() {
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabFragments.get(position);
            }

            @Override
            public int getCount() {
                return tabFragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                CityFilterFragment fragment = tabFragments.get(position);
                FragmentManager manager = getSupportFragmentManager();
                if (!fragment.isAdded()) {
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.add(fragment, fragment.getClass().getSimpleName());
                    ft.commit();
                    manager.executePendingTransactions();
                }
                View view = fragment.getView();
                if (view == null) {
                    view = container.getChildAt(position);
                }
                if (view != null && view.getParent() == null) {
                    container.addView(fragment.getView());
                }
                return fragment;
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void onProvinceSelected(FilterBean bean) {
        Log.d(TAG, "onProvinceSelected");
        provinceBean = bean;
        currentLevel = CitySelectBean.CityLevel.ONE_LEVEL;
        if (bean != null) {
            String name = bean.getName();
            if (AppConfig.UNLIMITED.equals(name)) {
                configResult();
            } else {
                if (currentCityLevel.getValue() > CitySelectBean.CityLevel.ONE_LEVEL.getValue()) {
                    if (pointPresenter != null) {
                        pointPresenter.getCityData(bean);
                    }
                } else {
                    configResult();
                }
            }
            provinceShow.set(name);
        }
    }

    private void onCitySelected(FilterBean bean) {
        Log.d(TAG, "onCitySelected");
        cityBean = bean;
        currentLevel = CitySelectBean.CityLevel.TWO_LEVEL;
        if (bean != null) {
            String name = bean.getName();
            if (AppConfig.UNLIMITED.equals(name)) {
                configResult();
            } else {
                if (currentCityLevel.getValue() > CitySelectBean.CityLevel.TWO_LEVEL.getValue()) {

                    if (pointPresenter != null) {
                        pointPresenter.getAreaData(bean);
                    }
                } else {
                    configResult();
                }
            }
            cityShow.set(name);
        }
    }

    private void onAreaSelected(FilterBean bean) {
        Log.d(TAG, "onAreaSelected");
        areaBean = bean;
        currentLevel = CitySelectBean.CityLevel.THREE_LEVEL;
        if (bean != null) {
            areaShow.set(bean.getName());
        }
        configResult();
    }

    @Override
    public void showProvinceData(List<FilterBean> data) {
        Log.d(TAG, "showProvinceData: size=" + data.size());
        CityFilterFragment fragment = tabFragments.get(0);
        if (fragment != null) {
            String defaultItem = "";
            if (isShowCity && cityFilterBean != null) {
                defaultItem = cityFilterBean.getProvince();
                String cityName = cityFilterBean.getCity();
                if (!StringUtil.emptyString(cityName)) {
                    onProvinceSelected(new FilterBean(cityFilterBean.getProvinceCode(), cityFilterBean.getProvince()));
                }
                isShowCity = false;
            }
            fragment.setDate(data, defaultItem);
        }
    }

    @Override
    public void showCityData(List<FilterBean> data) {
        Log.d(TAG, "showCityData: size=" + data.size());
        CityFilterFragment fragment = tabFragments.get(1);
        if (fragment != null) {
            String defaultItem = "";
            if (isShowArea && cityFilterBean != null) {
                defaultItem = cityFilterBean.getCity();
                String areaName = cityFilterBean.getArea();
                if (!StringUtil.emptyString(areaName)) {
                    onCitySelected(new FilterBean(cityFilterBean.getCityCode(), cityFilterBean.getCity()));
                    isShowArea = false;
                }
            }

            fragment.setDate(data, defaultItem);
            cityShow.set(StringUtil.emptyString(defaultItem) ? SELECT_CITY : defaultItem);
            isCityShow.set(true);
            isAreaShow.set(false);
        }
        viewPager.setCurrentItem(1);
    }

    @Override
    public void showAreaData(List<FilterBean> data) {
        Log.d(TAG, "showAreaData: size=" + data.size());
        CityFilterFragment fragment = tabFragments.get(2);
        if (fragment != null) {
            String defaultItem = "";
            if (isShowCurrent && cityFilterBean != null) {
                defaultItem = cityFilterBean.getArea();
                areaShow.set(defaultItem);
            }
            isShowCurrent = false;
            fragment.setDate(data, defaultItem);
            areaShow.set(StringUtil.emptyString(defaultItem) ? SELECT_AREA : defaultItem);
            isAreaShow.set(true);
        }
        viewPager.setCurrentItem(2);
    }

    public void provinceClick(View view) {
        viewPager.setCurrentItem(0);

        isCityShow.set(false);
        isAreaShow.set(false);
    }

    public void cityClick(View view) {
        viewPager.setCurrentItem(1);
        isAreaShow.set(false);
    }

    private void configResult() {
        CityFilterBean filterBean = new CityFilterBean();
        if (provinceBean != null) {
            filterBean.setProvince(provinceBean.getName());
            filterBean.setProvinceCode(provinceBean.getId());
        }

        if (cityBean != null) {
            filterBean.setCity(cityBean.getName());
            filterBean.setCityCode(cityBean.getId());
        }

        if (areaBean != null) {
            filterBean.setArea(areaBean.getName());
            filterBean.setAreaCode(areaBean.getId());
        }

        switch (currentLevel) {
            case ONE_LEVEL:
                if (provinceBean != null) {
                    filterBean.setSelectName(provinceBean.getName());
                }
                break;
            case TWO_LEVEL:
                if (cityBean != null) {
                    if (AppConfig.UNLIMITED.equals(cityBean.getName())) {
                        if (provinceBean != null) {
                            filterBean.setSelectName(provinceBean.getName());
                        }
                    } else {
                        filterBean.setSelectName(cityBean.getName());
                    }
                }
                break;
            case THREE_LEVEL:
                if (areaBean != null) {
                    if (AppConfig.UNLIMITED.equals(areaBean.getName())) {
                        if (cityBean != null) {
                            filterBean.setSelectName(cityBean.getName());
                        }
                    } else {
                        filterBean.setSelectName(areaBean.getName());
                    }
                }
                break;
            default:
                break;
        }
        filterBean.setSelectLevel(currentLevel.getValue());

        Intent intent = new Intent();
        intent.putExtra(DATA_KEY, filterBean);
        setResult(RESULT_OK, intent);
        finish();
    }
}
