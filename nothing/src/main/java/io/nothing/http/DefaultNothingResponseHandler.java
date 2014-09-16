package io.nothing.http;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by leo on 8/12/14.
 */
public class DefaultNothingResponseHandler implements NothingHttpResponseHandler{

    @Override
    public JSONArray onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
        return jsonArray;
    }

    @Override
    public JSONObject onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
        return jsonObject;
    }

    @Override
    public String onSuccess(int statusCode, Header[] headers, String responseString) {
        return responseString;
    }

    @Override
    public Throwable onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        return throwable;
    }

    @Override
    public Throwable onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        return throwable;
    }

    @Override
    public Throwable onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        return throwable;
    }
}
