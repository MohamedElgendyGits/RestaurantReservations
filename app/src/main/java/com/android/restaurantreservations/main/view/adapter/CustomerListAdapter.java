package com.android.restaurantreservations.main.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.restaurantreservations.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.DataObjectHolder> {

    private ArrayList<CustomerViewModel> mDataSet;
    private Context context;
    private static CustomerClickListener mCustomerClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textView_customer_first_name)
        TextView firstNameTextView;

        @BindView(R.id.textView_customer_last_name)
        TextView lastNameTextView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //adding listener...
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCustomerClickListener.onCustomerClick(getAdapterPosition(), v);
        }
    }

    public CustomerListAdapter(ArrayList<CustomerViewModel> mDataSet, CustomerClickListener mCustomerClickListener) {
        this.mDataSet = mDataSet;
        this.mCustomerClickListener = mCustomerClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer, parent, false);
        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        CustomerViewModel customerViewModel = mDataSet.get(position);

        holder.firstNameTextView.setText(customerViewModel.getFirstName());
        holder.lastNameTextView.setText(customerViewModel.getLastName());
    }

    public void addItem(CustomerViewModel dataObj) {
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

    public interface CustomerClickListener {
        void onCustomerClick(int position, View v);
    }
}
