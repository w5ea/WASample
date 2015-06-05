package cn.way.wandroid.activities.tabhost;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.R;
import cn.way.wandroid.applation.PhotoPickerUsage;
import cn.way.wandroid.utils.TabSelector;
import cn.way.wandroid.utils.TabSelector.OnTabChangeListener;
import cn.way.wandroid.views.FragmentTabHost;
import cn.way.wandroid.views.ScratchViewFragment;
import cn.way.wandroid.views.layouts.ExpandableListViewUsage;

/**
 * @author Wayne
 * @2015年5月28日
 */
public class FragmentTabHostUsage extends Fragment {
	private TabSelector ts = new TabSelector();
	private FragmentTabHost th;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.usage_fragment_tab_host, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupView();
	}
	private void setupView(){
		ts.clearItems();
		ts.addItem(findViewById(R.id.tab_item_1));
		ts.addItem(findViewById(R.id.tab_item_2));
		ts.addItem(findViewById(R.id.tab_item_3));

		if (th!=null) {
			ts.setCurrentIndex(th.getCurrentTab());
			th.setCurrentTabIndex(th.getCurrentTab(), true);
			return;
		}
		ts.setCurrentIndex(0);
		th = new FragmentTabHost(getActivity(),getChildFragmentManager(),R.id.tab_content);
	
        th.addTab("tab1",
        		ScratchViewFragment.class, null);
        th.addTab("tab2",
        		ExpandableListViewUsage.class, null);
        th.addTab("tab3",
        		PhotoPickerUsage.class, null);
        
        th.setCurrentTab(0);
        
        ts.setOnTabChangeListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(int index) {
				th.setCurrentTab(index);
			}
		});
	}
	private View findViewById(int resId){
		return getView().findViewById(resId);
	}
}
