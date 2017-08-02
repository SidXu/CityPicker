package com.sid.citypicker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.sid.citypicker.base.BaseActivity;
import com.sid.citypicker.bean.CityFilterBean;
import com.sid.citypicker.bean.CitySelectBean;
import com.sid.citypicker.databinding.ActivityMainBinding;
import com.sid.citypicker.picker.CityFilterActivity;
import com.sid.citypicker.util.AppUtil;

public class MainActivity extends BaseActivity {

    private static final int CODE_PICKER = 0;

    private CityFilterBean cityFilterBean;
    public ObservableField<CityFilterBean> beanField = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        titleBean.isBackVisible.set(false);
        titleBean.titleString.set("首页");
    }

    public void pickerCity(View view) {
        Intent intent = new Intent(this, CityFilterActivity.class);
        CitySelectBean selectBean = new CitySelectBean();
        selectBean.setTitle("选择城市");
        selectBean.setShowUnlimitedInProvince(false);
        selectBean.setShowUnlimitedInCity(false);
        selectBean.setShowUnlimitedInArea(true);
        selectBean.setShowCurrentLevel(true);
        selectBean.setOldData(cityFilterBean);
        selectBean.setCityLevel(CitySelectBean.CityLevel.THREE_LEVEL);
        intent.putExtra(CityFilterActivity.DATA_KEY, selectBean);
        startActivityForResult(intent, CODE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CODE_PICKER && data != null) {
            cityFilterBean = AppUtil.getFilterBean(data.getExtras());
            beanField.set(cityFilterBean);
        }
    }
}
