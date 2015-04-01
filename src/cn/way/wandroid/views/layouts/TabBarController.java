package cn.way.wandroid.views.layouts;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.utils.TabSelector;
import cn.way.wandroid.utils.TabSelector.OnTabChangeListener;
/**
 * 用于管理标签条
 * @author Wayne
 *
 */
public class TabBarController {
	private Activity ownerActivity;
	private TabSelector tabSelector;
	
	public TabBarController(Activity ownerAvitvity,ViewGroup container,int layoutId,int[]itemIds) {
		this.setOwnerActivity(ownerAvitvity);
		View view = ownerAvitvity.getLayoutInflater().inflate(layoutId, container, false); 
		if (view!=null) {
			container.addView(view);
			if (tabSelector==null) {
				tabSelector = new TabSelector();
			}
			for (int i = 0; i < itemIds.length; i++) {
				View item = view.findViewById(itemIds[i]);
				tabSelector.addItem(item);
			}
			tabSelector.setCurrentIndex(0);
		}
	}
	
	public void setCurrentIndex(int index){
		if (tabSelector!=null) {
			tabSelector.setCurrentIndex(index);
		}
	}

	public void setOnTabChangeListener(OnTabChangeListener tabChangeListener){
		if (tabSelector!=null) {
			tabSelector.setOnTabChangeListener(tabChangeListener);
		}
	}
	
	public Activity getOwnerActivity() {
		return ownerActivity;
	}

	public void setOwnerActivity(Activity ownerActivity) {
		this.ownerActivity = ownerActivity;
	}

}
