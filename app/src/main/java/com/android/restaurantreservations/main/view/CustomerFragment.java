package com.android.restaurantreservations.main.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.application.RestaurantReservationsApp;
import com.android.restaurantreservations.base.injector.Injection;
import com.android.restaurantreservations.base.view.BaseFragment;
import com.android.restaurantreservations.main.view.adapter.CustomerListAdapter;
import com.android.restaurantreservations.main.view.adapter.CustomerViewModel;
import com.android.restaurantreservations.main.model.CustomerRepository;
import com.android.restaurantreservations.main.presenter.CustomerPresenter;
import com.android.restaurantreservations.main.presenter.CustomerPresenterImpl;
import com.android.restaurantreservations.reservation.ReservationActivity;
import com.android.restaurantreservations.utils.DialogUtils;
import com.android.restaurantreservations.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Elgendy.
 */

public class CustomerFragment extends BaseFragment implements CustomerView , CustomerListAdapter.CustomerClickListener {

    @BindView(R.id.customers_recycler_view)
    RecyclerView customersRecyclerView;

    // declare customers list components
    private RecyclerView.Adapter customerAdapter;
    private RecyclerView.LayoutManager customerLayoutManager;
    private ArrayList<CustomerViewModel> customerViewModelArrayList;

    // declare progress dialog
    private ProgressDialog progressDialog;

    // declare customer presenter
    private CustomerPresenter customerPresenter;


    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // initialize views
        customersRecyclerView.setHasFixedSize(true);
        customerLayoutManager = new LinearLayoutManager(getActivity());
        customersRecyclerView.setLayoutManager(customerLayoutManager);
        customerViewModelArrayList = new ArrayList<>();
        customerAdapter = new CustomerListAdapter(customerViewModelArrayList, this);
        customersRecyclerView.setAdapter(customerAdapter);

        initializePresenter();
    }

    private void initializePresenter() {
        customerPresenter = new CustomerPresenterImpl(this,
                Injection.provideCustomerRepository(RestaurantReservationsApp.getInstance()));
        customerPresenter.loadCustomers();
    }

    @Override
    public void showProgressLoading() {
        /*if (NearByApplication.isActivityVisible()) {
            if (progressDialog == null)
                progressDialog = DialogUtils.getProgressDialog(getActivity(),
                        TextUtils.getString(R.string.loading_message), false,
                        false);

            progressDialog.show();
        }*/

        //todo check activity attached or not
        if (progressDialog == null)
            progressDialog = DialogUtils.getProgressDialog(getActivity(),
                    TextUtils.getString(R.string.loading_message), false,
                    false);

        progressDialog.show();
    }

    @Override
    public void hideProgressLoading() {
        /*if (NearByApplication.isActivityVisible()) {
            if (progressDialog != null)
                progressDialog.dismiss();
        }*/

        //todo check activity attached or not
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showInlineError(String error) {
        /*
        if (NearByApplication.isActivityVisible()) {
            DialogUtils.getSnackBar(getView(), error, null, null).show();
        }
        */

        //todo check activity attached or not
        DialogUtils.getSnackBar(getView(), error, null, null).show();
    }

    @Override
    public void showInlineConnectionError(String error) {
        /*if (NearByApplication.isActivityVisible()) {
            Snackbar.make(getView(), error
                    , Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nearByPresenter.startLocationRefresh();
                        }
                    })
                    .show();
        }*/

        //todo check activity attached or not
        Snackbar.make(getView(), error
                , Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customerPresenter.loadCustomers();
                    }
                })
                .show();
    }

    @Override
    public void loadCustomersList(List<CustomerViewModel> customerViewModels) {
        /*if(NearByApplication.isActivityVisible()) {
        customerViewModelArrayList.clear();
        customerAdapter.notifyDataSetChanged();

        customerViewModelArrayList.addAll(customerViewModels);
        customerAdapter.notifyDataSetChanged();
        }*/

        //todo check activity attached or not
        customerViewModelArrayList.clear();
        customerAdapter.notifyDataSetChanged();

        customerViewModelArrayList.addAll(customerViewModels);
        customerAdapter.notifyDataSetChanged();
    }

    @Override
    public void openReservationScreen() {
        Intent reservation = new Intent(getActivity(), ReservationActivity.class);
        startActivity(reservation);
    }

    @Override
    public void filterCustomersList(List<CustomerViewModel> customerViewModels) {
        //todo filter by customer name
    }

    @Override
    public void onCustomerClick(int position, View v) {
        Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //todo check if it works fine after rotation
        customerPresenter.clearRxDisposables();
    }
}
