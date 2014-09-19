package io.nothing.oauth.config;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import io.nothing.oauth.OAuthInfoListener;
import io.nothing.oauth.token.Token;


public class SinaConfig extends OAuthConfig {

	public SinaConfig(String appKey, String appSecret, String redirectUrl) {
		super(appKey, appSecret, redirectUrl);
	}

	@Override
	public String getRefreshTokenUrl() {
		return "https://api.weibo.com/oauth2/access_token";
	}

	@Override
	public RequestParams getRefreshTokenParams(String refreshToken) {
		RequestParams params = new RequestParams();
		params.put("client_id", appKey);
		params.put("client_secret", appSecret);
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refreshToken);

		return params;
	}

	@Override
	public String getCodeUrl() {
		return "https://api.weibo.com/oauth2/authorize?client_id=" + appKey
				+ "&response_type=code&display=mobile&redirect_uri="
				+ encodedRedirectUrl();
	}

	@Override
	public String getAccessCodeUrl(String code) {
		return "https://api.weibo.com/oauth2/access_token?client_id=" + appKey
				+ "&client_secret=" + appSecret
				+ "&grant_type=authorization_code&redirect_uri="
				+ encodedRedirectUrl() + "&code=" + code;
	}

	@Override
	public RequestParams getAccessTokenParams(String code) {
		return null;
	}


  @Override
  public void getInfo(final Token token, final OAuthInfoListener l) {
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(String.format("https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s",token.getAccessToken(), token.getUid()), new JsonHttpResponseHandler(){
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
          token.setNickName(response.getString("screen_name"));
          token.setOpenAvatar(response.getString("profile_image_url"));
          String sexStr = response.getString("gender");
          int sex = 0;
          if (sexStr != null) {
            if (sexStr.equals("m")) {
              sex = 1;
            } else if (sexStr.equals("f")) {
              sex = 2;
            } else {
              sex = 0;
            }
          }
          token.setOpenSex(sex);
          token.setType(1);
          token.setOpenUid(response.getString("id"));
          if(l != null) {
            l.onSuccess(token);
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }


}