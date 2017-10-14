package com.android.restaurantreservations.main.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.application.RestaurantReservationsApp;
import com.android.restaurantreservations.base.injector.Injection;
import com.android.restaurantreservations.base.view.BaseFragment;
import com.android.restaurantreservations.main.MainActivity;
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

public class CustomerFragment extends BaseFragment implements CustomerView, CustomerListAdapter.CustomerClickListener {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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

        startIdlingResource();

        customerPresenter.loadCustomers();
    }

    @Override
    public void showProgressLoading() {
        if (progressDialog == null)
            progressDialog = DialogUtils.getProgressDialog(getActivity(),
                    TextUtils.getString(R.string.loading_message), false,
                    false);

        progressDialog.show();
    }

    @Override
    public void hideProgressLoading() {

        stopIdlingResource();

        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showInlineError(String error) {

        stopIdlingResource();

        DialogUtils.getSnackBar(getView(), error, null, null).show();
    }

    @Override
    public void showInlineConnectionError(String error) {

        stopIdlingResource();

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

        stopIdlingResource();

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
        customerViewModelArrayList.clear();
        customerAdapter.notifyDataSetChanged();

        customerViewModelArrayList.addAll(customerViewModels);
        customerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCustomerClick(int position, View v) {
        Intent reservationsIntent = new Intent(getActivity(), ReservationActivity.class);
        startActivity(reservationsIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        performSearch(searchView);
    }

    private void performSearch(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customerPresenter.searchByCustomer(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    customerPresenter.loadCustomers();
                }else{
                    customerPresenter.searchByCustomer(newText);
                }
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        customerPresenter.onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        customerPresenter.onViewDetached();
    }

    /**
     * The IdlingResource is null in production as set by the @Nullable annotation which means
     * the value is allowed to be null.
     * <p>
     * If the idle state is true, Espresso can perform the next action.
     * If the idle state is false, Espresso will wait until it is true before
     * performing the next action.
     */

    private void startIdlingResource() {
        if (((MainActivity) getActivity()).mIdlingResource != null) {
            ((MainActivity) getActivity()).mIdlingResource.setIdleState(false);
        }
    }

    private void stopIdlingResource() {
        if (((MainActivity) getActivity()).mIdlingResource != null) {
            ((MainActivity) getActivity()).mIdlingResource.setIdleState(true);
        }
    }
}
