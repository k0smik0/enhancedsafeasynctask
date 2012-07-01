/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SC.java is part of enhancedsafeasynctask.
 * 
 * enhancedsafeasynctask is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * enhancedsafeasynctask is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with enhancedsafeasynctask ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.etask.test;


import net.iubris.etask.EnhancedSafeAsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SC extends EnhancedSafeAsyncTask<String> {

	private Context context;

	public SC(Context context) {
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
