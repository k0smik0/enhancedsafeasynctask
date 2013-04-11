/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * RoboEnhancedAsyncTask.java is part of 'EnhancedAsyncTask'.
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
package net.iubris.etask.roboguiced;

import java.util.concurrent.Executor;

import net.iubris.mirror.ExceptionHandler;
import net.iubris.mirror.OnExceptionProvided;

import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.os.Handler;
/**
 * This is roboguiced version of EnhancedSafeAsyncTaskContexted.java<br/>
 * You need roboguice.jar, javax.inject.jar and guice-3.x-no_aop.jar in order to compile it<br/>
 * see <a href="http://code.google.com/p/roboguice">http://code.google.com/p/roboguice</a>
 * @author k0smik0
 * @param <ResultT>
 */
public abstract class RoboEnhancedAsyncTask<ResultT> extends RoboAsyncTask<ResultT> {

	protected RoboEnhancedAsyncTask(Context context, Executor executor) {
		super(context, executor);
	}
	protected RoboEnhancedAsyncTask(Context context, Handler handler,
			Executor executor) {
		super(context, handler, executor);
	}
	protected RoboEnhancedAsyncTask(Context context, Handler handler) {
		super(context, handler);
	}
	protected RoboEnhancedAsyncTask(Context context) {
		super(context);
	}

	@Override
	protected void onException(Exception e) throws RuntimeException {
		ExceptionHandler.findBestMatchException(e, (OnExceptionProvided) this);
	}
	
	
	/*
	@Inject static protected Provider<Context> contextProvider;
    @Inject static protected Provider<ContextScope> scopeProvider;
    
    protected ContextScope scope = scopeProvider.get();
    protected Context context = contextProvider.get();
	
    protected RoboEnhancedAsyncTask(Context context) {
        super(context);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    protected RoboEnhancedAsyncTask(Context context, Handler handler) {
        super(context, handler);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    protected RoboEnhancedAsyncTask(Context context, Handler handler, Executor executor) {
        super(context, handler, executor);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    protected RoboEnhancedAsyncTask(Context context, Executor executor) {
        super(context, executor);
        RoboGuice.getInjector(context).injectMembers(this);
    }
    
    @Override
    protected Task<ResultT> newTask() {
//    	return new RoboTask<ResultT>(this);
        return new RoboTask(this);
    }

//    protected class RoboTask<ResultT>> extends EnhancedSafeAsyncTaskContexted.Task<ResultT> {
    protected class RoboTask extends EnhancedSafeAsyncTaskContexted.Task<ResultT> {
//    	public RoboTask(EnhancedSafeAsyncTaskContexted parent) {
        public RoboTask(EnhancedSafeAsyncTaskContexted<ResultT> parent) {
            super(parent);
        }

        @Override
        protected ResultT doCall() throws Exception {
            try {
                scope.enter(context);
                return super.doCall();
            } finally {
                scope.exit(context);
            }
        }
    }
    */
}