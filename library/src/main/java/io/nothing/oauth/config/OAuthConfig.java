package io.nothing.oauth.config;

import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.net.URLEncoder;

import io.nothing.oauth.OAuthInfoListener;
import io.nothing.oauth.OAuthListener;
import io.nothing.oauth.token.Token;

public abstract class OAuthConfig {

	protected static final String TAG = OAuthConfig.class.getSimpleName();

	public String appKey = "";
	public String appSecret = "";
	public String redirectUrl = "";

	public OAuthConfig(String appKey, String appSecret, String redirectUrl) {
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.redirectUrl = redirectUrl;
	}

	@SuppressWarnings("deprecation")
	public String encodedRedirectUrl() {
		return URLEncoder.encode(redirectUrl);
	}

	public abstract String getRefreshTokenUrl();

	public abstract RequestParams getRefreshTokenParams(String refreshToken);

	public abstract String getCodeUrl();

	public abstract String getAccessCodeUrl(String code);

	public abstract RequestParams getAccessTokenParams(String url);

  public abstract void getInfo(Token token, OAuthInfoListener l);

	public void getAccessCode(Uri uri, final OAuthListener l) {
		final String code = uri.getQueryParameter("code");
		Log.d(TAG, "code: " + code);

		AsyncHttpClient client = new AsyncHttpClient();
		String newUrl = getAccessCodeUrl(code);
		Log.d(TAG, "newUrl: " + newUrl);
		RequestParams requestParams = getAccessTokenParams(newUrl);
		getAccessCodeRequest(client, newUrl, requestParams,
				new AsyncHttpResponseHandler() {
          @Override
          public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if (l != null) {
              Token token = Token
                  .make(new String(responseBody), OAuthConfig.this);
              l.onSuccess(token);
            }
          }

          @Override
          public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            if (l != null) {
              l.onError(new String(responseBody));
            }
          }

        });
	}

	protected void getAccessCodeRequest(AsyncHttpClient client, String url,
			RequestParams requestParams, AsyncHttpResponseHandler l) {
		client.post(url, requestParams, l);

	}
}
