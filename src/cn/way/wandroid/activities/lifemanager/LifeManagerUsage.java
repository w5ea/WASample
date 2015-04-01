package cn.way.wandroid.activities.lifemanager;

import android.app.Activity;
import android.os.Bundle;
import cn.way.wandroid.R;
import cn.way.wandroid.activities.lifemanager.WLifeManager.WLifeManagerListener;
import cn.way.wandroid.views.WLabel;

public class LifeManagerUsage extends Activity {
	private WLifeManager lm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lifemanager);
		final WLabel tv = (WLabel) findViewById(R.id.label);
		tv.setTextSize(50);
		lm = new WLifeManager(this);
		lm.setListener(new WLifeManagerListener() {
			@Override
			public void lifePlusLeftTimeChanged(int lifePlusLeftTimeSeconds) {
				tv.setText(String.format("%02d:%02d:%02d  %d", lifePlusLeftTimeSeconds/60/60,lifePlusLeftTimeSeconds/60%60,lifePlusLeftTimeSeconds%60,lm.getLifeCount()));
			}
			
			@Override
			public void lifeCountChanged(int lifeCount,int maxLifeCount) {
				int lifePlusLeftTimeSeconds = lm.getLifePlusLeftTimeSeconds();
				tv.setText(String.format("%02d:%02d:%02d  %d", lifePlusLeftTimeSeconds/60/60,lifePlusLeftTimeSeconds/60%60,lifePlusLeftTimeSeconds%60,lm.getLifeCount()));
			}
		});
        lm.start(50,9,3,5);
//        lm.start();
	}
	@Override
	protected void onPause() {
		super.onPause();
		lm.pause();
	}
	@Override
	protected void onResume() {
		super.onResume();
		lm.resume();
	}
}
