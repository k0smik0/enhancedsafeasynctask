# Enhanced SafeAsyncTask
This is an enhanced version of 
[roboguice's SafeAsyncTask][1], 
 but providing polymorphic behaviour for subclasses: <br/>so you can 
 declare different onException method, for each different exception type
 you would handle.  

So, don't override onException(Exception e) with some internal logic
in order to know and handle call's exceptions, but declare as below:
 <pre>
 public class MySafeAsyncTask extends EnhancedSafeAsyncTask {
 	public String call() throws MyException1,MyException2 {
 		//code
 	}
 	protected void onException(MyException1 e) throws RuntimeException {
 		//code for ui
 	}
 	protected void onException(MyException2 e) throws RuntimeException {
 		//code for ui
 	}
 }
 </pre>
*It is inspired to: <a href="http://code.google.com/p/asyncworks">asyncworks</a>*

  
----    
Still inspired to SafeAsyncTask, there is a [Context][2]-ed version, EnhancedSafeAsyncTaskContexted.java
  
Finally, a roboguiced version is RoboEnhancedAsyncTask, which inherit from RoboAsyncTask while applies polymorphic exception handling ehnancement 
----  
         
That's all, folkies ;D !
 
 [1]: http://code.google.com/p/roboguice/source/browse/roboguice/src/main/java/roboguice/util/SafeAsyncTask.java
 [2]: http://developer.android.com/reference/android/content/Context.html