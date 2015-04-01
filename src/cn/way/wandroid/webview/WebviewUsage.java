package cn.way.wandroid.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.toast.Toaster;

public class WebviewUsage extends BaseFragmentActivity {
	private View refreshBtn;
	private EditText urlInputer;
	private WebView webview;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.usage_webview);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient() {
			   public void onProgressChanged(WebView view, int progress) {
			     // Activities and WebViews measure progress with different scales.
			     // The progress meter will automatically disappear when we reach 100%
				   WebviewUsage.this.setProgress(progress * 100);
			   }
			 });
			 webview.setWebViewClient(new WebViewClient() {
			   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				  Toaster.instance(WebviewUsage.this).setup(description, Gravity.CENTER).show();
			   }
			 });

		urlInputer = (EditText) findViewById(R.id.url_inputer);
		refreshBtn = findViewById(R.id.refresh_btn);
		refreshBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refresh();
			}
		});
	}
	private void refresh(){
		webview.clearCache(true);
		if (urlInputer.getText().length()>0) {
			webview.loadUrl(urlInputer.getText().toString());
		}
	}
}
