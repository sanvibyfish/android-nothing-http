package io.nothing.http;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public abstract class NothingResponse<T extends Result> {

    public void transfer(JSONArray jsonArray) throws Exception {
        onSuccess((List<T>) getActualClass().transfer(jsonArray));
    }

    private Type getType() {
        Type mySuperClass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
    }

    private <T extends Result> T getActualClass() throws Exception {
        Result cls = null;
        try {
            String clsName = getType().toString().replace("class ", "").trim();
            cls = (Result) Class.forName(clsName).newInstance();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (T) cls;

    }


    public void transfer(JSONObject jsonObject) throws Exception {
        onSuccess((T) getActualClass().transfer(jsonObject));
    }

    public void transfer(String responseString) throws Exception {
        onSuccess(responseString);
    }

    public void onSuccess(List<T> response){

    }

    public void onSuccess(T response) throws NoSuchFieldException, IllegalAccessException {

    }

    public void onSuccess(String responseString){

    }

    public void onFailure(int statusCode, Throwable e,
                                   JSONObject errorResponse){
      e.printStackTrace();
    }

}
