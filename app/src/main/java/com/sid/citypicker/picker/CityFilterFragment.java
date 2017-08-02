package com.sid.citypicker.picker;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sid.citypicker.R;
import com.sid.citypicker.adapter.FilterAdapter;
import com.sid.citypicker.bean.FilterBean;
import com.sid.citypicker.config.AppConfig;
import com.sid.citypicker.databinding.FragmentCityFilterBinding;
import com.sid.citypicker.util.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sid on 2017/8/1.
 *
 */
public class CityFilterFragment extends Fragment {

    private static final String TAG = "CityFilterFragment";
    private static final int COLUMN_NUM = 4;

    public GridLayoutManager layoutManager;
    public FilterAdapter adapter;
    private List<FilterBean> filterBean = new ArrayList<>();
    private boolean isShowUnlimited = true;
    private OnDataSelectedListener onDataSelectedListener;
    public RecyclerView.ItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCityFilterBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_filter, container, false);
        binding.setFragment(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        initData();
    }

    private void initData() {
        decoration = new DividerGridItemDecoration(getContext(), COLUMN_NUM);
        layoutManager = new GridLayoutManager(getContext(), COLUMN_NUM);
        adapter = new FilterAdapter(getContext(), filterBean);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position >= filterBean.size()) {
                    return;
                }

                if (onDataSelectedListener != null) {
                    onDataSelectedListener.onData(filterBean.get(position));
                }
            }
        });
    }

    public void setDate(List<FilterBean> data, String defaultItem) {
        Log.d(TAG, "setDate");
        if (data == null) {
            return;
        }
        if (isShowUnlimited) {
            data.add(0, new FilterBean("-1", AppConfig.UNLIMITED));
        }
        adapter.setDefaultItem(defaultItem);
        adapter.notifyData(data);
    }

    public void setShowUnlimited(boolean showUnlimited) {
        isShowUnlimited = showUnlimited;
    }

    public void setOnDataSelectedListener(OnDataSelectedListener onDataSelectedListener) {
        this.onDataSelectedListener = onDataSelectedListener;
    }

    public interface OnDataSelectedListener {
        void onData(FilterBean bean);
    }
}
