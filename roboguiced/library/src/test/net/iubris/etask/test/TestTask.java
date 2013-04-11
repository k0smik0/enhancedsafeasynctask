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
package net.iubris.etask.test;


import android.content.Context;
import android.widget.Toast;

public class TestTask extends AbstractTestTask {

//	private Context context;

	public TestTask(Context context) {
//		this.context = context;
		super(context);
	}
	
	private int number;

	@Override
	public String call() throws OddNumberException, EvenNumberException  {
		number = (int) Math.floor( (Math.random()*100) );
		System.out.println(number);
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
	
	/*@Override
	protected void onException(Exception e) throws RuntimeException {
		super.onException(e);
		Log.d("SC","e");
	}*/
	
	//@Override
	/*protected void onException(NumberFormatException e) throws RuntimeException {	
		super.onException(e);
//		System.out.println("nfe");
		Log.d("SC","nfe");
		Toast.makeText(context, "nfe",Toast.LENGTH_SHORT).show();
	}*/

}
