package com.android.restaurantreservations.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.restaurantreservations.utils.KeyboardUtils;

/**
 * Created by Mohamed Elgendy.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void showSoftKeyboard(View view) {
        KeyboardUtils.showSoftKeyboard(getActivity(), view);
    }

    public void hideSoftKeyboard() {
        KeyboardUtils.hideSoftKeyboard(getActivity());
    }
}
