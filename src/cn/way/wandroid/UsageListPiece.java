package cn.way.wandroid;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.way.wandroid.account.usage.AccountUsagePageAdapter;
import cn.way.wandroid.activityadapter.PageAdapter;
import cn.way.wandroid.activityadapter.Piece;
import cn.way.wandroid.activityadapter.usage.TestPageAdapter;

public class UsageListPiece extends Piece<UsageListPage> {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.usage_list_piece, container, false);
        ListView lv = (ListView) view.findViewById(R.id.listView);
        lv.setAdapter(new ArrayAdapter<DummyItem>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                DummyContent.ITEMS));
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DummyItem item = DummyContent.ITEMS.get(position);
				PageAdapter.turnToPage(getActivity(), item.clazz);
			}
		});
		return view;
	}
	@Override
	public void onPageReady() {

	}

	public static class DummyContent {
	    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
	    static {
	    	addItem(new DummyItem(TestPageAdapter.class));
	    	addItem(new DummyItem(AccountUsagePageAdapter.class));
	    }
	    static void addItem(DummyItem item) {
	        ITEMS.add(item);
	    }
	}
	static class DummyItem {
        public Class<? extends PageAdapter> clazz;
        private String title;
        public DummyItem(Class<? extends PageAdapter> clazz) {
            this.clazz = clazz;
            this.title = clazz.getSimpleName();
        }
        @Override
        public String toString() {
        	return (1+DummyContent.ITEMS.indexOf(this))+"."+this.title;
        }
    }
	
}
