package com.sid.citypicker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sid.citypicker.R;
import com.sid.citypicker.bean.FilterBean;
import com.sid.citypicker.databinding.AdapterOneTextBinding;
import com.sid.citypicker.picker.OnItemClickListener;
import com.sid.citypicker.util.StringUtil;

import java.util.List;

/**
 * Created by dell on 2017/8/1.
 *
 */
public class FilterAdapter extends BaseFilterAdapter {

    private static final String TAG = "FilterAdapter";
    private LayoutInflater inflater;
    private List<FilterBean> data;
    private OnItemClickListener onItemClickListener;
    private int lastPosition = -1;
    private String defaultItem = "";

    public FilterAdapter(@NonNull Context context, List<FilterBean> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void notifyData(List<FilterBean> data) {
        Log.d(TAG, "notifyData");
        if (data == null) {
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();

        lastPosition = -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");

        final AdapterOneTextBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_one_text, null, false);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }

                if (position != lastPosition) {
                    FilterBean selectBean = data.get(position);
                    if (selectBean != null) {
                        selectBean.isSelected.set(true);
                    }
                    if (lastPosition >= 0) {
                        FilterBean lastBean = data.get(lastPosition);
                        if (lastBean != null) {
                            lastBean.isSelected.set(false);
                        }
                    }
                    lastPosition = position;
                }
            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");

        holder.binding.getRoot().setTag(position);
        FilterBean bean = data.get(position);
        if (bean != null) {
            holder.binding.setBean(bean);
            if (!StringUtil.emptyString(defaultItem) && defaultItem.equals(bean.getName())) {
                bean.isSelected.set(true);
                lastPosition = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDefaultItem(String defaultItem) {
        this.defaultItem = defaultItem;
    }

    @Override
    public List<Integer> getSelectId() {
        return null;
    }
}
