package com.sid.citypicker.bean;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.sid.citypicker.R;


/**
 * Created by Sid on 2017/8/1.
 *
 */
public class TitleBean {

    public ObservableInt backIcon = new ObservableInt(R.mipmap.icon_left);
    public ObservableField<String> backString = new ObservableField<>("返回");
    public ObservableBoolean isBackVisible = new ObservableBoolean(true);

    public ObservableField<String> titleString = new ObservableField<>();
    public ObservableBoolean isTitleVisible = new ObservableBoolean(true);

    public ObservableInt rightIcon = new ObservableInt();
    public ObservableBoolean isRightVisible = new ObservableBoolean(false);

    public ObservableInt rightSecIcon = new ObservableInt();
    public ObservableBoolean isRightSecVisible = new ObservableBoolean(false);

    public ObservableField<String> rightTextString = new ObservableField<String>();
    public ObservableBoolean isRightTextVisible = new ObservableBoolean(false);

    public ObservableBoolean isRedPointVisible = new ObservableBoolean(false);

    public ObservableInt backgroundColor = new ObservableInt(R.color.background_blue);

}
