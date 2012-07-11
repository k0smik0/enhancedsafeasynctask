package net.iubris.etask.test;

import android.app.Activity;

public class TestActivity extends Activity {
	
	@Override
	protected void onResume() {		
		super.onResume();
		new TestTask(this).execute();
	}

}
