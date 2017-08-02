package com.sid.citypicker.bean;

import java.io.Serializable;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public class CityFilterBean implements Serializable {

    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String area;
    private String areaCode;
    private String selectName;
    private int selectLevel;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getSelectLevel() {
        return selectLevel;
    }

    public void setSelectLevel(int selectLevel) {
        this.selectLevel = selectLevel;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityFilterBean{");
        sb.append("province='").append(province).append('\'');
        sb.append(", provinceCode='").append(provinceCode).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", cityCode='").append(cityCode).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", areaCode='").append(areaCode).append('\'');
        sb.append(", selectName='").append(selectName).append('\'');
        sb.append(", selectLevel=").append(selectLevel);
        sb.append('}');
        return sb.toString();
    }
}
