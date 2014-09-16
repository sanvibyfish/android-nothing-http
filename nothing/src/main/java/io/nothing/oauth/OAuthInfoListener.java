package io.nothing.oauth;

import io.nothing.oauth.token.Token;

/**
 * Created by sanvi on 6/3/14.
 */
public interface OAuthInfoListener {

  void onSuccess(Token token);
}
