package com.danazone.autosharesms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PhoneAdapter extends BaseAdapter {
    public interface OnHistoryClickListener {
        void onClickItem(int position);
    }

    private List<Phone> mList;
    private OnHistoryClickListener mListener;

    protected PhoneAdapter(@NonNull Context context, List<Phone> list, OnHistoryClickListener listener) {
        super(context);
        this.mList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item_add, parent, false);
        return new ViewHolderItem(view);
    }

    /**
     * onBindHolder Item
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolderItem(ViewHolderItem holder, int position) {
        Phone mRun = mList.get(position);
        holder.mTvName.setText(mRun.getName());
        holder.mTvPhone.setText(mRun.getPhone());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolderItem((ViewHolderItem) holder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * ViewHolderItem
     */
    private class ViewHolderItem extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvPhone;
        private TextView mTvDelete;

        public ViewHolderItem(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.mTvName);
            mTvPhone = (TextView) view.findViewById(R.id.mTvPhone);
            mTvDelete = (TextView) view.findViewById(R.id.mTvDelete);

            mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickItem(getAdapterPosition());
                    }
                }
            });
        }
    }
}

