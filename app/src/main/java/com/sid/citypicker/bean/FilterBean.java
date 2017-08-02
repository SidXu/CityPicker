package com.sid.citypicker.bean;

import android.databinding.ObservableBoolean;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public class FilterBean {

    public String id;
    public String name;
    public ObservableBoolean isSelected = new ObservableBoolean(false);

    public FilterBean(String name) {
        this.name = name;
    }

    public FilterBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}