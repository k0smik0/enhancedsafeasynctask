/*******************************************************************************
 * 
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * RoboEnhancedAsyncTaskContexted.java is part of 'Enhancedsafeasynctask'.
 * 
 * 'Enhancedsafeasynctask' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Enhancedsafeasynctask' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Enhancedsafeasynctask' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.etask.roboguiced;

import java.util.concurrent.Executor;

import net.iubris.etask.EnhancedSafeAsyncTaskContexted;
import roboguice.RoboGuice;
import android.content.Context;
import android.os.Handler;
/**
 * This is roboguiced version of EnhancedSafeAsyncTaskContexted.java<br/>
 * You need roboguice.jar, javax.inject.jar and guice-3.x-no_aop.jar in order to compile it<br/>
 * see <a href="http://code.google.com/p/roboguice">http://code.google.com/p/roboguice</a>
 * @author k0smik0
 * @param <ResultT>
 */
public abstract class RoboEnhancedAsyncTaskContexted<ResultT> extends EnhancedSafeAsyncTaskContexted<ResultT> {

    protected RoboEnhancedAsyncTaskContexted(Context context) {
        super(context);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    protected RoboEnhancedAsyncTaskContexted(Context context, Handler handler) {
        super(context, handler);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    protected RoboEnhancedAsyncTaskContexted(Context context, Handler handler, Executor executor) {
        super(context, handler, executor);
        RoboGuice.getInjector(context).injectMembers(this);
    }

    protected RoboEnhancedAsyncTaskContexted(Context context, Executor executor) {
        super(context, executor);
        RoboGuice.getInjector(context).injectMembers(this);
    }
}
