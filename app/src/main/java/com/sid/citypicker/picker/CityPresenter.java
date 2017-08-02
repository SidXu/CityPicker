package com.sid.citypicker.picker;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sid.citypicker.bean.FilterBean;
import com.sid.citypicker.util.CityDBUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Sid on 2017/8/1.
 *
 */
public class CityPresenter {

    private static final String TAG = "CityPresenter";
    private CityViewInterface pointInterface;
    private CityDBUtil cityDBUtil;

    private List<FilterBean> provinceData;

    public CityPresenter(@NonNull Context context, @NonNull CityViewInterface pointInterface) {
        this.pointInterface = pointInterface;
        cityDBUtil = new CityDBUtil(context);
    }

    private Observable<List<FilterBean>> getCityObserver(final String id) {
        Log.d(TAG, "getCityObserver: id=" + id);

        return Observable.create(new ObservableOnSubscribe<List<FilterBean>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<FilterBean>> e) throws Exception {
                if (cityDBUtil == null) return;

                List<FilterBean> data = new ArrayList<>();
                cityDBUtil.open();
                try {
                    Cursor cursor = cityDBUtil.selectByParentId(id);

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            data.add(new FilterBean(cursor.getString(0), cursor.getString(1)));
                        } while (cursor.moveToNext());
                        cursor.close();
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "getCityObserver: t=" + t.getMessage());
                } finally {
                    cityDBUtil.close();
                }
                e.onNext(data);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void getProvinceData() {
        if (provinceData != null && provinceData.size() > 0) {
            if (pointInterface != null) {
                pointInterface.showProvinceData(provinceData);
            }
        } else {
            getCityObserver(CityDBUtil.PROVINCE_ID).subscribe(new Consumer<List<FilterBean>>() {
                @Override
                public void accept(List<FilterBean> filterBeen) throws Exception {
                    if (filterBeen != null) {
                        provinceData = new ArrayList<>();
                        provinceData.addAll(filterBeen);
                    }

                    if (pointInterface != null) {
                        pointInterface.showProvinceData(provinceData);
                    }
                }
            });
        }
    }

    public void getCityData(FilterBean province) {
        if (province == null) return;

        getCityObserver(province.getId())
                .subscribe(new Consumer<List<FilterBean>>() {
                    @Override
                    public void accept(List<FilterBean> filterBeen) throws Exception {
                        if (pointInterface != null) {
                            pointInterface.showCityData(filterBeen);
                        }
                    }
                });
    }

    public void getAreaData(FilterBean city) {
        if (city == null) {
            return;
        }

        getCityObserver(city.getId())
                .subscribe(new Consumer<List<FilterBean>>() {
                    @Override
                    public void accept(List<FilterBean> filterBeen) throws Exception {
                        if (pointInterface != null) {
                            pointInterface.showAreaData(filterBeen);
                        }
                    }
                });
    }
}
