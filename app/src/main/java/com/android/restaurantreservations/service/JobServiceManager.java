package com.android.restaurantreservations.service;

import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Mohamed Elgendy.
 */

public class JobServiceManager {

    private static FirebaseJobDispatcher dispatcher;
    private static JobServiceManager INSTANCE;
    private static final String JOB_TAG = "RESET_RESERVATION";

    // Prevent direct instantiation.
    private JobServiceManager(Context context) {
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }

    public static JobServiceManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new JobServiceManager(context);
        }
        return INSTANCE;
    }

    private static Job createJob(FirebaseJobDispatcher dispatcher){
        Job job = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(ResetJobService.class)
                .setTag(JOB_TAG)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(10*60, 20*60))
                .build();
        return job;
    }

    public void scheduleResetJob(){
        Job job = createJob(dispatcher);
        dispatcher.schedule(job);
    }
}
