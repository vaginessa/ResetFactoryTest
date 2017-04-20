package com.onyx.resetfactorytest.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 12345 on 2017/4/7.
 */

public class AsyncRequest<T> {
    public final static int DEFAULT_REQUEST = 0;
    public final static int REMOTE_DATA_REQUEST = 1;

    private static Handler handler;
    private RequestCallback<T> callback;
    private int requestType = 0;
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public AsyncRequest(int requestType, RequestCallback<T> callback) {
        this.requestType = requestType;
        this.callback = callback;
    }

    public void execute() {
        callback.onStart();
        run();
    }

    private void run() {
        if (requestType == REMOTE_DATA_REQUEST) {

        } else if(requestType == DEFAULT_REQUEST){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Message message = Message.obtain();
                    message.obj = new AsyncRequest.ResultData(AsyncRequest.this, callback.onDoInBackground());
                    getHandler().sendMessage(message);
                }
            });
        }

    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    private static class MyHandler<T> extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AsyncRequest.ResultData resultData = (AsyncRequest.ResultData) msg.obj;
            resultData.asyncRequest.callback.onResult(resultData.data);
        }
    }

    private static Handler getHandler() {
        if (handler == null) {
            synchronized (MyHandler.class) {
                if (handler == null) {
                    handler = new MyHandler(Looper.getMainLooper());
                }
            }
        }
        return handler;
    }


    private static class ResultData<Data> {
        AsyncRequest asyncRequest;
        Data data;

        public ResultData(AsyncRequest asyncRequest, Data data) {
            this.asyncRequest = asyncRequest;
            this.data = data;
        }
    }


}
