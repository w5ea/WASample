package cn.way.wandroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import cn.way.wandroid.toast.Toaster;

/**
 * @author Wayne
 * @2015年3月27日
 */
public class FragmentContentActivity extends BaseActivity {
	public static String EXTRA_FRAGMENT_CLASS = "EXTRA_FRAGMENT_CLASS";
	public static String EXTRA_FINISH_CONFIRM = "EXTRA_FINISH_CONFIRM";
	private Fragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout layout = new FrameLayout(this);
		int layoutId = 100001;
		layout.setId(layoutId);
		setContentView(layout);
		this.finishConfirm = getIntent().getBooleanExtra(EXTRA_FINISH_CONFIRM, false);
		@SuppressWarnings("unchecked")
		Class<? extends Fragment> clazz = (Class<? extends Fragment>) getIntent().getSerializableExtra(EXTRA_FRAGMENT_CLASS);
		if (clazz!=null) {
			try {
				fragment = clazz.newInstance();
				getFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	/**
	 * 启动一个Activity
	 * @param parent 父Activity
	 * @param clazz 要显示的Fragment
	 */
	public static void startWithFragment(Activity parent,Class<? extends Fragment> clazz){
		startWithFragment(parent, clazz,-1, false);
	}
	public static void startWithFragment(Activity parent,Class<? extends Fragment> clazz,int requestCode){
		startWithFragment(parent, clazz,requestCode, false);
	}
	public static void startWithFragment(Activity parent,Class<? extends Fragment> clazz,boolean finishConfirm){
		startWithFragment(parent, clazz,-1, finishConfirm);
	}
	/**
	 * 启动一个Activity
	 * @param parent 父Activity
	 * @param clazz 要显示的Fragment
	 * @param finishConfirm true 则点击两次返回才退出，false直接退出
	 */
	public static void startWithFragment(Activity parent,Class<? extends Fragment> clazz,int requestCode,boolean finishConfirm){
		if (parent!=null&&clazz!=null) {
			Intent intent = new Intent(parent, FragmentContentActivity.class);
			intent.putExtra(EXTRA_FRAGMENT_CLASS, clazz);
			intent.putExtra(EXTRA_FINISH_CONFIRM, finishConfirm);
			parent.startActivityForResult(intent, requestCode);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (fragment!=null) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	private boolean finishConfirm = false;
	private long lastExitTime = -1;
	@Override
	public void finish() {
		if (!finishConfirm) {
			super.finish();
			return;
		}
		if (lastExitTime==-1) {
			lastExitTime = System.currentTimeMillis();
			Toaster.instance(this).setup("再按一次返回键退出应用",Gravity.BOTTOM,0,150).show();
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
}
