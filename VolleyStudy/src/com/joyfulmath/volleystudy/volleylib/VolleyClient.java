package com.joyfulmath.volleystudy.volleylib;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joyfulmath.volleystudy.utils.TraceLog;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/*volley can quickly connenct with http & download img, but it can not using for
 *download files 
 * */
public class VolleyClient extends HandlerThread {

	public final static int MSG_ID_VOLLEYCLIENT_STRING_REQUEST = 0x01;
	public final static int MSG_ID_VOLLEYCLIENT_JSON_REQUEST = 0x02;
	RequestQueue mQueue = null;
	
	Handler mHandler = null;
	
	public VolleyClient(String name) {
		super(name);
	}

	
	
	@Override
	protected void onLooperPrepared() {
		super.onLooperPrepared();
		mHandler = new Handler(getLooper()){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what)
				{
				case MSG_ID_VOLLEYCLIENT_STRING_REQUEST:
					String url = (String) msg.obj;
					addStringRequest(url);
					break;
				case MSG_ID_VOLLEYCLIENT_JSON_REQUEST:
					String jurl = (String) msg.obj;
					addJsonRequest(jurl);
					break;	
				}
			}
			
		};
	}	

	public void init(Context context) {
		start();
		mQueue = Volley.newRequestQueue(context);
	}

	public void startStringRequesAsync(String url)
	{
		TraceLog.i(url);
		if(mHandler == null)
		{
			throw new RuntimeException("handler thread has not been start");
		}
		
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_ID_VOLLEYCLIENT_STRING_REQUEST;
		msg.obj = url;
		mHandler.sendMessage(msg);
	}
	
	public void startJsonRequesAsync(String url)
	{
		TraceLog.i(url);
		if(mHandler == null)
		{
			throw new RuntimeException("handler thread has not been start");
		}
		
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_ID_VOLLEYCLIENT_JSON_REQUEST;
		msg.obj = url;
		mHandler.sendMessage(msg);
	}
	
	protected void addStringRequest(String url) {
		TraceLog.i(url);
		if (mQueue == null) {
			throw new RuntimeException("queue has not been inited");
		}
		StringRequest stringRequest = new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				TraceLog.i(response);
			}

		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				TraceLog.i(error.getMessage());
			}

		});

		mQueue.add(stringRequest);
	}
	
	protected void addJsonRequest(String url)
	{
		TraceLog.i(url);
		if (mQueue == null) {
			throw new RuntimeException("queue has not been inited");
		}
		
		JsonObjectRequest objectRequest = new JsonObjectRequest(Method.GET,url,null,new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				TraceLog.i(response.toString());
			}
			
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				TraceLog.i(error.getMessage());
			}
		});
		
		mQueue.add(objectRequest);
	}
}
