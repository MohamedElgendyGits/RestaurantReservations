package com.android.restaurantreservations.base.presenter;

import com.android.restaurantreservations.base.view.BaseView;

/**
 * Created by Mohamed Elgendy.
 */

public interface BasePresenter {
    void onViewAttached(BaseView view);
    void onViewDetached();
}
