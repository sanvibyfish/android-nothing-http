package io.nothing.android.http;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

/**
 * Created by megrez on 14/10/25.
 */
public class NothingJSONEntityParam extends StringEntity {

  public NothingJSONEntityParam(String s) throws UnsupportedEncodingException {
    super(s,"utf-8");
    setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
  }

}
