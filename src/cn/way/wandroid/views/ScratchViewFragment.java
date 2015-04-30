package cn.way.wandroid.views;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import cn.way.wandroid.R;
import cn.way.wandroid.views.WScratchView.ProgressListener;

/**
 * @author Wayne
 * @2015年4月30日
 */
public class ScratchViewFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_scratchview, container,false);
		final WScratchView scratchView = (WScratchView) view.findViewById(R.id.scratchView);
		scratchView.setResultText("谢谢惠顾");
		Bitmap coverBitmap = 
				BitmapFactory.decodeResource(getResources(), R.drawable.empty_photo);
		scratchView.setCoverBitmap(coverBitmap);
		scratchView.setProgressListener(new ProgressListener() {
			@Override
			public void onProgressChanged(float progress) {
				if (progress>=0.5) {
					scratchView.clear();
				}
			}
		});
		view.findViewById(R.id.resetBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scratchView.reset();
			}
		});
		return view;
	}
}
