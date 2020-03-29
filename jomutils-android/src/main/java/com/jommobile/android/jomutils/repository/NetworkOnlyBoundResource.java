package com.jommobile.android.jomutils.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.jommobile.android.jomutils.AppExecutors;
import com.jommobile.android.jomutils.Logs;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/14/2020
 */
public abstract class NetworkOnlyBoundResource<RESULT> {

    private static final String TAG = Logs.makeTag(NetworkOnlyBoundResource.class);

    final AppExecutors appExecutors;

    final MediatorLiveData<Resource<RESULT>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkOnlyBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.Factory.loading());
        fetchFromNetwork();
    }

    protected void setValue(Resource<RESULT> newValue) {
        if (!result.getValue().equals(newValue)) {
            result.setValue(newValue);
        }
    }

    @NonNull
    @MainThread
    protected abstract LiveData<Resource<RESULT>> createNetworkCall();

    public LiveData<Resource<RESULT>> asLiveData() {
        return result;
    }

    @MainThread
    protected void onFetchFailed(Throwable cause) {
    }

    private void fetchFromNetwork() {
        final Executor mainThread = appExecutors.mainThread();

        final LiveData<Resource<RESULT>> callSource = createNetworkCall();
        Objects.requireNonNull(callSource, "Required a network call data source, createNetworkCall() must not return null.");

        result.addSource(callSource, newNetworkSource -> {
            if (newNetworkSource == null)
                return;

            if (newNetworkSource.getStatus() != Resource.Status.LOADING) {
                final RESULT data = newNetworkSource.getData();

                if (newNetworkSource.getStatus() == Resource.Status.SUCCESS) {
                    mainThread.execute(() -> {
                        setValue(Resource.Factory.success(data));
                    });
                } else { // error
                    mainThread.execute(() -> {
                        setValue(Resource.Factory.error(newNetworkSource.getMessage(), newNetworkSource.getCause()));
                        onFetchFailed(newNetworkSource.getCause());
                    });
                }
            }
        });
    }

}
