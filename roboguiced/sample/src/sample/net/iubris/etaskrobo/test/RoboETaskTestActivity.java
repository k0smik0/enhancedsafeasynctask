/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * RoboETaskTestActivity.java is part of 'EnhancedSafeAsyncTask'.
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

import net.iubris.etaskrobo.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RoboETaskTestActivity extends RoboActivity {
	
	@InjectView(R.id.button1) private Button button;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		button.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				new TestRoboTask(RoboETaskTestActivity.this).execute();
			}
		});
	};
}
