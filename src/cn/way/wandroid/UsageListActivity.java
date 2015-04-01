package cn.way.wandroid;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cn.way.wandroid.activities.bulletin.BulletinUsage;
import cn.way.wandroid.activities.dialog.DialogUsage;
import cn.way.wandroid.activities.lifemanager.LifeManagerUsage;
import cn.way.wandroid.activities.residemenu.ResideMenuUsage;
import cn.way.wandroid.activities.tabhost.TabHostActivity;
import cn.way.wandroid.activities.viewpager.ViewPagerUsage;
import cn.way.wandroid.activities.views.ViewsActivity;
import cn.way.wandroid.activityadapter.PageAdapter;
import cn.way.wandroid.animation.AnimationUsage;
import cn.way.wandroid.applation.AppUtils;
import cn.way.wandroid.applation.ApplationInfoUsage;
import cn.way.wandroid.bluetooth.BluetoothUsage;
import cn.way.wandroid.data.greendao.GreenDaoUsage;
import cn.way.wandroid.graphics.GraphicsUsage;
import cn.way.wandroid.imageloader.usage.ImageLoaderUsage;
import cn.way.wandroid.json.GsonUsageActivity;
import cn.way.wandroid.net.AsynchronousHttpClientUsage;
import cn.way.wandroid.net.VolleyUsage;
import cn.way.wandroid.slidingmenu.usage.SlidingMenuUsage;
import cn.way.wandroid.text.TextUseage;
import cn.way.wandroid.utils.WLog;
import cn.way.wandroid.webview.WebviewUsage;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

public class UsageListActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<DummyItem>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                DummyContent.ITEMS));
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onItemSelected(position);
			}
		});
        setTitle(AppUtils.getAppName(this)+AppUtils.getAppVersionName(this));
        UpdateConfig.setDebug(BuildConfig.DEBUG);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        UmengUpdateAgent.update(this);
        WLog.d(new BigDecimal(new BigInteger("8888", 10)).toString());
        WLog.d(new BigDecimal(new BigInteger("8888", 16)).toString());
        WLog.d(new BigDecimal(new BigInteger("8888", Character.MAX_RADIX)).toString());
        findViewById(R.id.activityAdapterBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PageAdapter.turnToPage(UsageListActivity.this, UsageListPage.class);
			}
		});
//        GuideAction ga = GuideAction.A1;
//        WLog.d(AppGuider.shouldGuide(getApplicationContext(), ga)?ga+"true":ga+"false");
//        WLog.d(AppGuider.shouldGuide(getApplicationContext(), ga)?ga+"true":ga+"false");
//        ga = GuideAction.A2;
//        WLog.d(AppGuider.shouldGuide(getApplicationContext(), ga)?ga+"true":ga+"false");
//        WLog.d(AppGuider.shouldGuide(getApplicationContext(), ga)?ga+"true":ga+"false");
//        ga = GuideAction.A3;
//        WLog.d(AppGuider.shouldGuide(getApplicationContext(), ga)?ga+"true":ga+"false");
//        WLog.d(AppGuider.shouldGuide(getApplicationContext(), ga)?ga+"true":ga+"false");
        
    }

    public void onItemSelected(int index) {
//    	Log.d("test", PageNavigateManager.tag.toString());
//    	PageNavigateManager.clearTag();
    	
		DummyItem item = DummyContent.ITEMS.get(index);
    	Intent intent = new Intent(this, item.clazz);
    	startActivity(intent);
    }
    private long lastExitTime = -1;
	@Override
	public void finish() {
		if (lastExitTime==-1) {
			lastExitTime = System.currentTimeMillis();
			Toast.makeText(this, "再按一次返回键退出应用", Toast.LENGTH_SHORT).show();
		}else{
			long currentExitTime = System.currentTimeMillis();
			if (currentExitTime-lastExitTime<2000) {
				super.finish();
			}else{
				lastExitTime = -1;
				finish();
			}
		}
	}
	
	public static class DummyContent {
	    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
	    static {
	    	addItem(new DummyItem(BluetoothUsage.class));
	    	addItem(new DummyItem(VolleyUsage.class));
	    	addItem(new DummyItem(AnimationUsage.class));
	    	addItem(new DummyItem(ImageLoaderUsage.class));
	    	addItem(new DummyItem(WebviewUsage.class));
	    	addItem(new DummyItem(AsynchronousHttpClientUsage.class));
	    	addItem(new DummyItem(GsonUsageActivity.class));
	    	addItem(new DummyItem(GreenDaoUsage.class));
	    	addItem(new DummyItem(LifeManagerUsage.class));
	    	addItem(new DummyItem(BulletinUsage.class));
	    	addItem(new DummyItem(GraphicsUsage.class));
	    	addItem(new DummyItem(DialogUsage.class));
			addItem(new DummyItem(TabHostActivity.class));
			addItem(new DummyItem(ViewsActivity.class));
			addItem(new DummyItem(ResideMenuUsage.class));
			addItem(new DummyItem(ViewPagerUsage.class));
			addItem(new DummyItem(TextUseage.class));
			addItem(new DummyItem(ApplationInfoUsage.class));
			addItem(new DummyItem(SlidingMenuUsage.class));
	    }
	    private static void addItem(DummyItem item) {
	        ITEMS.add(item);
	    }
	}
	public static class DummyItem {
        public Class<? extends Activity> clazz;
        private String title;
        public DummyItem(Class<? extends Activity> clazz) {
            this.clazz = clazz;
            this.title = clazz.getSimpleName();
        }
        @Override
        public String toString() {
        	return (1+DummyContent.ITEMS.indexOf(this))+"."+this.title;
        }
    }
}
