package io.nothing.samples.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.nothing.http.Result;

/**
 * @author: Sanvi
 * @date: 10/28/14
 * @email: sanvibyfish@gmail.com
 */
public class JSONConvert {

  public static <T extends Result> List<T> transfer(JSONArray jsonArray,Class cls) {
    List result = new ArrayList();
    for (int i = 0; i < jsonArray.length(); i++) {
      try {
        result.add(transfer(jsonArray.getJSONObject(i),cls));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return (List<T>) result;
  }

  public static <T extends Result> T transfer(JSONObject jsonObject,Class cls) {
    if(jsonObject != null){
      Gson gson = new Gson();
      return(T) gson.fromJson(jsonObject.toString(), cls);
    } else{
      return null;
    }
  }
}
