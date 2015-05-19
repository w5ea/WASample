package cn.way.wandroid.views.wdialog;

import java.util.ArrayList;

import cn.way.wandroid.R;
import cn.way.wandroid.views.ContentControlDialog;
import cn.way.wandroid.views.ContentControlDialog.DialogContentController;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Wayne
 * @2015年5月19日
 */
public class WDialogAndListViewUsage extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wdialog_and_listview_usage, container, false);
		view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
		return view;
	}
	private ContentControlDialog mDialog;
	private void showDialog(){
		if (mDialog==null) {
			mDialog = new ContentControlDialog(getActivity());
			TestDialogContentController controller = new TestDialogContentController(getActivity(), mDialog);
			mDialog.setContentView(controller);
		}
		mDialog.show();
	}
	public class TestDialogContentController extends DialogContentController{

		public TestDialogContentController(Activity activity,
				ContentControlDialog dialog) {
			super(activity, dialog);
		}

		@SuppressLint("InflateParams")
		@Override
		protected ViewGroup inflateContentView() {
			return (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.wdialog_and_listview_usage_content_view, null);
		}

		@Override
		protected View inflateReplaceableView(ViewGroup parent) {
			return getActivity().getLayoutInflater().inflate(R.layout.wdialog_and_listview_usage_inner_view, parent,false);
		}
	
		private ListView mListView;
		private ArrayAdapter<String> mAdapter;
		private ArrayList<String> data ;
		@Override
		protected void inflateSubView() {
			mListView = (ListView) getView().findViewById(R.id.listView);
			if (data==null) {
				data = new ArrayList<String>();
			}
			mAdapter = new ArrayAdapter<String>(getActivity(), 0, data){
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = convertView;
					if (view == null) {
						view = getActivity().getLayoutInflater().
								inflate(R.layout.wdialog_and_listview_usage_row, parent, false);
					}
					return view;
				}
			};
			mListView.setAdapter(mAdapter);
			for (int i = 0; i < 1; i++) {
				data.add("item:"+i);
			}
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public String getTitle() {
			return "Title";
		}
	}
}
