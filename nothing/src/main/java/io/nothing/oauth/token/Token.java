package io.nothing.oauth.token;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.nothing.oauth.config.DoubanConfig;
import io.nothing.oauth.config.OAuthConfig;
import io.nothing.oauth.config.QQConfig;
import io.nothing.oauth.config.RenrenConfig;
import io.nothing.oauth.config.SinaConfig;
import io.nothing.oauth.config.TweiboConfig;

public class Token {

	private static final String TAG = Token.class.getSimpleName();

	private long mExpiresTime = 0;
	private String mAccessToken = "";
	private String mRefreshToken = "";
	private long mExpiresIn;
	private String mUid = "";

  /**
   * 开放平台类型
   * QQ（0）、
   * 新浪微博（1）、
   * 腾讯微博（2）、
   * 人人网（3）、
   * 豆瓣网（4）、
   * twitter（5）、
   * facebook（6）。
   */
  private int type;

  public String getSource(){
    if(type == 0) {
      return "qq_connect";
    } else if (type == 1) {
      return "sina";
    }
    return "reg";
  }


  private String tokenSecret;

  private String nickName;

  /**
   * 开放平台性别（新浪微博、腾讯微博、人人网有，其他没有）
   */
  private int openSex;

  /**
   * 开放平台过期标识（人人网特有）
   */
  private String openExpire;

  private String openAvatar;

  private String openUid;


	public Token() {
	}

	public static Token make(String response, OAuthConfig config) {
		Log.d(TAG, "token make response: " + response);

		if (config instanceof SinaConfig) {
			try {
				return new WeiboToken(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (config instanceof DoubanConfig) {
			try {
				return new DoubanToken(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (config instanceof TweiboConfig) {
			return new TWeiboToken(response);
		} else if (config instanceof RenrenConfig) {
			try {
				return new RenrenToken(new JSONObject(response));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (config instanceof QQConfig) {
			return new QQToken(response);
		}

		return null;

	}

	public void setAccessToken(String mAccessToken) {
		this.mAccessToken = mAccessToken;
	}

	public String getRefreshToken() {
		return mRefreshToken;
	}

	public void setRefreshToken(String mRefreshToken) {
		this.mRefreshToken = mRefreshToken;
	}

	public String getUid() {
		return mUid;
	}

	public void setUid(String mUid) {
		this.mUid = mUid;
	}

	public String getAccessToken() {
		return mAccessToken;
	}

	public void setExpiresIn(long expiresIn) {
		if (expiresIn != 0) {
			setExpiresTime(System.currentTimeMillis() + expiresIn * 1000);
			this.mExpiresIn = expiresIn;
		}
	}

	public void setExpiresTime(long mExpiresTime) {
		this.mExpiresTime = mExpiresTime;
	}

	public long getExpiresTime() {
		return mExpiresTime;
	}

	public long getExpiresIn() {
		return mExpiresIn;
	}

	public boolean isSessionValid() {
		return (!TextUtils.isEmpty(mAccessToken) && (mExpiresTime == 0 || (System
				.currentTimeMillis() < mExpiresTime)));
	}

  public void getInfo(){};

	@Override
	public String toString() {
		String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
				.format(new java.util.Date(mExpiresTime));
		return "mAccessToken:" + mAccessToken + ";mExpiresTime:" + date
				+ ";mRefreshToken:" + mRefreshToken + ";mUid:" + mUid;
	}

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public int getOpenSex() {
    return openSex;
  }

  public void setOpenSex(int openSex) {
    this.openSex = openSex;
  }

  public String getOpenAvatar() {
    return openAvatar;
  }

  public void setOpenAvatar(String openAvatar) {
    this.openAvatar = openAvatar;
  }

  public String getOpenUid() {
    return openUid;
  }

  public void setOpenUid(String openUid) {
    this.openUid = openUid;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
