package cn.way.wandroid.net;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.TestResource;
import cn.way.wandroid.utils.WLog;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class VolleyUsage extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage_volley);
		
		final RequestQueue mQueue = Volley
				.newRequestQueue(getApplicationContext());
		findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = TestResource.URL_JSON_APPS;
				mQueue.add(new JsonObjectRequest(Method.GET,url,null,
						new Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								WLog.d("response : " + response.toString());
							}
						}, null));
				mQueue.start();
			}
		});
		ImageView imageView = (ImageView) findViewById(R.id.iv);
		ImageListener listener = ImageLoader.getImageListener(imageView, 0, 0);  
		new ImageLoader(mQueue, new ImageCache() {
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
			}
			@Override
			public Bitmap getBitmap(String url) {
				return null;
			}
		}).get(TestResource.URL_IMAGE, listener);
		NetworkImageView niv = (NetworkImageView) findViewById(R.id.niv);
		niv.setImageUrl(TestResource.URL_IMAGE, new ImageLoader(mQueue, new ImageCache() {
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
			}
			@Override
			public Bitmap getBitmap(String url) {
				return null;
			}
		}));
	}
}
