package io.nothing.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class NothingRestClient {

    private static String TAG = NothingRestClient.class.getSimpleName();

    private static String baseURL = "";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private NothingHttpResponseHandler handler;

    public NothingRestClient(String baseURL) {
        this.baseURL = baseURL;
        this.handler = new DefaultNothingResponseHandler();
    }

    public NothingRestClient(String baseURL, NothingHttpResponseHandler handler) {
        this.baseURL = baseURL;
        this.handler = handler;
    }


    public AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    public AsyncHttpClient getClient() {
        return client;
    }

    public void get(String url, NothingResponse response, NothingParam... params
    ) {
        client.get(url, stripNulls(params), jsonHttpResponseHandler(response));
    }

    public void get(String url, AsyncHttpResponseHandler response, NothingParam... params
    ) {
        client.get(url, stripNulls(params), response);
    }


    public void post(String url, NothingResponse response, NothingParam... params) {
        Log.i("request","url："+url);
        for(NothingParam param:params)
        Log.i("request","params："+param.getName()+"  "+param.getValue());

        client.post(url, stripNulls(params), jsonHttpResponseHandler(response));
    }


    public void post(String url, AsyncHttpResponseHandler response, NothingParam... params) {
        client.post(url, stripNulls(params), response);
    }

    public void download(String url, DataAsyncHttpResponseHandler handler) {
        client.get(url, handler);
    }

    private RequestParams stripNulls(NothingParam... nothingParams) {
        RequestParams params = new RequestParams();
        if (nothingParams != null) {
            for (int i = 0; i < nothingParams.length; i++) {
                NothingParam param = nothingParams[i];
                if (param.getValue() != null) {
                    if (param instanceof NothingStringParam) {
                        NothingStringParam nothingParam = (NothingStringParam) param;
                        params.put(nothingParam.getName(), nothingParam.getValue());
                    }

                }
            }
        }
        return params;
    }

    private <T extends Result> AsyncHttpResponseHandler jsonHttpResponseHandler(final NothingResponse<T> response) {

        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                try {
                    response.transfer(handler.onSuccess(statusCode, headers,jsonArray));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                try {
                    response.transfer(handler.onSuccess(statusCode, headers, jsonObject));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    response.transfer(handler.onSuccess(statusCode, headers, responseString));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace();
                handler.onFailure(statusCode, headers,responseString, throwable);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                throwable.printStackTrace();
                handler.onFailure(statusCode, headers, throwable,errorResponse);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                throwable.printStackTrace();
                handler.onFailure(statusCode, headers, throwable,errorResponse);
                response.onFailure(statusCode, throwable, errorResponse);
            }
        };


    }
}
