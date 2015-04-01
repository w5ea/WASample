package cn.way.wandroid.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.util.Log;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.utils.FileUtil;

public class ZipUsageActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String filename = "skin.zip";
        try {
        	InputStream inputStream = getAssets().open(filename);
			FileUtil.unZip(inputStream, getFilesDir().getAbsolutePath()+File.separator+"skin");
		} catch (IOException e) {
			Log.d("test", e.toString());
		}
	}
}
