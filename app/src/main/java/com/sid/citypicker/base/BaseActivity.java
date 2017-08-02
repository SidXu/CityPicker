package com.sid.citypicker.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.sid.citypicker.bean.TitleBean;
import com.sid.citypicker.util.ClickHandler;

/**
 * Created by Sid on 2017/8/1.
 *
 */

public class BaseActivity extends FragmentActivity implements ClickHandler {

    public TitleBean titleBean = new TitleBean();

    @Override
    public void onBackClick(View view) {
        finish();
    }
}
