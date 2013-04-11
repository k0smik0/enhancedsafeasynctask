/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AbstractRoboTestTask.java is part of 'EnhancedAsyncTask'.
 * 
 * 'EnhancedAsyncTask' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'EnhancedAsyncTask' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'EnhancedAsyncTask' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.etask.test;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import net.iubris.etask.roboguiced.RoboEnhancedAsyncTask;

public abstract class AbstractRoboTestTask extends RoboEnhancedAsyncTask<String> {

	protected AbstractRoboTestTask(Context context) {
		super(context);
	}
	
	protected void onException(OddNumberException e) throws RuntimeException {
		Log.d("ETaskTest","OddNumberException");
		Toast.makeText(context, "OddNumberException",Toast.LENGTH_SHORT).show();
	}
	
	protected void onException(EvenNumberException e) throws RuntimeException {
		Log.d("ETaskTest","EvenNumberException");
		Toast.makeText(context, "EvenNumberException",Toast.LENGTH_SHORT).show();
	}
}
