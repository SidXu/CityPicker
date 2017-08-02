package com.sid.citypicker.adapter;

import android.support.v7.widget.RecyclerView;

import com.sid.citypicker.databinding.AdapterOneTextBinding;
import com.sid.citypicker.picker.OnItemClickListener;

import java.util.List;

/**
 * Created by Sid on 2017/8/1.
 *
 */

public abstract class BaseFilterAdapter extends RecyclerView.Adapter<BaseFilterAdapter.ViewHolder> {

    public abstract void setOnItemClickListener(OnItemClickListener onItemClickListener);

    public abstract void setDefaultItem(String defaultItem);

    public abstract List<Integer> getSelectId();

    class ViewHolder extends RecyclerView.ViewHolder {

        final AdapterOneTextBinding binding;

        ViewHolder(AdapterOneTextBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
