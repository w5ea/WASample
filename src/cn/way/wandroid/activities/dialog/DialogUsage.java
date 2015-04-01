package cn.way.wandroid.activities.dialog;

import cn.way.wandroid.R;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class DialogUsage extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final String[] strs = {"Dialog CoustomStyle ContentView","AlertDialog","AlertDialogContentView"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, strs);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					showDialogWithCoustomStyle();
					break;
				case 1:
					showAlertDialog();
					break;
				case 2:
					showAlertDialogWithContentView();
					break;
				default:
					break;
				}
			}
		});
	}
	
	private void showDialogWithCoustomStyle(){
		View dialogContentView = getLayoutInflater().inflate(R.layout.dialog_1, null);
		Dialog dialog = new Dialog(this);
//		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);//不显示标题
//		dialog.getWindow().getAttributes().dimAmount = 0.0f;//透明度0为透明1为不透明
//		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//不使用默认背景图片 
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.setContentView(dialogContentView);
		dialog.show();
	}
	private void showAlertDialog(){
		Builder builder = new Builder(this)
		.setCancelable(true)
		.setTitle("TITLE")
		.setMessage("MESSAGE")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setNegativeButton("NO", null)
		.setNeutralButton("Center", null)
		.setPositiveButton("YES", null);
		Dialog dialog = builder.create();
		dialog.show();
	}
	private void showAlertDialogWithContentView(){
		View dialogContentView = getLayoutInflater().inflate(R.layout.dialog_1, null);
		Builder builder = new Builder(this)
		.setView(dialogContentView)
		.setCancelable(false)
		.setIcon(android.R.drawable.ic_dialog_alert);
		final Dialog dialog = builder.create();
		dialogContentView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
}
