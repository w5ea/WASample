package cn.way.wandroid;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentContentActivity.startWithFragment(this, SimplesFragment.class,true);
		finish();
	}
}
