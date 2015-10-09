package com.joyfulmath.volleystudy.activity;

import com.joyfulmath.volleystudy.R;

import com.joyfulmath.volleystudy.utils.TraceLog;
import com.joyfulmath.volleystudy.volleylib.VolleyClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class VolleyStudyMainActivity extends Activity {
	
	TextView mText = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley_study_main);
		mText = (TextView) findViewById(R.id.action_text_start);
		mText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TraceLog.i();
//				startConnect("http://www.baidu.com");
				startConnectJson("http://m.weather.com.cn/data/101010100.html");
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.volley_study_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	protected void startConnect(String url) {
		VolleyClient client = new VolleyClient("volley client");
		client.init(getApplicationContext());
		client.startStringRequesAsync(url);
		
	}
	
	protected void startConnectJson(String url)
	{
		VolleyClient client = new VolleyClient("volley client");
		client.init(getApplicationContext());
		client.startJsonRequesAsync(url);
	}
}
