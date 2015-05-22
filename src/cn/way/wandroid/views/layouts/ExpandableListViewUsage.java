package cn.way.wandroid.views.layouts;

import java.util.Locale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import cn.way.wandroid.R;
import cn.way.wandroid.toast.Toaster;

/**
 * @author Wayne
 * @2015年5月12日
 */
public class ExpandableListViewUsage extends Fragment {
	private ExpandableListView listView;
	private BaseExpandableListAdapter adapter;
	private String[] titles = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z"
			.split(" ");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.usage_expandable_list_view,
				container, false);
		listView = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				toast("onChildClick: " + groupPosition);
				return true;
			}
		});
		listView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				toast("onGroupClick: " + groupPosition);
				return false;
			}
		});
		adapter = new BaseExpandableListAdapter() {
			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return false;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				View view = getActivity().getLayoutInflater().inflate(
						R.layout.usage_expandable_list_view_item, null);
				TextView tv = (TextView) view.findViewById(R.id.tvTitle);
				tv.setText(groupPosition + 1 + ". " + titles[groupPosition]);
				View button = view.findViewById(R.id.button);
				final int gp = groupPosition;
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						toast("onClick: " + titles[gp]);
					}
				});
				return view;
			}

			@Override
			public long getGroupId(int groupPosition) {
				return 0;
			}

			@Override
			public int getGroupCount() {
				return titles.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				return null;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return 1;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				View view = getActivity().getLayoutInflater().inflate(
						R.layout.usage_expandable_list_view_item_child, null);
				TextView tv = (TextView) view.findViewById(R.id.tvText);
				tv.setText(titles[groupPosition].toLowerCase(Locale
						.getDefault()));
				return view;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		listView.setAdapter(adapter);
		return view;
	}

	private void toast(String text) {
		Toaster.instance(getActivity()).setup(text).show();
	}
}
