package com.android.restaurantreservations.reservation.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.android.restaurantreservations.R;
import com.android.restaurantreservations.application.RestaurantReservationsApp;
import com.android.restaurantreservations.base.injector.Injection;
import com.android.restaurantreservations.base.view.BaseFragment;
import com.android.restaurantreservations.main.MainActivity;
import com.android.restaurantreservations.main.presenter.CustomerPresenter;
import com.android.restaurantreservations.main.presenter.CustomerPresenterImpl;
import com.android.restaurantreservations.main.view.adapter.CustomerListAdapter;
import com.android.restaurantreservations.main.view.adapter.CustomerViewModel;
import com.android.restaurantreservations.reservation.ReservationActivity;
import com.android.restaurantreservations.reservation.adapter.ReservationListAdapter;
import com.android.restaurantreservations.reservation.adapter.ReservationViewModel;
import com.android.restaurantreservations.reservation.presenter.ReservationPresenter;
import com.android.restaurantreservations.reservation.presenter.ReservationPresenterImpl;
import com.android.restaurantreservations.service.RxBus;
import com.android.restaurantreservations.utils.DialogUtils;
import com.android.restaurantreservations.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Mohamed Elgendy.
 */

public class ReservationFragment extends BaseFragment implements ReservationView ,
        ReservationListAdapter.ReservationClickListener  {

    @BindView(R.id.reservations_recycler_view)
    RecyclerView reservationsRecyclerView;

    // declare reservations list components
    private RecyclerView.Adapter reservationAdapter;
    private RecyclerView.LayoutManager reservationLayoutManager;
    private ArrayList<ReservationViewModel> reservationViewModelArrayList;

    // declare progress dialog
    private ProgressDialog progressDialog;
    private AlertDialog bookingDialog;

    // declare reservation presenter
    private ReservationPresenter reservationPresenter;


    public ReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // initialize views
        reservationsRecyclerView.setHasFixedSize(true);
        reservationLayoutManager = new GridLayoutManager(getActivity(),3);
        reservationsRecyclerView.setLayoutManager(reservationLayoutManager);
        reservationViewModelArrayList = new ArrayList<>();
        reservationAdapter = new ReservationListAdapter(reservationViewModelArrayList,this);
        reservationsRecyclerView.setAdapter(reservationAdapter);


        ViewTreeObserver viewTreeObserver = reservationsRecyclerView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateCellSize();
            }
        });

        initializePresenter();
    }


    private void calculateCellSize() {
        int sColumnWidth = 100; // assume cell width of 100dp
        int spanCount = (int) Math.floor(reservationsRecyclerView.getWidth() / convertDPToPixels(sColumnWidth));
        ((GridLayoutManager) reservationsRecyclerView.getLayoutManager()).setSpanCount(spanCount);
    }

    private int convertDPToPixels(int sColumnWidth) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) (sColumnWidth * logicalDensity);
    }

    private void initializePresenter() {
        reservationPresenter = new ReservationPresenterImpl(this,
                Injection.provideReservationRepository(RestaurantReservationsApp.getInstance()));


        startIdlingResource();

        reservationPresenter.loadReservations();
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
                        reservationPresenter.loadReservations();
                    }
                })
                .show();
    }


    @Override
    public void loadReservationsList(List<ReservationViewModel> reservations) {

        stopIdlingResource();

        reservationViewModelArrayList.clear();
        reservationAdapter.notifyDataSetChanged();

        reservationViewModelArrayList.addAll(reservations);
        reservationAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBookingDialog(final ReservationViewModel reservationViewModel, final int position) {
        if (bookingDialog != null)
            bookingDialog.dismiss();

        bookingDialog = new AlertDialog.Builder(getActivity())
                .setTitle(TextUtils.getString(R.string.booking_title))
                .setMessage(String.format(TextUtils.getString(R.string.booking_message), reservationViewModel.getTableNumber()))
                .setPositiveButton(R.string.book_table, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startIdlingResource();

                        reservationPresenter.updateTableStatus(reservationViewModel, position);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showInlineBookingError(String error) {

        stopIdlingResource();

        DialogUtils.getSnackBar(getView(), error, null, null).show();
    }

    @Override
    public void updateGridStatus(ReservationViewModel reservationViewModel, int position) {

        stopIdlingResource();

        ((ReservationListAdapter)reservationAdapter).updateItem(position,reservationViewModel);
        reservationsRecyclerView.setItemAnimator(null);
    }

    @Override
    public void clearAllGridStatus() {
        ((ReservationListAdapter)reservationAdapter).updateAllItems();
        reservationsRecyclerView.setItemAnimator(null);
    }


    @Override
    public void onTableClick(int position, View v) {
        reservationPresenter.tableClicked(reservationViewModelArrayList.get(position) , position);
    }

    @Override
    public void onResume() {
        super.onResume();
        reservationPresenter.onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        reservationPresenter.onViewDetached();
    }


    /**
     * The IdlingResource is null in production as set by the @Nullable annotation which means
     * the value is allowed to be null.
     *
     * If the idle state is true, Espresso can perform the next action.
     * If the idle state is false, Espresso will wait until it is true before
     * performing the next action.
     */

    private void startIdlingResource() {
        if (((ReservationActivity)getActivity()).mIdlingResource != null) {
            ((ReservationActivity)getActivity()).mIdlingResource.setIdleState(false);
        }
    }
    private void stopIdlingResource(){
        if (((ReservationActivity)getActivity()).mIdlingResource != null) {
            ((ReservationActivity)getActivity()).mIdlingResource.setIdleState(true);
        }
    }
}
