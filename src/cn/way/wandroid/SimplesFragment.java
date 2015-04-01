package cn.way.wandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Wayne
 * @2015年3月27日
 */
public class SimplesFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(getString(R.string.app_name));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.simples_fragment, container, false);
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
				FragmentContentActivity.startWithFragment(getActivity(), item.clazz);
			}
		});
		return view;
	}

	public static class DummyContent {
	    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
	    static {
	    	addItem(new DummyItem(TestFragment.class));
	    }
	    static void addItem(DummyItem item) {
	        ITEMS.add(item);
	    }
	}
	static class DummyItem {
        public Class<? extends Fragment> clazz;
        private String title;
        public DummyItem(Class<? extends Fragment> clazz) {
            this.clazz = clazz;
            this.title = clazz.getSimpleName();
        }
        @Override
        public String toString() {
        	return (1+DummyContent.ITEMS.indexOf(this))+"."+this.title;
        }
    }
}
