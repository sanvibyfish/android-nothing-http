package io.nothing.oauth.config;

import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.nothing.oauth.OAuthInfoListener;
import io.nothing.oauth.OAuthListener;
import io.nothing.oauth.token.Token;

public class QQConfig extends OAuthConfig {

	public QQConfig(String appKey, String appSecret, String redirectUrl) {
		super(appKey, appSecret, redirectUrl);
	}

	@Override
	public String getRefreshTokenUrl() {
		return null;
	}

	@Override
	public RequestParams getRefreshTokenParams(String refreshToken) {
		RequestParams params = new RequestParams();
		params.put("client_id", appKey);
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refreshToken);

		return params;
	}

	@Override
	public String getCodeUrl() {
		return "https://graph.qq.com/oauth2.0/authorize?client_id=" + appKey
				+ "&response_type=code&redirect_uri=" + encodedRedirectUrl()
				+ "&state=test&display=mobile";
	}

	@Override
	public String getAccessCodeUrl(String code) {
		return "https://graph.qq.com/oauth2.0/token?client_id=" + appKey
				+ "&client_secret=" + appSecret + "&redirect_uri="
				+ encodedRedirectUrl() + "&grant_type=authorization_code&code="
				+ code;
	}

	@Override
	public RequestParams getAccessTokenParams(String code) {
		RequestParams params = new RequestParams();

		return params;
	}

	public void getAccessCode(Uri uri, final OAuthListener l) {
		final String code = uri.getQueryParameter("code");
		Log.d(TAG, "code: " + code);

		final AsyncHttpClient client = new AsyncHttpClient();
		String newUrl = getAccessCodeUrl(code);
		Log.d(TAG, "newUrl: " + newUrl);
		RequestParams requestParams = getAccessTokenParams(code);
		getAccessCodeRequest(client, newUrl, requestParams,
				new AsyncHttpResponseHandler() {

          @Override
          public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if (l != null) {
              final Token token = Token.make(new String(responseBody),
                  QQConfig.this);
              client.get(
                  "https://graph.z.qq.com/moc2/me?access_token="
                      + token.getAccessToken(),
                  new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                      if (l != null && responseBody != null) {
                        l.onError(new String(responseBody));
                      }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                      Log.d(TAG, "openid response: "
                          + new String(responseBody));
                      // client_id=100222222&openid=1704************************878C
                      Pattern pattern = Pattern
                          .compile("client_id=(.*)&openid=(.*)");
                      Matcher m = pattern
                          .matcher(new String(responseBody));
                      if (m.matches()
                          && m.groupCount() == 2) {
                        token.setUid(m.group(2));
                        l.onSuccess(token);
                      }
                    }
                  });

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

  @Override
  public void getInfo(final Token token, final OAuthInfoListener l) {
    final AsyncHttpClient client = new AsyncHttpClient();
    token.setType(0);
    client.get(String.format("https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s",token.getAccessToken(),appKey ,token.getUid()), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
              try {
                token.setOpenAvatar(response.getString("figureurl"));
                token.setNickName(response.getString("nickname"));
                if(response.getString("gender").equals("男")){
                   token.setOpenSex(1);
                } else {
                  token.setOpenSex(2);
                }
                if(l != null) {
                  l.onSuccess(token);
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
      }
    });


  }

  @Override
	protected void getAccessCodeRequest(AsyncHttpClient client, String url,
			RequestParams requestParams, AsyncHttpResponseHandler l) {
		client.get(url, requestParams, l);
	}

}