package cn.way.wandroid.openapi;

import cn.way.wandroid.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class AuthActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sdk_activity_auth);
		
		Button btn = (Button) findViewById(R.id.confirmBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setData(Uri.parse("token://"));
				setResult(200, i);
				finish();
			}
		});
		if (getIntent()!=null) {
			btn.setText(getIntent().getAction()+getIntent().getDataString());
		}
	}
}
