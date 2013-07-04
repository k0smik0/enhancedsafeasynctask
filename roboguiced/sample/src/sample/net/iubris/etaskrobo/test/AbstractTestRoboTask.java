/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AbstractTestRoboTask.java is part of 'EnhancedSafeAsyncTask'.
 * 
 * 'EnhancedSafeAsyncTask' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'EnhancedSafeAsyncTask' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'EnhancedSafeAsyncTask' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.etaskrobo.test;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import net.iubris.etask.roboguiced.RoboEnhancedAsyncTask;

public abstract class AbstractTestRoboTask 
extends RoboEnhancedAsyncTask<String> {
//extends RoboAsyncTask<String> {
	
	public AbstractTestRoboTask(Context context) {
		super(context);
	}

	protected void onException(OddNumberException e) throws RuntimeException {
		Log.d("AbstractRoboTestTask","OddNumberException");
		Toast.makeText(context, "OddNumberException",Toast.LENGTH_SHORT).show();
	}
	
	protected void onException(EvenNumberException e) throws RuntimeException {
		Log.d("AbstractRoboTestTask","EvenNumberException");
		Toast.makeText(context, "EvenNumberException",Toast.LENGTH_SHORT).show();
	}
	
	/*@Override
	protected void onException(Exception arg0) throws RuntimeException {
//		super.onException(arg0);
		Log.d("AbstractRoboTestTask","Exception");
		Toast.makeText(context, "Exception",Toast.LENGTH_SHORT).show();
	}*/
	@Override
	protected void onGenericException(Exception arg0) throws RuntimeException {
//		super.onException(arg0);
		Log.d("AbstractRoboTestTask","GenericException");
		Toast.makeText(context, "GenericException",Toast.LENGTH_SHORT).show();
	}
}
