package com.sap.truecolor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		Intent intent = getIntent();
        int[] msg = intent.getIntArrayExtra(MainActivity.RESULT);

		
		setContentView(R.layout.result_layout);
		setupActionBar();

		ColorPieView pie1 = new ColorPieView(this, R.color.orange, msg[0]);
		LinearLayout pieContainer1 = (LinearLayout) findViewById(R.id.pie_orange);
		pieContainer1.addView(pie1);

		ColorPieView pie2 = new ColorPieView(this, R.color.green, msg[1]);
		LinearLayout pieContainer2 = (LinearLayout) findViewById(R.id.pie_green);
		pieContainer2.addView(pie2);
		
		ColorPieView pie3 = new ColorPieView(this, R.color.blue, msg[2]);
		LinearLayout pieContainer3 = (LinearLayout) findViewById(R.id.pie_blue);
		pieContainer3.addView(pie3);

		ColorPieView pie4 = new ColorPieView(this, R.color.gold, msg[3]);
		LinearLayout pieContainer4 = (LinearLayout) findViewById(R.id.pie_gold);
		pieContainer4.addView(pie4);
	
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
