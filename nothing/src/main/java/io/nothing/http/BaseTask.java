package io.nothing.http;

import android.content.Context;
import android.os.AsyncTask;


/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间2010-8-19 上午11:54:22
 */
public abstract class BaseTask extends AsyncTask<String, Void, Result>  {
	protected Context context = null;
	
	private static String TAG = "BaseTask";
	private static boolean DEBUG = true;
	
	

	private  OnInvokeBeforeListener onInvokeBeforeListener;
	public OnInvokeBeforeListener getOnInvokeBeforeListener() {
		return onInvokeBeforeListener;
	}

	public BaseTask setOnInvokeBeforeListener(
			OnInvokeBeforeListener onInvokeBeforeListener) {
		this.onInvokeBeforeListener = onInvokeBeforeListener;
		return this;
	}

	
	private OnInvokeAterListener onInvokeAfterListener;

	
	
	public OnInvokeAterListener getOnInvokeAfterListener() {
		return onInvokeAfterListener;
	}

	public BaseTask setOnInvokeAfterListener(OnInvokeAterListener onInvokeAfterListener) {
		this.onInvokeAfterListener = onInvokeAfterListener;
		return this;
	}


	public interface OnInvokeBeforeListener {
		public void onInvokeBefore();
	}
	
	public interface OnInvokeAterListener {
		public void onInvokeAter(Result result);
	}
	
	
	@Override
	 protected void onPreExecute() {
		//执行客户写的代码
		if(onInvokeBeforeListener != null) {
			onInvokeBeforeListener.onInvokeBefore();
		}
	}

	@Override
	protected Result doInBackground(String... strings) {
    return request();
	}
	
	/*
	 * 在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用�?*
	 * 后台的计算结果将通过该方法传递到UI thread.
	 */
	@Override
	protected void onPostExecute(Result result) {
		if(onInvokeAfterListener != null){
			onInvokeAfterListener.onInvokeAter(result);
		}
	}
	
	/**
	 * 获取数据
	 */
	abstract public Result request();
	
	public void execute(){
		super.execute();
	}
	
}
