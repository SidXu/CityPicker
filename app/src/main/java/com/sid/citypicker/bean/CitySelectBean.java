package com.sid.citypicker.bean;

import java.io.Serializable;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public class CitySelectBean implements Serializable {

    private String title = "选择城市";
    private boolean isShowUnlimitedInProvince = true;
    private boolean isShowUnlimitedInCity = true;
    private boolean isShowUnlimitedInArea = true;
    private boolean isShowCurrentLevel = false;
    private CityLevel cityLevel = CityLevel.TWO_LEVEL;
    private CityFilterBean oldData;

    public enum CityLevel {
        ONE_LEVEL(1),
        TWO_LEVEL(2),
        THREE_LEVEL(3);

        int value;

        CityLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowUnlimitedInProvince() {
        return isShowUnlimitedInProvince;
    }

    public void setShowUnlimitedInProvince(boolean showUnlimitedInProvince) {
        isShowUnlimitedInProvince = showUnlimitedInProvince;
    }

    public boolean isShowUnlimitedInCity() {
        return isShowUnlimitedInCity;
    }

    public void setShowUnlimitedInCity(boolean showUnlimitedInCity) {
        isShowUnlimitedInCity = showUnlimitedInCity;
    }

    public boolean isShowUnlimitedInArea() {
        return isShowUnlimitedInArea;
    }

    public void setShowUnlimitedInArea(boolean showUnlimitedInArea) {
        isShowUnlimitedInArea = showUnlimitedInArea;
    }

    public boolean isShowCurrentLevel() {
        return isShowCurrentLevel;
    }

    public void setShowCurrentLevel(boolean showCurrentLevel) {
        isShowCurrentLevel = showCurrentLevel;
    }

    public CityLevel getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(CityLevel cityLevel) {
        this.cityLevel = cityLevel;
    }

    public CityFilterBean getOldData() {
        return oldData;
    }

    public void setOldData(CityFilterBean oldData) {
        this.oldData = oldData;
    }
}
