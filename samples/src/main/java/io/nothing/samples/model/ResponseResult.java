package io.nothing.samples.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import io.nothing.android.http.Result;

/**
 * @author: Sanvi
 * @date: 8/13/14
 * @email: sanvibyfish@gmail.com
 */
public class ResponseResult implements Result, Serializable {

  public String code;
  public String message;

  @Override
  public <T extends Result> List<T> transfer(JSONArray jsonArray) {
    return JSONConvert.transfer(jsonArray, this.getClass());
  }

  @Override
  public <T extends Result> T transfer(JSONObject jsonObject) {
    return JSONConvert.transfer(jsonObject, this.getClass());
  }

}
