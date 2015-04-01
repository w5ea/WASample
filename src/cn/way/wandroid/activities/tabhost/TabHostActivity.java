package cn.way.wandroid.activities.tabhost;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import cn.way.wandroid.R;
import cn.way.wandroid.imageloader.displayingbitmaps.ui.ImageGridFragment2;

public class TabHostActivity extends FragmentActivity {
	private FragmentTabHost mainTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost);
		//设置TabHost
        mainTabHost = 
        		(FragmentTabHost) findViewById(R.id.mainTabHost);
//        		new FragmentTabHost(this);
        
        mainTabHost.setup(this, getSupportFragmentManager(),R.id.tabhost_main_content);
        mainTabHost.addTab(mainTabHost.newTabSpec("tab1").setIndicator("1"),
        		ImageGridFragment2.class, null);
        mainTabHost.addTab(mainTabHost.newTabSpec("tab2").setIndicator("2"),
        		ImageGridFragment2.class, null);
        mainTabHost.addTab(mainTabHost.newTabSpec("tab3").setIndicator("3"),
        		ImageGridFragment2.class, null);
        //通过TabBarFragment来管理原生FragmentTabHost的功能
        TabBarFragment tabBar = (TabBarFragment) getSupportFragmentManager().findFragmentById(R.id.tabBar);
        tabBar.setTabHost(mainTabHost);
	}
}
