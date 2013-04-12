/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * TestTask.java is part of 'EnhancedAsyncTask'.
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
package net.iubris.etaskrobo.test;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TestRoboTask extends AbstractTestRoboTask {

	private int number;
	
	public TestRoboTask(Context context) {
		super(context);
	}

	@Override
	public String call() throws GreaterThanFiftyException, OddNumberException, EvenNumberException  {
		number = (int) Math.floor( (Math.random()*100) );
		Log.d("RoboTestTask","number: "+number);
		if (number>50)
			throw new GreaterThanFiftyException();
		if (number%2 ==0) // even
			throw new EvenNumberException();	
		throw new OddNumberException();
	}
	
	@Override
	protected void onException(EvenNumberException e) throws RuntimeException {
		super.onException(e);
		Toast.makeText(context, ""+number, Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onException(OddNumberException e) throws RuntimeException {
		super.onException(e);
		Toast.makeText(context, ""+number, Toast.LENGTH_SHORT).show();
	}

}
