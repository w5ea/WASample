package cn.way.wandroid.activities.tabhost;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.way.wandroid.R;

public class TabBarFragment extends Fragment{
	private FragmentTabHost tabHost;
	private ArrayList<View> tabItems = new ArrayList<View>();
//	private ArrayList<View> tabItemStateButtons = new ArrayList<View>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab_bar, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupView();
	}
	
	public void setupView(){
		if (tabHost!=null) {
			View item1 = getActivity().findViewById(R.id.tab_item_1);
			tabItems.add(item1);
			View item2 = getActivity().findViewById(R.id.tab_item_2);
			tabItems.add(item2);
			View item3 = getActivity().findViewById(R.id.tab_item_3);
			tabItems.add(item3);
			
//			View tabBtn1 = getActivity().findViewById(R.id.tab_btn_1);
//			View tabBtn2 = getActivity().findViewById(R.id.tab_btn_2);
//			View tabBtn3 = getActivity().findViewById(R.id.tab_btn_3);
//			tabItemStateButtons.add(tabBtn1);
//	        tabItemStateButtons.add(tabBtn2);
//	        tabItemStateButtons.add(tabBtn3);
			
			item1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (tabHost.getCurrentTab()!=0) {
						tabHost.setCurrentTab(0);
						changeItemState(v);
					}
				}
			});
	        item2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (tabHost.getCurrentTab()!=1) {
						tabHost.setCurrentTab(1);
						changeItemState(v);
					}
				}
			});
	        item3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (tabHost.getCurrentTab()!=2) {
						tabHost.setCurrentTab(2);
						changeItemState(v);
					}
				}
			});
	        
	        //default
	        tabHost.setCurrentTab(1);
			changeItemState(item2);
		}
	}
	private void changeItemState(View item){
		boolean cState = item.isSelected();
		for (View ti : tabItems) {
			if (ti == item) {
				ti.setSelected(!cState);
			}else{
				ti.setSelected(cState);
			}
		}
	}
	public FragmentTabHost getTabHost() {
		return tabHost;
	}
	public void setTabHost(FragmentTabHost tabHost) {
		this.tabHost = tabHost;
	}
}
