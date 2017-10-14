package com.android.restaurantreservations.service;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Mohamed Elgendy.
 */

public final class RxBus {

    private static PublishSubject<Object> sSubject = PublishSubject.create();

    private RxBus() {
        // hidden constructor
    }

    public static Disposable subscribe(Consumer<Object> action) {
        return sSubject.subscribe(action);
    }

    public static void publish(Object message) {
        sSubject.onNext(message);
    }
}
