/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * EnhancedSafeAsyncTask.java is part of 'EnhancedSafeAsyncTask'.
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


import java.io.InterruptedIOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import net.iubris.mirror.ExceptionDispatcher;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * This is an enhanced version of roboguice's SafeAsyncTask, 
 * but this provides polymorphic behaviour for subclasses, so you can 
 * declare different onException method, for each different exception type
 * you would handle. <br/>
 * So, you don't declare onException(Exception e) and then use some internal logic
 * in order to know exception type, but you can declare:
 * <pre>
 * public String call() throws MyException1,MyException2 {}
 * 
 * protected void onException(MyException1 e) throws RuntimeException {}
 * protected void onException(MyException2 e) throws RuntimeException {}
 * //... others
 * </pre>
 * For details about original SafeAsyncTask: <a href="http://code.google.com/p/roboguice/source/browse/roboguice/src/main/java/roboguice/util/SafeAsyncTask.java">SafeAsyncTask.java</a><br/>
 * and for details about improvement, the inspired come from: <a href="http://code.google.com/p/asyncwork">asyncwork</a>
 * <br/><br/><br/><br/>
 * 
 * From original SafeAsyncTask doc:<br/>
 * 
 * A class similar but unrelated to android's {@link android.os.AsyncTask}.
 *
 * Unlike AsyncTask, this class properly propagates exceptions.
 *
 * If you're familiar with AsyncTask and are looking for {@link android.os.AsyncTask#doInBackground(Object[])},
 * we've named it {@link #call()} here to conform with java 1.5's {@link java.util.concurrent.Callable} interface.
 *
 * Current limitations: does not yet handle progress, although it shouldn't be
 * hard to add.
 *
 * If using your own executor, you must call future() to get a runnable you can execute.
 * 
 * @param <ResultT>
 */
public abstract class EnhancedSafeAsyncTask<ResultT> implements Callable<ResultT> {
	
	protected static int MAX_SEARCH_RIGHT_EXCEPTION_TRY = 5;
	
    public static final int DEFAULT_POOL_SIZE = 25;
    protected static final Executor DEFAULT_EXECUTOR = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

    protected Handler handler;
    protected Executor executor;
    protected StackTraceElement[] launchLocation;
    protected FutureTask<Void> future;


    /**
     * Sets executor to Executors.newFixedThreadPool(DEFAULT_POOL_SIZE) and
     * Handler to new Handler()
     */
    public EnhancedSafeAsyncTask() {
        this.executor = DEFAULT_EXECUTOR;
    }

    /**
     * Sets executor to Executors.newFixedThreadPool(DEFAULT_POOL_SIZE)
     */
    public EnhancedSafeAsyncTask( Handler handler ) {
        this.handler = handler;
        this.executor = DEFAULT_EXECUTOR;
    }

    /**
     * Sets Handler to new Handler()
     */
    public EnhancedSafeAsyncTask( Executor executor ) {
        this.executor = executor;
    }

    public EnhancedSafeAsyncTask( Handler handler, Executor executor ) {
        this.handler = handler;
        this.executor = executor;
    }


    public FutureTask<Void> future() {
        future = new FutureTask<Void>( newTask() );
        return future;
    }

    public EnhancedSafeAsyncTask<ResultT> executor( Executor executor ) {
        this.executor = executor;
        return this;
    }

    public Executor executor() {
        return executor;
    }

    public EnhancedSafeAsyncTask<ResultT> handler( Handler handler ) {
        this.handler = handler;
        return this;
    }

    public Handler handler() {
        return handler;
    }

    public void execute() {
        execute(Thread.currentThread().getStackTrace());
    }

    protected void execute( StackTraceElement[] launchLocation ) {
        this.launchLocation = launchLocation;
        executor.execute( future() );
    }

    public boolean cancel( boolean mayInterruptIfRunning ) {
        if( future==null )
            throw new UnsupportedOperationException("You cannot cancel this task before calling future()");

        return future.cancel(mayInterruptIfRunning);
    }


    /**
     * @throws Exception, captured on passed to onException() if present.
     */
    protected void onPreExecute() throws Exception {}

    /**
     * @param t the result of {@link #call()}
     * @throws Exception, captured on passed to onException() if present.
     */
    //@SuppressWarnings({"UnusedDeclaration"})
    protected void onSuccess( ResultT t ) throws Exception {}

    /**
     * Called when the thread has been interrupted, likely because
     * the task was canceled.
     *
     * By default, calls {@link #onException(Exception)}, but this method
     * may be overridden to handle interruptions differently than other
     * exceptions.
     *
     * @param e an InterruptedException or InterruptedIOException
     */
    protected void onInterrupted( Exception e ) {
        onException(e);
    }

    /**
     * Logs the exception as an Error by default, but this method may
     * be overridden by subclasses.
     *
     * @param e the exception thrown from {@link #onPreExecute()}, {@link #call()}, or {@link #onSuccess(Object)}
     * @throws RuntimeException, ignored
     */
    protected void onException( Exception e ) throws RuntimeException {
        onThrowable(e);
    }

    protected void onThrowable( Throwable t ) throws RuntimeException {
        Log.e("AbstractEnhancedSafeAsyncTask", "Throwable caught during background processing", t);
    }
    
    /**
     * @throws RuntimeException, ignored
     */
    protected void onFinally() throws RuntimeException {}


    protected Task<ResultT> newTask() {
        return new Task<ResultT>(this);
    }


    public static class Task<ResultT> implements Callable<Void> {
        protected EnhancedSafeAsyncTask<ResultT> parent;
        protected Handler handler;

        public Task(EnhancedSafeAsyncTask<ResultT> parent) {
            this.parent = parent;
            this.handler = parent.handler!=null ? parent.handler : new Handler(Looper.getMainLooper());
        }

        public Void call() throws Exception {
            try {
                doPreExecute();
                doSuccess(doCall());
            } catch( final Exception e ) {
                try {
                    doException(e);
                } catch( Exception f ) {
                    // logged but ignored
                    Log.e("","",f);
                }
            } catch( final Throwable t ) {
                try {
                    doThrowable(t);
                } catch( Exception f ) {
                    // logged but ignored
                	Log.e("","",f);
                }
            } finally {
                doFinally();
            }
            return null;
        }

        protected void doPreExecute() throws Exception {
            postToUiThreadAndWait( new Callable<Void>() {
                public Void call() throws Exception {
                    parent.onPreExecute();
                    return null;
                }
            });
        }

        protected ResultT doCall() throws Exception {
            return parent.call();
        }

        protected void doSuccess( final ResultT r ) throws Exception {
            postToUiThreadAndWait( new Callable<Void>() {
                public Void call() throws Exception {
                    parent.onSuccess(r);
                    return null;
                }
            });
        }

        protected void doException( final Exception e ) throws Exception {
            if( parent.launchLocation!=null ) {
                final ArrayList<StackTraceElement> stack = new ArrayList<StackTraceElement>(Arrays.asList(e.getStackTrace()));
                stack.addAll(Arrays.asList(parent.launchLocation));
                e.setStackTrace(stack.toArray(new StackTraceElement[stack.size()]));
            }
            
            // the improvement invokes bestMatchedMethod
            postToUiThreadAndWait( new Callable<Void>() {
            	public Void call() throws Exception {
                    if( e instanceof InterruptedException || e instanceof InterruptedIOException )
                        parent.onInterrupted(e);
                    else {
                    	Method bestMatchedMethod = ExceptionDispatcher.findBestMatchException(e, parent, "onException", 0, MAX_SEARCH_RIGHT_EXCEPTION_TRY);
                    	if (bestMatchedMethod!=null) {
							if (!(bestMatchedMethod.isAccessible())) {
		                        bestMatchedMethod.setAccessible(true);
		                        bestMatchedMethod.invoke(parent,e);
		                        bestMatchedMethod.setAccessible(false);
	                        } else {
	                        	bestMatchedMethod.invoke(parent,e);
	                        }
                    	} else {
                            // old
                            parent.onException(e);
                    	}
                    }
                    return null;
                }
            });
        }

        protected void doThrowable( final Throwable e ) throws Exception {
            if( parent.launchLocation!=null ) {
                final ArrayList<StackTraceElement> stack = new ArrayList<StackTraceElement>(Arrays.asList(e.getStackTrace()));
                stack.addAll(Arrays.asList(parent.launchLocation));
                e.setStackTrace(stack.toArray(new StackTraceElement[stack.size()]));
            }
            postToUiThreadAndWait( new Callable<Void>() {
                public Void call() throws Exception {
                    parent.onThrowable(e);
                    return null;
                }
            });
        }
        
        protected void doFinally() throws Exception {
            postToUiThreadAndWait( new Callable<Void>() {
                public Void call() throws Exception {
                    parent.onFinally();
                    return null;
                }
            });
        }


        /**
         * Posts the specified runnable to the UI thread using a handler,
         * and waits for operation to finish.  If there's an exception,
         * it captures it and rethrows it.
         * @param c the callable to post
         * @throws Exception on error
         */
        protected void postToUiThreadAndWait( final Callable<Void> c ) throws Exception {
            final CountDownLatch latch = new CountDownLatch(1);
            final Exception[] exceptions = new Exception[1];

            // Execute onSuccess in the UI thread, but wait
            // for it to complete.
            // If it throws an exception, capture that exception
            // and rethrow it later.
            handler.post( new Runnable() {
               public void run() {
                   try {
                       c.call();
                   } catch( Exception e ) {
                       exceptions[0] = e;
                   } finally {
                       latch.countDown();
                   }
               }
            });

            // Wait for onSuccess to finish
            latch.await();

            if( exceptions[0] != null ) {
                throw exceptions[0];
            }
        }
        
        /**
         * inspired by http://code.google.com/p/asyncworks
         * @param throwable
         * @return {@link Method}
         */
        //OLD, throwing UnknownError
        /*
        private Method findBestMatch(Throwable throwable) {
        	//System.out.println("finding best");
    	    Class<?> handlerClass = parent.getClass();
System.out.println("trying on: "+handlerClass.getName());
    	    Class<?> throwableClass = throwable.getClass();
System.out.println("throwable: "+throwableClass.getSimpleName());
    	    do {
    	    	try {
    	    		//Method method = handlerClass.getMethod("onException", throwableClass);
    	    		Method method = handlerClass.getDeclaredMethod("onException", throwableClass);
System.out.println("found: "+method+" !");
//					method.setAccessible(true);
    	    		return method;
    	    	} catch (NoSuchMethodException e) {
System.out.println("nothing found, trying on superclass");
//    	    		throwableClass = throwableClass.getSuperclass();
    	    		handlerClass = handlerClass.getSuperclass();
System.out.println("superclass: "+handlerClass.getSimpleName()); 	    		
//    	    		if (throwableClass == Object.class) {
	    			if (handlerClass == Object.class) {
    	    			// this should never happen because onException(Throwable) must be present for an instance
    	    			// of ExceptionHandler
    	    			throw new UnknownError("Expected method onException("+throwable.getClass()+") not found");
    	    		}
    	    	}
    	    } while (true);
        }
        */
        // NEW, calling onException(Exception e) if no other better found 
        /*
        private static <ResultT> Method findBestMatchException(Exception throwable, EnhancedSafeAsyncTask<ResultT> self) {
        	Class<?> handlerClass = self.getClass();
        	Class<?> founderClass = EnhancedSafeAsyncTask.class;
//        	Class<?> handlerClass = parent.getClass();
    	    Class<?> throwableClass = throwable.getClass();
System.out.println("throwable is: "+throwableClass.getSimpleName());
    	    do {
    	    	try {
System.out.println("trying on: "+handlerClass.getName());
    	    		//Method method = handlerClass.getMethod("onException", throwableClass);
    	    		Method method = handlerClass.getDeclaredMethod("onException", throwableClass);
System.out.println("found: "+method+" !");
//        						method.setAccessible(true);
    	    		return method;
    	    	} catch (NoSuchMethodException e) {
//        	    	    		throwableClass = throwableClass.getSuperclass();
    	    		handlerClass = handlerClass.getSuperclass();
System.out.println("nothing found, trying on superclass: "+handlerClass.getSimpleName()); 	    		
//        	    	    		if (throwableClass == Object.class) {
	    			if (handlerClass == founderClass) {
						try {
							Method method = founderClass.getDeclaredMethod("onException", Exception.class);
//System.out.println("sorry, calling: "+selfClass.getSimpleName()+".onException(Exception e)");
System.out.println("sorry, calling: "+method.getName()+"(Exception e)");
							return method;
						} catch (NoSuchMethodException e1) {
	    	    			// this should never happen because onException(Exception) must be present for an instance
	    	    			// of ExceptionHandler	    				
	    	    			throw new UnknownError("Expected method onException("+throwable.getClass()+") not found");
						}
    	    		}
    	    	}
    	    } while (true);
        }
        */
        
    }
    
    
}
