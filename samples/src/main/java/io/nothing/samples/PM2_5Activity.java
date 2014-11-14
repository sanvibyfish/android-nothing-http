package io.nothing.samples;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nothing.android.http.NothingResponse;
import io.nothing.android.http.NothingRestClient;
import io.nothing.samples.adapter.PM2_5Adapter;
import io.nothing.samples.model.PM2_5;

/**
 * Created by sanvi on 11/5/14.
 */
public class PM2_5Activity extends Activity {

  @InjectView(R.id.listview)
  ListView mListview;
  private NothingRestClient mClient;
  private PM2_5Adapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    adapter = new PM2_5Adapter(this);
    mListview.setAdapter(adapter);
    mClient = new NothingRestClient();
    mClient.get("http://www.pm25.in/api/querys/pm2_5.json?token=5j1znBVAsnSf5xQyNQyq&city=021", new NothingResponse<PM2_5>() {
      @Override
      public void onSuccess(List<PM2_5> response) {
        if (response != null) {
          adapter.setItems(response);
          adapter.notifyDataSetChanged();
        }
      }

      @Override
      public void onFailure(int statusCode, Throwable e, JSONObject errorResponse) {

      }
    });
  }
}
