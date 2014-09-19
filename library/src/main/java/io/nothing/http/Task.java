package io.nothing.http;

public class Task extends BaseTask {

	private OnTaskRequestListener onTaskRequestListener;
	
	public OnTaskRequestListener getOnTaskRequestListener() {
		return onTaskRequestListener;
	}

	public Task setOnTaskRequestListener(OnTaskRequestListener onTaskRequestListener) {
		this.onTaskRequestListener = onTaskRequestListener;
		return this;
	}
	
	
	public Task setOnInvokeBeforeListener(
			OnInvokeBeforeListener onInvokeBeforeListener) {
		super.setOnInvokeBeforeListener(onInvokeBeforeListener);
		return this;
	}
	
	public Task setOnInvokeAfterListener(OnInvokeAterListener onInvokeAfterListener) {
		super.setOnInvokeAfterListener(onInvokeAfterListener);
		return this;
	}
	

	public Task() {
		super();
	}
	
	

	@Override
	public Result request() {
		if(onTaskRequestListener != null) {
			return onTaskRequestListener.onRequest();
		}
		return null;
	}
	
	
	public interface OnTaskRequestListener {
		public Result onRequest();
	}



}
