/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * EnhancedSafeAsyncTaskContexted.java is part of 'EnhancedSafeAsyncTask'.
 * 
 * 'EnhancedSafeAsyncTask' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'EnhancedSafeAsyncTask' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'EnhancedSafeAsyncTask'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.etask;

import java.util.concurrent.Executor;

import android.content.Context;
import android.os.Handler;
/**
 * Inspiring to roboguice's roboasynctask, this class adds context support
 * @author k0smik0
 * @param <ResultT>
 */
public abstract class EnhancedSafeAsyncTaskContexted<ResultT> extends EnhancedSafeAsyncTask<ResultT>{

	protected Context context;

	protected EnhancedSafeAsyncTaskContexted(Context context) {
		this.context = context;
	}

	protected EnhancedSafeAsyncTaskContexted(Context context, Handler handler) {
		super(handler);
		this.context = context;
	}
	
	protected EnhancedSafeAsyncTaskContexted(Context context, Executor executor) {
		super(executor);
		this.context = context;
	}

	protected EnhancedSafeAsyncTaskContexted(Context context, Handler handler, Executor executor) {
		super(handler, executor);
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	/**
 	 * printStackTrace() as default
 	 * @param e
 	 * @throws RuntimeException
 	 */
 	protected void onException(NullPointerException e) throws RuntimeException {
 		e.printStackTrace();
 	}
}
