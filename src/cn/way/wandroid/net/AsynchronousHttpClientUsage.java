package cn.way.wandroid.net;

import java.util.Date;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import cn.way.wandroid.utils.WLog;
import cn.way.wandroid.utils.StrUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

public class AsynchronousHttpClientUsage extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		testOAuth2();
	}
	private void testOAuth2(){
		String url = "https://api.weibo.com/oauth2/authorize?client_id=1905473307&redirect_uri=http%3A%2F%2Fsns.whalecloud.com%2Fsina2%2Fcallback&display=client";
		new AsyncHttpClient().get(url, new TextHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				WLog.d(responseString);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				WLog.d(responseString);
			}
		});
	}
	
	@SuppressWarnings("unused")
	private void test(){
		/*{"ID":"1","Token":"Sl1OOfvb0I51TMqlic88T1JkXPoLoaYWZwtj64udeq0="}*/
		String host = 
//				"http://auth.91yaojiang.com/v1/";
				"http://y1.91yaojiang.com/v1/";
		String path = host+
//				"api/Account/Logon?name=18899998888&password=123456&clientType=3&protocolVer=100";
				"api/User/Prize";
		AsyncHttpClient client = new AsyncHttpClient();
		String timestamp = new Date().getTime()/1000+"";
//		Log.d("test", timestamp);
		try {
			String authorization = StrUtils.hmacsha256(timestamp, "Sl1OOfvb0I51TMqlic88T1JkXPoLoaYWZwtj64udeq0=");
			authorization = String.format("yj %s %s", "1",authorization);
			client.addHeader("Authorization", authorization);
			Log.d("test", authorization);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("test", e.toString());
		}
		client.addHeader("timestamp", timestamp);
		
		client.get(path, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Log.d("test", throwable.toString());
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.d("test", response.toString());
			}
		    
		});
	}
}
