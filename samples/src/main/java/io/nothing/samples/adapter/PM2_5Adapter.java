package io.nothing.samples.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.nothing.samples.R;
import io.nothing.samples.model.PM2_5;

/**
 * @author: Sanvi
 * @date: 11/5/14
 * @email: sanvibyfish@gmail.com
 */
public class PM2_5Adapter extends BaseAdapter {

  public final String TAG = getClass().getSimpleName();

  protected Context context;
  protected List<PM2_5> items;

  public PM2_5Adapter(Context context) {
    this.context = context;
  }

  public List<PM2_5> getItems() {
    return items;
  }

  @Override
  public int getCount() {
    int count = 0;
    if (items != null) {
      count = items.size();
    }
    return count;
  }

  @Override
  public PM2_5 getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public void setItems(List<PM2_5> items) {
    this.items = items;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;

    if (convertView == null) {
      convertView = View.inflate(context, R.layout.activity_pm2_5_item, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    PM2_5 item = items.get(position);
    holder.mAqi.setText(String.format("空气质量指数:%s",item.aqi));
    holder.mPm25.setText(String.format("1小时平均颗粒物:%s",item.pm2_5));
    holder.mPm2524h.setText(String.format("24小时平均颗粒物%s",item.pm2_5_24h));
    holder.mPositionName.setText(String.format("监测点:%s",item.position_name));
    holder.mPrimaryPollutant.setText(String.format("首要污染物:%s",item.primary_pollutant));
    holder.mQuality.setText(String.format("空气质量指数类别:%s",item.quality));
    holder.mStationCode.setText(String.format("监测点编码:%s",item.station_code));
    holder.mTimePoint.setText(String.format("时间:%s",item.time_point));

    return convertView;
  }

  /**
   * This class contains all butterknife-injected Views & Layouts from layout file 'activity_pm2_5_item.xml'
   * for easy to all layout elements.
   *
   * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
   */
  static class ViewHolder {
    @InjectView(R.id.aqi)
    TextView mAqi;
    @InjectView(R.id.pm2_5)
    TextView mPm25;
    @InjectView(R.id.pm2_5_24h)
    TextView mPm2524h;
    @InjectView(R.id.position_name)
    TextView mPositionName;
    @InjectView(R.id.primary_pollutant)
    TextView mPrimaryPollutant;
    @InjectView(R.id.quality)
    TextView mQuality;
    @InjectView(R.id.station_code)
    TextView mStationCode;
    @InjectView(R.id.time_point)
    TextView mTimePoint;

    ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
