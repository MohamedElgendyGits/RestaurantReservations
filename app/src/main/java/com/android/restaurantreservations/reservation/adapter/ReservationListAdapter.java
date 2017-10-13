package com.android.restaurantreservations.reservation.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.utils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.DataObjectHolder> {

    private ArrayList<ReservationViewModel> mDataSet;
    private Context context;
    private static ReservationClickListener mReservationClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageView_table)
        AppCompatImageView tableImageView;

        @BindView(R.id.textView_table_status)
        TextView tableStatusTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //adding listener...
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mReservationClickListener.onTableClick(getAdapterPosition(), v);
        }
    }

    public ReservationListAdapter(ArrayList<ReservationViewModel> mDataSet, ReservationClickListener mReservationClickListener) {
        this.mDataSet = mDataSet;
        this.mReservationClickListener = mReservationClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tables, parent, false);
        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        ReservationViewModel reservationViewModel = mDataSet.get(position);

        if(reservationViewModel.isTableStatus()){
            Picasso.with(context)
                    .load(R.drawable.table_empty)
                    .fit()
                    .into(holder.tableImageView);

            holder.tableStatusTextView.setText(TextUtils.getString(R.string.table_available));
        } else {
            Picasso.with(context)
                    .load(R.drawable.table_reserved)
                    .fit()
                    .into(holder.tableImageView);

            holder.tableStatusTextView.setText(TextUtils.getString(R.string.table_reserved));
        }

    }

    public void addItem(ReservationViewModel dataObj) {
        mDataSet.add(dataObj);
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface ReservationClickListener {
        void onTableClick(int position, View v);
    }
}
