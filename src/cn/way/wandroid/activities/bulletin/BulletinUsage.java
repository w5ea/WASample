package cn.way.wandroid.activities.bulletin;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.views.BulletinBoardFragment;

public class BulletinUsage extends BaseFragmentActivity {
	BulletinBoardFragment bulletin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bulletin);
		bulletin = (BulletinBoardFragment) getSupportFragmentManager().findFragmentById(R.id.bulletin);
//		bulletin = new BulLETINBOARDFRAGMENT();
//		GETSUPPORTFRAGMENTManager().beginTransaction().add(R.id.bulletin, bulletin).commit();
	}
	@Override
	protected void onResume() {
		super.onResume();
		ArrayList<String> bulletins = new ArrayList<String>();
		String text = "sss : ";
		for (int i = 0; i < 10; i++) {
			text += text;
			bulletins.add((i+1)+text);
		}
		bulletin.updateBulletins(bulletins);
		bulletin.startAnimation();
		bulletin.getView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> bulletins = new ArrayList<String>();
				String text = "a : ";
				for (int i = 0; i < 5; i++) {
					text += text;
					bulletins.add((i+1)+text);
				}
				bulletin.stopAnimation();
				bulletin.updateBulletins(bulletins);
				bulletin.startAnimation();
//				startActivity(new Intent(BulletinUsage.this, GsonUsageActivity.class));
			}
		});
	}
	@Override
	public void onPanelClosed(int featureId, Menu menu) {
		super.onPanelClosed(featureId, menu);
	}
}
