package cn.way.wandroid.applation;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;

public class ApplationInfoUsage extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage_applation);
		TextView tv = (TextView) findViewById(R.id.textVeiw);
		String value = AppUtils.getAppInfoMetaData(this, "UMENG_CHANNEL");
		try {
			value = "Manifest.xml/applation/meta-data/"
					+ getPackageManager().getApplicationInfo(getPackageName(),
							PackageManager.GET_META_DATA).metaData
							.getString("UMENG_CHANNEL");
			Log.d("test", value);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		tv.setText(value);
	}
}
