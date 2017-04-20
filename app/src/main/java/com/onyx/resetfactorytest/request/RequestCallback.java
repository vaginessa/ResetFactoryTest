package com.onyx.resetfactorytest.request;


/**
 * Created by 12345 on 2017/4/7.
 */

public abstract class RequestCallback<ResultData> {

    public void onStart() {
    }

    public abstract ResultData onDoInBackground();

    public void onResult(ResultData resultData) {
    }
}
