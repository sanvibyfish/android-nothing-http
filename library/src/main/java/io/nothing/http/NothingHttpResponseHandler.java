package io.nothing.http;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by leo on 8/12/14.
 */
public interface NothingHttpResponseHandler {
    public JSONArray onSuccess(int statusCode, Header[] headers, JSONArray jsonArray);
    public JSONObject onSuccess(int statusCode, Header[] headers, JSONObject jsonObject);
    public String onSuccess(int statusCode, Header[] headers, String responseString);
    public Throwable onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable);
    public Throwable onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse);
    public Throwable onFailure(int statusCode, Header[] headers, Throwable e,JSONObject errorResponse);
}
