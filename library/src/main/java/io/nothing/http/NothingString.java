package io.nothing.http;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sanvi on 6/24/14.
 */
public class NothingString  implements Result {

  public String str = null;
  public NothingString(String str) {
    this.str = str;
  }

  @Override
  public <T extends Result> List<T> transfer(JSONArray jsonArray) {
    return null;
  }

  @Override
  public <T extends Result> T transfer(JSONObject jsonObject) {
    return null;
  }
}
