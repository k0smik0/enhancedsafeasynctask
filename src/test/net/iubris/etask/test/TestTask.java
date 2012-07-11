package net.iubris.etask.test;


import net.iubris.etask.EnhancedSafeAsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TestTask extends EnhancedSafeAsyncTask<String> {

	private Context context;

	public TestTask(Context context) {
		this.context = context;
	}

	@Override
	public String call() throws NumberFormatException {
		throw new NumberFormatException();
	}
	
	@Override
	protected void onException(Exception e) throws RuntimeException {
		super.onException(e);
		Log.d("SC","e");
	}
	
	//@Override
	protected void onException(NumberFormatException e) throws RuntimeException {	
		super.onException(e);
//		System.out.println("nfe");
		Log.d("SC","nfe");
		Toast.makeText(context, "nfe",Toast.LENGTH_SHORT).show();
	}

}
